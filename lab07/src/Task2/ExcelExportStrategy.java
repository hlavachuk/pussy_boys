package Task2;

public class ExcelExportStrategy implements ExportStrategy {
    @Override
    public String formatName() { return "EXCEL"; }

    @Override
    public String export(ReportData data) {
        return "<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\">\n"
                + "  <Worksheet ss:Name=\"" + data.getTitle() + "\">\n"
                + "    <!-- Rendered " + data.getRows().size() + " rows -->\n"
                + "  </Worksheet>\n"
                + "</Workbook>\n";
    }
}
