package Task4;

public class InsuranceCoverage implements CoveragePolicy {
    private final double rate;

    public InsuranceCoverage(double rate) { this.rate = rate; }

    public double coverage(double cost) { return cost * rate; }
}
