package Task3;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public record Transaction(LocalDate date, String description, BigDecimal amount) {
    public Transaction {
        Objects.requireNonNull(date);
        Objects.requireNonNull(description);
        Objects.requireNonNull(amount);
    }
}
