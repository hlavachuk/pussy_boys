package Task2;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ReportData data = new ReportData("Sales Q3", LocalDateTime.now());
        data.getHeaders().addAll(List.of("Product", "Amount"));
        data.getRows().add(List.of("Laptop", "10"));
        data.getRows().add(List.of("Phone", "25"));

        ReportExporter exporter = new ReportExporter(List.of(
                new PdfExportStrategy(),
                new ExcelExportStrategy(),
                new CsvExportStrategy(),
                new JsonExportStrategy(),
                new XmlExportStrategy()));   // новий формат — лише тут реєструємо

        for (String format : List.of("PDF", "EXCEL", "CSV", "JSON", "XML")) {
            System.out.println(exporter.export(data, format));
        }
    }
}
