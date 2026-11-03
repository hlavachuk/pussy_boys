package Task1;
import java.math.BigDecimal;

// Готова незмінна заявка: приватні final-поля, без сеттерів.
public final class LoanApplication {
    private final String client, purpose, collateral, guarantor, incomeCertificate;
    private final BigDecimal amount;
    private final int termMonths;
    private final boolean insured;
    // Створення лише через Builder після перевірки даних.
    private LoanApplication(Builder b) {
        client = b.client; amount = b.amount; termMonths = b.termMonths; purpose = b.purpose;
        collateral = b.collateral; guarantor = b.guarantor;
        incomeCertificate = b.incomeCertificate; insured = b.insured;
    }
    // Геттери дозволяють лише читати дані заявки.
    public String getClient() { return client; }
    public BigDecimal getAmount() { return amount; }
    public int getTermMonths() { return termMonths; }
    public String getPurpose() { return purpose; }
    public String getCollateral() { return collateral; }
    public String getGuarantor() { return guarantor; }
    public String getIncomeCertificate() { return incomeCertificate; }
    public boolean isInsured() { return insured; }
    // Читабельний опис заявки для журналу.
    @Override public String toString() {
        return "Заявка{клієнт='" + client + "', сума=" + amount.toPlainString()
            + " грн, строк=" + termMonths + " міс., мета='" + purpose
            + "', застава='" + collateral + "', поручитель='" + guarantor
            + "', довідка='" + incomeCertificate + "', страхування=" + insured + "}";
    }

    
    // Будівельник накопичує дані; необов’язкові поля мають початкові значення.
    public static final class Builder {
        private String client, purpose = "не зазначено", collateral = "немає",
            guarantor = "немає", incomeCertificate = "немає";
        private BigDecimal amount;
        private int termMonths;
        private boolean insured;
        // return this дозволяє з’єднувати кроки в ланцюжок.
        public Builder client(String value) { client = value; return this; }
        public Builder amount(BigDecimal value) { amount = value; return this; }
        public Builder termMonths(int value) { termMonths = value; return this; }
        public Builder purpose(String value) { purpose = value; return this; }
        public Builder collateral(String value) { collateral = value; return this; }
        public Builder guarantor(String value) { guarantor = value; return this; }
        public Builder incomeCertificate(String value) { incomeCertificate = value; return this; }
        public Builder insured(boolean value) { insured = value; return this; }
        // Перевіряємо обов’язкові дані, потім створюємо готову заявку.
        public LoanApplication build() {
            if (client == null || client.isBlank()) fail("не вказано клієнта");
            if (amount == null) fail("не вказано суму кредиту");
            if (amount.signum() <= 0) fail("сума кредиту має бути більшою за нуль");
            if (termMonths <= 0) fail("строк кредиту має бути більшим за нуль");
            return new LoanApplication(this);
        }
        // Перериваємо створення; текст помилки виводить Main.
        private static void fail(String reason) {
            throw new IllegalStateException("Неможливо сформувати заявку: " + reason);
        }
    }
}
