package Task2;

import java.time.format.DateTimeFormatter;

public class PdfExportStrategy implements ExportStrategy {
    @Override
    public String formatName() { return "PDF"; }

    @Override
    public String export(ReportData data) {
        return "%PDF-1.7 HEADER\n"
                + "TITLE: " + data.getTitle() + "\n"
                + "GENERATED: " + data.getGeneratedAt().format(DateTimeFormatter.ISO_LOCAL_DATE) + "\n"
                + "[PDF Table Stream Rendered with " + data.getRows().size() + " rows]\n";
    }
}
