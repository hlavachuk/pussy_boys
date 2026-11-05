package Task1;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Відповідальність: збереження тренування у локальний файл. */
public class WorkoutRepository {

    public void save(String userId, String json) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String path = String.format("workout_%s_%s.json", userId, timestamp);
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(json);
        } catch (IOException e) {
            System.err.println("Помилка збереження JSON тренування: " + e.getMessage());
        }
    }
}
