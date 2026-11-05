package Task3.infrastructure;

import Task2.Appointment;
import Task3.application.AppointmentRepository;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileAppointmentRepository implements AppointmentRepository {
    private final Path file;

    public FileAppointmentRepository(Path file) { this.file = file; }

    @Override
    public void save(Appointment a) {
        String line = "Запис #" + a.id() + "; пацієнт: " + a.patientName()
                + "; лікар: " + a.doctorName() + "; до сплати: " + a.patientPayable() + " грн" + System.lineSeparator();
        try {
            Files.writeString(file, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
