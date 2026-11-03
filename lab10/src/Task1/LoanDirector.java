package Task1;
import java.math.BigDecimal;

// Director зберігає готові рецепти типових заявок.
public final class LoanDirector {
    public LoanApplication consumerLoan(String client) {
        return new LoanApplication.Builder().client(client).amount(new BigDecimal("50000"))
            .termMonths(12).purpose("Споживчий кредит").build();
    }
    public LoanApplication carLoan(String client) {
        return new LoanApplication.Builder().client(client).amount(new BigDecimal("300000"))
            .termMonths(36).purpose("Автокредит").collateral("Автомобіль")
            .incomeCertificate("Довідка про доходи за 6 місяців").insured(true).build();
    }
}
