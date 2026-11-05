package Task1;

import common.Logger;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class LoanIssuance {
    private LoanIssuance() {}
    public record Application(String client, BigDecimal amount, boolean securityPass) {}
    public enum Event { SUBMITTED, CHECK_PASSED, CHECK_FAILED, SIGNED, PAID }
    public interface Mediator { void notify(Participant sender, Event event, Application app); }
    public abstract static class Participant {
        protected final Mediator mediator;
        protected Participant(Mediator mediator) { this.mediator = mediator; }
        protected void emit(Event event, Application app) {
            Logger.log(getClass().getSimpleName(), "Подія " + event + ": " + app.client());
            mediator.notify(this, event, app);
        }
    }
    public static final class Manager extends Participant {
        public Manager(Mediator mediator) { super(mediator); }
        public void submit(Application app) { emit(Event.SUBMITTED, app); }
        public void inform(String result) { Logger.log("Manager", result); }
    }
    public abstract static class Check extends Participant {
        protected Check(Mediator mediator) { super(mediator); }
        public abstract void check(Application app);
    }
    public static final class Scoring extends Check {
        public Scoring(Mediator mediator) { super(mediator); }
        public void check(Application app) { emit(app.amount().signum() > 0 ? Event.CHECK_PASSED : Event.CHECK_FAILED, app); }
    }
    public static final class Security extends Check {
        public Security(Mediator mediator) { super(mediator); }
        public void check(Application app) { emit(app.securityPass() ? Event.CHECK_PASSED : Event.CHECK_FAILED, app); }
    }
    public static final class CollateralAppraiser extends Check {
        public CollateralAppraiser(Mediator mediator) { super(mediator); }
        public void check(Application app) { emit(Event.CHECK_PASSED, app); }
    }
    public static final class Legal extends Participant {
        public Legal(Mediator mediator) { super(mediator); }
        public void prepareAndSign(Application app) {
            Logger.log("Legal", "Підготовлено договір; клієнт підписав"); emit(Event.SIGNED, app);
        }
    }
    public static final class Cashier extends Participant {
        private int payouts;
        public Cashier(Mediator mediator) { super(mediator); }
        public int payouts() { return payouts; }
        public void pay(Application app) {
            payouts++; Logger.log("Cashier", "Видано " + app.amount() + " грн"); emit(Event.PAID, app);
        }
    }
    public static final class LoanCoordinator implements Mediator {
        private final Manager manager = new Manager(this);
        private final Legal legal = new Legal(this);
        private final Cashier cashier = new Cashier(this);
        private final List<Check> checks = new ArrayList<>();
        private final java.util.Set<Participant> passed = new java.util.HashSet<>();
        private Application current;
        private enum Stage { IDLE, CHECKING, CONTRACT, PAYMENT, DONE, REJECTED }
        private Stage stage = Stage.IDLE;
        public LoanCoordinator() { checks.add(new Scoring(this)); checks.add(new Security(this)); }
        public Manager manager() { return manager; }
        public int payouts() { return cashier.payouts(); }
        public void addCheck(Check check) {
            if (check.mediator != this || checks.contains(check)) throw new IllegalArgumentException("Некоректний учасник");
            checks.add(check);
        }
        public void notify(Participant sender, Event event, Application app) {
            Logger.log("LoanCoordinator", "Отримано " + event + " від " + sender.getClass().getSimpleName());
            if (event == Event.SUBMITTED && sender == manager) {
                current = app; passed.clear(); stage = Stage.CHECKING;
                for (Check check : List.copyOf(checks)) {
                    if (stage != Stage.CHECKING) break;
                    check.check(app);
                }
            } else if (app == current && stage == Stage.CHECKING && checks.contains(sender)) {
                if (event == Event.CHECK_FAILED) {
                    stage = Stage.REJECTED; manager.inform("Відмова: процес зупинено, кошти не видаються");
                } else if (event == Event.CHECK_PASSED) {
                    passed.add(sender);
                    if (passed.size() == checks.size()) {
                        stage = Stage.CONTRACT; legal.prepareAndSign(app);
                    }
                }
            } else if (app == current && stage == Stage.CONTRACT && sender == legal && event == Event.SIGNED) {
                stage = Stage.PAYMENT; cashier.pay(app);
            } else if (app == current && stage == Stage.PAYMENT && sender == cashier && event == Event.PAID) {
                stage = Stage.DONE; manager.inform("Кредит видано після всіх перевірок і підписання");
            }
        }
    }
}
