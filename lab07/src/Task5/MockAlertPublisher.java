package Task5;

import java.util.ArrayList;
import java.util.List;

public class MockAlertPublisher implements AlertPublisher {
    public final List<String> alerts = new ArrayList<>();

    @Override
    public void sendEmergencyAlert(String topic, String payload) {
        alerts.add(topic + ":" + payload);
    }
}
