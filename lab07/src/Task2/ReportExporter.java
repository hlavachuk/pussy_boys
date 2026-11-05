package Task2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Закритий для змін: новий формат = нова стратегія, цей клас не чіпаємо. */
public class ReportExporter {

    private final Map<String, ExportStrategy> strategies = new HashMap<>();

    public ReportExporter(List<ExportStrategy> availableStrategies) {
        for (ExportStrategy strategy : availableStrategies) {
            strategies.put(strategy.formatName().toUpperCase(), strategy);
        }
    }

    public String export(ReportData data, String format) {
        ExportStrategy strategy = strategies.get(format.toUpperCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Формат експорту " + format + " не підтримується.");
        }
        String result = strategy.export(data);
        System.out.println("[EXPORTER] Успішно створено звіт '" + data.getTitle() + "' у форматі "
                + format + ". Довжина: " + result.length() + " символів.");
        return result;
    }
}
