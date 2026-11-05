package Task1;

/** Відповідальність: валідація даних телеметрії. */
public class WorkoutValidator {

    public void validate(WorkoutSessionData session) {
        if (session == null) {
            throw new IllegalArgumentException("Параметр session не може бути null.");
        }
        if (session.getDistanceKm() <= 0 || session.getDistanceKm() > 300) {
            throw new IllegalArgumentException("Некоректна дистанція забігу.");
        }
        if (session.getDurationMinutes() <= 0 || session.getDurationMinutes() > 1440) {
            throw new IllegalArgumentException("Некоректна тривалість тренування.");
        }
        if (session.getAverageHeartRate() < 40 || session.getAverageHeartRate() > 230) {
            throw new IllegalArgumentException("Пульс виходить за межі безпечних фізіологічних показників.");
        }
    }
}
