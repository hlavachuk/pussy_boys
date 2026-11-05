package Task3;

import java.math.BigDecimal;

public class PayrollService {
    public void processYearEndBonuses(PaidEmployee employee, double rating) {
        BigDecimal bonus = employee.calculateAnnualBonus(rating);
        System.out.println("Бонус для " + employee.getFullName() + ": грн " + bonus);
    }
}
