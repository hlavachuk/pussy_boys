package Task3;

import java.math.BigDecimal;
import java.util.Objects;

public class PaidEmployee extends Employee {
    private BigDecimal baseSalary;

    public PaidEmployee(String employeeId, String fullName, BigDecimal baseSalary) {
        super(employeeId, fullName);
        this.baseSalary = requireNonNegative(baseSalary);
    }

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    public void setSalary(BigDecimal newSalary) {
        this.baseSalary = requireNonNegative(newSalary);
    }

    public BigDecimal calculateAnnualBonus(double performanceRating) {
        if (performanceRating < 1.0 || performanceRating > 5.0) {
            throw new IllegalArgumentException("Invalid rating");
        }
        return baseSalary.multiply(BigDecimal.valueOf(0.10 * performanceRating));
    }

    private static BigDecimal requireNonNegative(BigDecimal salary) {
        Objects.requireNonNull(salary, "Salary не може бути null");
        if (salary.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Salary не може бути від'ємним");
        }
        return salary;
    }
}
