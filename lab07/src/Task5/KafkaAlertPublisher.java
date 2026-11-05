package Task5;

public class KafkaAlertPublisher implements AlertPublisher {
    @Override
    public void sendEmergencyAlert(String topic, String payload) {
        System.out.println("Опубліковано тривогу у топік '" + topic + "': " + payload);
    }
}
