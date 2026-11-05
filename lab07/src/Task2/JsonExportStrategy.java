package Task2;

public class JsonExportStrategy implements ExportStrategy {
    @Override
    public String formatName() { return "JSON"; }

    @Override
    public String export(ReportData data) {
        return "{\n"
                + "  \"title\": \"" + data.getTitle() + "\",\n"
                + "  \"generatedAt\": \"" + data.getGeneratedAt() + "\",\n"
                + "  \"totalRows\": " + data.getRows().size() + "\n"
                + "}\n";
    }
}
