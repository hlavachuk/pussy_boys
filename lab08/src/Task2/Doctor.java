package Task2;

import Task4.FeePolicies;
import Task4.FeePolicy;
//Те саме. consultationFee() рахує ціну консультації за категорією.
//Раніше це було в сервісі, тепер у лікаря, бо він знає свою ставку.
//Конструктор не дасть створити лікаря з від'ємною ціною чи неіснуючою категорією.
public class Doctor {
    private final int id;
    private final String name;
    private final String specialty;
    private final FeePolicy feePolicy;
    private final double baseConsultationFee;

    private Doctor(int id, String name, String specialty, FeePolicy feePolicy, double baseConsultationFee) {
        if (baseConsultationFee < 0) {
            throw new IllegalArgumentException("Базова вартість не може бути від'ємною");
        }
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.feePolicy = feePolicy;
        this.baseConsultationFee = baseConsultationFee;
    }

    public static Doctor create(int id, String name, String specialty, String category, double baseFee) {
        return new Doctor(id, name, specialty, FeePolicies.of(category), baseFee);
    }

    /** Information Expert: лікар знає свою ставку та категорію. */
    public double consultationFee() {
        return feePolicy.apply(baseConsultationFee);
    }

    public int id() { return id; }
    public String name() { return name; }
    public String specialty() { return specialty; }
    public String category() { return feePolicy.name(); }
}
