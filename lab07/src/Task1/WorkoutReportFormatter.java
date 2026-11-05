package Task1;

import java.time.Instant;

/** Відповідальність: формування JSON-звіту. */
public class WorkoutReportFormatter {

    public String toJson(WorkoutSessionData session, WorkoutMetrics metrics) {
        int paceMinutes = (int) Math.floor(metrics.paceMinutesPerKm());
        int paceSeconds = (int) Math.round((metrics.paceMinutesPerKm() - paceMinutes) * 60);

        return String.format("{\n"
                        + "  \"UserId\": \"%s\",\n"
                        + "  \"DistanceKm\": %.2f,\n"
                        + "  \"Pace\": \"%d:%02d хв/км\",\n"
                        + "  \"Calories\": %.0f,\n"
                        + "  \"IntensityZone\": \"%s\",\n"
                        + "  \"Timestamp\": \"%s\"\n"
                        + "}",
                session.getUserId(),
                session.getDistanceKm(),
                paceMinutes, paceSeconds,
                metrics.caloriesBurned(),
                metrics.heartRateZone(),
                Instant.now());
    }
}
