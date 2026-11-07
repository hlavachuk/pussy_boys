package Task3;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record StatementData(LocalDate from, LocalDate to, List<Transaction> transactions,
                            BigDecimal income, BigDecimal expense) {
    public StatementData {
        transactions = List.copyOf(transactions);
    }

    public BigDecimal net() {
        return income.subtract(expense);
    }

    public String summary() {
        return from + " — " + to + ", надходження: " + income + ", списання: "
                + expense + ", зміна балансу: " + net();
    }
}
