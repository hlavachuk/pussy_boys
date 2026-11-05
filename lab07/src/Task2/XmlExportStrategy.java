package Task2;

/** Новий формат, доданий БЕЗ змін у ReportExporter (демонстрація OCP). */
public class XmlExportStrategy implements ExportStrategy {
    @Override
    public String formatName() { return "XML"; }

    @Override
    public String export(ReportData data) {
        return "<report>\n"
                + "  <title>" + data.getTitle() + "</title>\n"
                + "  <generatedAt>" + data.getGeneratedAt() + "</generatedAt>\n"
                + "  <totalRows>" + data.getRows().size() + "</totalRows>\n"
                + "</report>\n";
    }
}
