package Task5;

public class Main {
    public static void main(String[] args) {
        TelemetryPacket packet = new TelemetryPacket();
        packet.setDeviceId("sensor-01");
        packet.setTemperature(92.5);
        packet.setPressure(4.2);
        packet.setTimestamp(System.currentTimeMillis());

        System.out.println("Production");
        IoTTelemetryProcessor production = new IoTTelemetryProcessor(
                new TimescaleDbTelemetryRepository(),
                new KafkaAlertPublisher(),
                new LocalDiskEmergencyWriter());
        production.processTelemetry(packet);

        System.out.println("Mock");
        MockTelemetryRepository repo = new MockTelemetryRepository();
        MockAlertPublisher alerts = new MockAlertPublisher();
        MockBackupWriter backup = new MockBackupWriter();
        new IoTTelemetryProcessor(repo, alerts, backup).processTelemetry(packet);
        System.out.println("Saved: " + repo.saved);
        System.out.println("Alerts: " + alerts.alerts);
        System.out.println("Backup: " + backup.logs);
    }
}
