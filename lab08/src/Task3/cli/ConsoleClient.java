package Task3.cli;

import Task2.Appointment;
import Task3.application.ScheduleAppointmentController;

import java.util.Scanner;

public class ConsoleClient {
    private final ConsoleCommandParser parser;
    private final ScheduleAppointmentController controller;
    private final ReceiptFormatter formatter;

    public ConsoleClient(ConsoleCommandParser parser, ScheduleAppointmentController controller, ReceiptFormatter formatter) {
        this.parser = parser;
        this.controller = controller;
        this.formatter = formatter;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" Запис пацієнта на прийом ");
        System.out.print("Введіть ім'я пацієнта: ");
        String name = scanner.nextLine();
        System.out.print("Введіть категорію лікаря (General/Specialist/Professor): ");
        String category = scanner.nextLine();
        try {
            Appointment appointment = controller.schedule(parser.parse(name, category));
            System.out.println(formatter.format(appointment));
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }
}
