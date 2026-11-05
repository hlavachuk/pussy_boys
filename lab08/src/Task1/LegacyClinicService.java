package Task1;

import java.util.Scanner;

public class LegacyClinicService {
    public void scheduleAppointmentConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Запис пацієнта на прийом ===");
        System.out.print("Введіть ім'я пацієнта: ");
        String name = scanner.nextLine();
        System.out.print("Введіть категорію лікаря (General/Specialist/Professor): ");
        String docCategory = scanner.nextLine();

        //Об'єкт заповнюється ззовні по одному полю, це порушення інкапсуляції

        Patient patient = new Patient();
        patient.id = 22; patient.fullName = name; patient.hasInsurance = true; patient.insurancePolicyNumber = "INS-99201"; patient.phone = "+380684445566";

        Doctor doctor = new Doctor();
        doctor.id = 101; doctor.name = "Др. Ковальчук"; doctor.specialty = "Кардіолог"; doctor.category = docCategory; doctor.baseConsultationFee = 800.0;

        Appointment appointment = new Appointment();
        appointment.appointmentId = 3001;
        appointment.patient = patient;
        appointment.doctor = doctor;
        appointment.status = "Scheduled";


        //Тут сервіс лізе в чужі поля, це High Coupling і порушення Закону Деметри

        double fee = appointment.doctor.baseConsultationFee;

        //Це спагетті-код: каскад if-else і магічні числа

        if ("Professor".equalsIgnoreCase(appointment.doctor.category)) {
            fee *= 2.0;
        } else if ("Specialist".equalsIgnoreCase(appointment.doctor.category)) {
            fee *= 1.4;
        }

        appointment.consultationCost = fee;

        double coverage = 0.0;
        if (appointment.patient.hasInsurance) {
            coverage = appointment.consultationCost * 0.80;
        }

        appointment.insuranceCoverage = coverage;
        appointment.patientPayable = appointment.consultationCost - appointment.insuranceCoverage;
        appointment.status = "Confirmed";

        String invoice = "Квитанція прийому #" + appointment.appointmentId +
                "\nПацієнт: " + appointment.patient.fullName +
                "\nЛікар: " + appointment.doctor.name + " (" + appointment.doctor.category + ")" +
                "\nВартість: " + appointment.consultationCost + " грн" +
                "\nПокриття страховкою: " + appointment.insuranceCoverage + " грн" +
                "\nДо сплати пацієнтом: " + appointment.patientPayable + " грн";
        System.out.println(invoice);

        System.out.println("[Електронна карта] Запис #" + appointment.appointmentId + " збережено у файл appointments.log.");
        System.out.println("[SMS] Нагадування про прийом надіслано на номер " + appointment.patient.phone);
    }
}
