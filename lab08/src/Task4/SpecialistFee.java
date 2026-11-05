package Task4;

public class SpecialistFee implements FeePolicy {
    public double apply(double baseFee) { return baseFee * 1.4; }
    public String name() { return "Specialist"; }
}
