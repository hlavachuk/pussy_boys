package Task1;

public class WorkoutSessionData {
    private String userId;
    private String deviceToken;
    private double distanceKm;
    private int durationMinutes;
    private int averageHeartRate;
    private double userWeightKg;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getDeviceToken() { return deviceToken; }
    public void setDeviceToken(String deviceToken) { this.deviceToken = deviceToken; }

    public double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(double distanceKm) { this.distanceKm = distanceKm; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public int getAverageHeartRate() { return averageHeartRate; }
    public void setAverageHeartRate(int averageHeartRate) { this.averageHeartRate = averageHeartRate; }

    public double getUserWeightKg() { return userWeightKg; }
    public void setUserWeightKg(double userWeightKg) { this.userWeightKg = userWeightKg; }
}
