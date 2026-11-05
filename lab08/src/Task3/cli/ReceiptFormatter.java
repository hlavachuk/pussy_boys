package Task3.cli;

import Task2.Appointment;

import java.util.Locale;

public class ReceiptFormatter {
    public String format(Appointment a) {
        return "Квитанція прийому #" + a.id() +
                "\nПацієнт: " + a.patientName() +
                "\nЛікар: " + a.doctorName() + " (" + a.doctorCategory() + ")" +
                "\nВартість: " + money(a.consultationCost()) + " грн" +
                "\nПокриття страховкою: " + money(a.insuranceCoverage()) + " грн" +
                "\nДо сплати пацієнтом: " + money(a.patientPayable()) + " грн";
    }

    private String money(double value) {
        return String.format(Locale.US, "%.2f", value);
    }
}
