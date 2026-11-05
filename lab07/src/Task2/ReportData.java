package Task2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReportData {
    private final String title;
    private final LocalDateTime generatedAt;
    private final List<String> headers = new ArrayList<>();
    private final List<List<String>> rows = new ArrayList<>();

    public ReportData(String title, LocalDateTime generatedAt) {
        this.title = title;
        this.generatedAt = generatedAt;
    }

    public String getTitle() { return title; }
    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public List<String> getHeaders() { return headers; }
    public List<List<String>> getRows() { return rows; }
}
