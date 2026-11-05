package Task2;

import Task4.CoveragePolicy;
import Task4.InsuranceCoverage;
import Task4.NoCoverage;
// Поля private final, сеттерів немає.
// Конструктор приватний, створити пацієнта можна тільки через Patient.insured(...)
// або Patient.uninsured(...). Це Creator. Метод coverageFor() рахує покриття страховки,
// бо страховку знає пацієнт. Це Information Expert.

public class Patient {
    private static final double INSURANCE_RATE = 0.80;

    private final int id;
    private final String fullName;
    private final String insurancePolicyNumber; // null, якщо немає страховки
    private final String phone;
    private final CoveragePolicy coveragePolicy;

    private Patient(int id, String fullName, String insurancePolicyNumber, String phone, CoveragePolicy coveragePolicy) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Ім'я пацієнта не може бути порожнім");
        }
        this.id = id;
        this.fullName = fullName;
        this.insurancePolicyNumber = insurancePolicyNumber;
        this.phone = phone;
        this.coveragePolicy = coveragePolicy;
    }

    public static Patient insured(int id, String fullName, String policyNumber, String phone) {
        return new Patient(id, fullName, policyNumber, phone, new InsuranceCoverage(INSURANCE_RATE));
    }

    public static Patient uninsured(int id, String fullName, String phone) {
        return new Patient(id, fullName, null, phone, new NoCoverage());
    }

    /** Information Expert: пацієнт знає свою страховку. */
    public double coverageFor(double cost) {
        return coveragePolicy.coverage(cost);
    }

    public int id() { return id; }
    public String fullName() { return fullName; }
    public String phone() { return phone; }
    public String insurancePolicyNumber() { return insurancePolicyNumber; }
}
