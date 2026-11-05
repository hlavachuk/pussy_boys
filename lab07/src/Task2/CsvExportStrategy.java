package Task2;

import java.util.List;

public class CsvExportStrategy implements ExportStrategy {
    @Override
    public String formatName() { return "CSV"; }

    @Override
    public String export(ReportData data) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.join(",", data.getHeaders())).append("\n");
        for (List<String> row : data.getRows()) {
            builder.append(String.join(",", row)).append("\n");
        }
        return builder.toString();
    }
}
