package Task1;

/** Відповідальність: бізнес-логіка розрахунків. */
public class WorkoutMetricsCalculator {

    private static final double RUNNING_MET = 9.8;

    public WorkoutMetrics calculate(WorkoutSessionData session) {
        double pace = session.getDurationMinutes() / session.getDistanceKm();
        double durationHours = session.getDurationMinutes() / 60.0;
        double calories = RUNNING_MET * session.getUserWeightKg() * durationHours;
        return new WorkoutMetrics(pace, calories, resolveZone(session.getAverageHeartRate()));
    }

    private String resolveZone(int heartRate) {
        if (heartRate >= 160) {
            return "Анаеробна / Анаеробний поріг";
        }
        if (heartRate >= 130) {
            return "Аеробна / Спалювання жиру";
        }
        return "Розминка / Легка активність";
    }
}
