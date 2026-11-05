package Task4;

public class GeneralFee implements FeePolicy {
    public double apply(double baseFee) { return baseFee; }
    public String name() { return "General"; }
}
