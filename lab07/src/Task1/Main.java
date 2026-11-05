package Task1;

public class Main {
    public static void main(String[] args) {
        WorkoutSessionData session = new WorkoutSessionData();
        session.setUserId("user42");
        session.setDeviceToken("token-abc");
        session.setDistanceKm(10.0);
        session.setDurationMinutes(55);
        session.setAverageHeartRate(150);
        session.setUserWeightKg(72.0);

        FitnessActivityManager manager = new FitnessActivityManager(
                new WorkoutValidator(),
                new WorkoutMetricsCalculator(),
                new WorkoutReportFormatter(),
                new WorkoutRepository(),
                new PushNotificationService());

        manager.processWorkoutSession(session);
    }
}
