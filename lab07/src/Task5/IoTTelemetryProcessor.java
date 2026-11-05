package Task5;

public class IoTTelemetryProcessor {

    private static final double MAX_TEMPERATURE = 85.0;
    private static final double MAX_PRESSURE = 10.0;

    private final TelemetryRepository repository;
    private final AlertPublisher alertPublisher;
    private final BackupWriter backupWriter;

    public IoTTelemetryProcessor(TelemetryRepository repository, AlertPublisher alertPublisher, BackupWriter backupWriter) {
        this.repository = repository;
        this.alertPublisher = alertPublisher;
        this.backupWriter = backupWriter;
    }

    public void processTelemetry(TelemetryPacket packet) {
        if (packet == null || packet.getDeviceId() == null || packet.getDeviceId().isEmpty()) {
            throw new IllegalArgumentException("Некоректний пакет телеметрії.");
        }

        repository.insertMetric(packet.getDeviceId(), packet.getTemperature(), packet.getPressure(), packet.getTimestamp());
        backupWriter.writeBackupLog("DEV:" + packet.getDeviceId() + "|T:" + packet.getTemperature() + "|P:" + packet.getPressure());

        if (packet.getTemperature() > MAX_TEMPERATURE || packet.getPressure() > MAX_PRESSURE) {
            alertPublisher.sendEmergencyAlert("iot-critical-alerts", "Критичний перегрів або тиск на пристрої " + packet.getDeviceId() + "!");
        }
    }
}
