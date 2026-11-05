package Task5;

public interface TelemetryRepository {
    void insertMetric(String deviceId, double temp, double press, long time);
}
