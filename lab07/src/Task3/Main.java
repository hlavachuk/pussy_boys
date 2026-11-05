package Task3;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        PayrollService payroll = new PayrollService();

        PaidEmployee paid = new PaidEmployee("E1", "Іван Петренко", new BigDecimal("1000"));
        payroll.processYearEndBonuses(paid, 4.0);

        VolunteerEmployee volunteer = new VolunteerEmployee("V1", "Олена Коваленко", "Red Cross");
        System.out.println(volunteer.getFullName() + " волонтери в " + volunteer.getOrganizationName());

        try {
            paid.setSalary(new BigDecimal("-1"));
        } catch (IllegalArgumentException e) {
            System.out.println("Fail-fast: " + e.getMessage());
        }
    }
}
