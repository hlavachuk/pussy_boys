package Task1;

/** Відповідальність: відправка push-сповіщення. */
public class PushNotificationService {

    public void send(WorkoutSessionData session, WorkoutMetrics metrics) {
        String payload = String.format(
                "{\"to\": \"%s\", \"title\": \"Чудове тренування!\", \"body\": \"Ви подолали %.1f км та спалили %.0f ккал!\"}",
                session.getDeviceToken(), session.getDistanceKm(), metrics.caloriesBurned());
        System.out.println("Надсилання Push-сповіщення через http://push-gateway.local/send");
        System.out.println("Payload: " + payload);
    }
}
