package Task1;

import common.Logger;
import java.math.BigDecimal;
import java.math.RoundingMode;

public final class ProductClosing {
    private ProductClosing() {}
    public abstract static class ClosingProcedure {
        private final String client;
        private final BigDecimal debt;
        private boolean closed;
        protected ClosingProcedure(String client, BigDecimal debt) { this.client = client; this.debt = debt; }
        public final boolean close() {
            if (closed) throw new IllegalStateException("Продукт уже закрито");
            Logger.log("ClosingProcedure", "1. Ідентифікувати клієнта: " + client);
            Logger.log("ClosingProcedure", "2. Перевірити заборгованість: " + debt);
            if (debt.signum() > 0) {
                Logger.log("ClosingProcedure", "Зупинка: є заборгованість"); return false;
            }
            Logger.log("ClosingProcedure", "3. Фінальні розрахунки " + getClass().getSimpleName());
            settle(); withholdTax();
            closed = true; Logger.log("ClosingProcedure", "4. Продукт закрито");
            Logger.log("ClosingProcedure", "5. " + certificate()); return true;
        }
        protected abstract void settle();
        protected void withholdTax() { Logger.log("ClosingProcedure", "Податок: додаткове утримання не потрібне"); }
        public final String certificate() { return "Довідка: банківський продукт клієнта " + client + " закрито"; }
        public final boolean closed() { return closed; }
    }
    public static final class DepositClosing extends ClosingProcedure {
        private final BigDecimal interest;
        public DepositClosing(String client, BigDecimal interest) { super(client, BigDecimal.ZERO); this.interest = interest; }
        protected void settle() { Logger.log("DepositClosing", "Нараховано відсотки " + interest); }
        protected void withholdTax() {
            Logger.log("DepositClosing", "Утримано умовний податок 20%: "
                    + interest.multiply(new BigDecimal("0.20")).setScale(2, RoundingMode.HALF_UP));
        }
    }
    public static final class LoanClosing extends ClosingProcedure {
        public LoanClosing(String client, BigDecimal debt) { super(client, debt); }
        protected void settle() { Logger.log("LoanClosing", "Звірено остаточне погашення кредиту"); }
    }
    public static final class CardClosing extends ClosingProcedure {
        public CardClosing(String client) { super(client, BigDecimal.ZERO); }
        protected void settle() { Logger.log("CardClosing", "Повернено залишок, припинено комісію картки"); }
    }
}
