package Task3.application;

import Task2.Appointment;

public class ScheduleAppointmentController {
    private final AppointmentFactory factory;
    private final AppointmentRepository repository;

    public ScheduleAppointmentController(AppointmentFactory factory, AppointmentRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    public Appointment schedule(ScheduleAppointmentCommand command) {
        Appointment appointment = factory.create(command);
        repository.save(appointment);
        return appointment;
    }
}
