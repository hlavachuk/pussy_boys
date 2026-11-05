package Task1;

import common.Logger;
import java.math.BigDecimal;

public final class LoanApproval {
    private LoanApproval() {}
    public record Application(String client, BigDecimal amount) {
        public Application {
            if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("Сума має бути додатна");
        }
    }
    public abstract static class Handler {
        protected Handler next;
        public Handler then(Handler next) { this.next = next; return next; }
        protected String forward(Application app) {
            if (next == null) throw new IllegalStateException("Немає рівня погодження");
            return next.handle(app);
        }
        public abstract String handle(Application app);
    }
    public static class Approver extends Handler {
        private final String name;
        private BigDecimal limit;
        public Approver(String name, BigDecimal limit) { this.name = name; setLimit(limit); }
        public final void setLimit(BigDecimal limit) {
            if (limit == null || limit.signum() <= 0) throw new IllegalArgumentException("Ліміт");
            this.limit = limit;
        }
        public String handle(Application app) {
            Logger.log(name, "Отримано заявку " + app.client() + ": " + app.amount());
            if (app.amount().compareTo(limit) <= 0) {
                Logger.log(name, "Погоджено " + app.amount()); return name;
            }
            Logger.log(name, "Ліміт " + limit + ": передаю далі"); return forward(app);
        }
    }
    public static final class CreditManager extends Approver {
        public CreditManager() { super("Кредитний менеджер", new BigDecimal("50000")); }
    }
    public static final class BranchHead extends Approver {
        public BranchHead() { super("Керівник відділення", new BigDecimal("500000")); }
    }
    public static final class Committee extends Approver {
        public Committee() { super("Кредитний комітет", new BigDecimal("5000000")); }
    }
    public static final class Board extends Handler {
        public String handle(Application app) {
            Logger.log("Правління", "Отримано й погоджено " + app.amount()); return "Правління";
        }
    }
    public static final class SecurityCheck extends Handler {
        private final BigDecimal threshold;
        public SecurityCheck(BigDecimal threshold) { this.threshold = threshold; }
        public String handle(Application app) {
            if (app.amount().compareTo(threshold) > 0)
                Logger.log("Служба безпеки", "Перевірено заявку понад " + threshold + ": " + app.amount());
            return forward(app);
        }
    }
}
