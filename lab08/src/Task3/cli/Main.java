package Task3.cli;

import Task3.application.AppointmentFactory;
import Task3.application.ScheduleAppointmentController;
import Task3.infrastructure.FileAppointmentRepository;
import Task3.infrastructure.NotifyingAppointmentRepository;
import Task3.infrastructure.SmsNotificationSender;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        var repository = new NotifyingAppointmentRepository(
                new FileAppointmentRepository(Path.of("appointments.log")),
                new SmsNotificationSender());
        var controller = new ScheduleAppointmentController(new AppointmentFactory(), repository);
        new ConsoleClient(new ConsoleCommandParser(), controller, new ReceiptFormatter()).run();
    }
}
