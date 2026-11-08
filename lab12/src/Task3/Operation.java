package Task3;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Operation(BigDecimal amount, LocalDate date, String account, Merchant merchant) {}
