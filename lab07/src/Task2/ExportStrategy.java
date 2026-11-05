package Task2;

/** Точка розширення (Extension Point): кожен формат експорту — окрема стратегія. */
public interface ExportStrategy {
    String formatName();

    String export(ReportData data);
}
