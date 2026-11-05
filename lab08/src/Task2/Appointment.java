package Task2;

import java.util.Objects;
//«Приватний конструктор сам бере вартість у лікаря, покриття в пацієнта і рахує суму до сплати.
// Ззовні нічого не присвоюється. schedule(...) це фабрика, яка повертає запис одразу підтвердженим.
// Методи patientName() та doctorCategory() це Закон Деметри: зовнішній код не лізе ланцюжком у Patient чи Doctor.»
public class Appointment {
    public enum Status { SCHEDULED, CONFIRMED }

    private final int id;
    private final Patient patient;
    private final Doctor doctor;
    private final double consultationCost;
    private final double insuranceCoverage;
    private final double patientPayable;
    private Status status;

    private Appointment(int id, Patient patient, Doctor doctor) {
        this.id = id;
        this.patient = Objects.requireNonNull(patient);
        this.doctor = Objects.requireNonNull(doctor);
        this.consultationCost = doctor.consultationFee();
        this.insuranceCoverage = patient.coverageFor(consultationCost);
        this.patientPayable = consultationCost - insuranceCoverage;
        this.status = Status.SCHEDULED;
    }

    /** Creator / Static Factory: запис завжди створюється валідним і підтвердженим. */
    public static Appointment schedule(int id, Patient patient, Doctor doctor) {
        Appointment appointment = new Appointment(id, patient, doctor);
        appointment.confirm();
        return appointment;
    }

    private void confirm() {
        this.status = Status.CONFIRMED;
    }

    // Закон Деметри: делегування замість appointment.patient().fullName()
    public String patientName() { return patient.fullName(); }
    public String patientPhone() { return patient.phone(); }
    public String doctorName() { return doctor.name(); }
    public String doctorCategory() { return doctor.category(); }

    public int id() { return id; }
    public double consultationCost() { return consultationCost; }
    public double insuranceCoverage() { return insuranceCoverage; }
    public double patientPayable() { return patientPayable; }
    public Status status() { return status; }
}
