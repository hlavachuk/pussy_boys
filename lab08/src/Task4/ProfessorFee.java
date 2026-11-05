package Task4;

public class ProfessorFee implements FeePolicy {
    public double apply(double baseFee) { return baseFee * 2.0; }
    public String name() { return "Professor"; }
}
