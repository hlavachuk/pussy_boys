package Task3;

import common.Logger;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public abstract class StatementGenerator {
    public final StatementDocument generate(List<Transaction> source, LocalDate from,
                                            LocalDate to, String recipient) {
        if (from == null || to == null || from.isAfter(to)) {
            throw new IllegalArgumentException("Некоректний період");
        }
        if (recipient == null || recipient.isBlank()) {
            throw new IllegalArgumentException("Не вказано адресата");
        }

        List<Transaction> transactions = source.stream()
                .filter(t -> !t.date().isBefore(from) && !t.date().isAfter(to))
                .toList();
        BigDecimal income = BigDecimal.ZERO;
        BigDecimal expense = BigDecimal.ZERO;
        for (Transaction transaction : transactions) {
            BigDecimal amount = transaction.amount();
            if (amount.signum() >= 0) {
                income = income.add(amount);
            } else {
                expense = expense.subtract(amount);
            }
        }
        StatementDocument document = createDocument(new StatementData(from, to, transactions, income, expense));
        Logger.log(getClass().getSimpleName(), "Надсилання " + document.format() + " для " + recipient);
        Logger.log(document.format(), document.render());
        return document;
    }

    protected abstract StatementDocument createDocument(StatementData data);
}
