package Task3.infrastructure;

import Task2.Appointment;
import Task3.application.AppointmentRepository;
import Task3.application.NotificationSender;

public class NotifyingAppointmentRepository implements AppointmentRepository {
    private final AppointmentRepository delegate;
    private final NotificationSender sender;

    public NotifyingAppointmentRepository(AppointmentRepository delegate, NotificationSender sender) {
        this.delegate = delegate;
        this.sender = sender;
    }

    @Override
    public void save(Appointment appointment) {
        delegate.save(appointment);
        sender.send(appointment.patientPhone(), "Нагадування про прийом #" + appointment.id());
    }
}
