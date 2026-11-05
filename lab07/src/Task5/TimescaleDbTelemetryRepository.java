package Task5;

public class TimescaleDbTelemetryRepository implements TelemetryRepository {
    @Override
    public void insertMetric(String deviceId, double temp, double press, long time) {
        System.out.println("Записано метрику для " + deviceId + ": Temp=" + temp + "°C, Press=" + press + "bar.");
    }
}
