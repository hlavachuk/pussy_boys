package Task5;

import java.util.ArrayList;
import java.util.List;

public class MockTelemetryRepository implements TelemetryRepository {
    public final List<String> saved = new ArrayList<>();

    @Override
    public void insertMetric(String deviceId, double temp, double press, long time) {
        saved.add(deviceId + ":" + temp + ":" + press);
    }
}
