package Task2;

import common.Logger;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class ScheduledPayments {
    private ScheduledPayments() {}
    public static final class Account {
        private final String name;
        private BigDecimal balance;
        public Account(String name, BigDecimal balance) { this.name = name; this.balance = balance; }
        public BigDecimal balance() { return balance; }
        public void debit(BigDecimal amount) {
            if (amount.signum() <= 0 || balance.compareTo(amount) < 0)
                throw new IllegalStateException("Недостатньо коштів/некоректна сума");
            balance = balance.subtract(amount); Logger.log(name, "Списання " + amount + "; залишок " + balance);
        }
        public void credit(BigDecimal amount) {
            if (amount.signum() <= 0) throw new IllegalArgumentException("Некоректна сума");
            balance = balance.add(amount); Logger.log(name, "Зарахування " + amount + "; залишок " + balance);
        }
    }
    public interface Command {
        void execute(LocalDate day); void reverse(LocalDate day); void cancel();
        LocalDate due(); boolean pending(); String description();
    }
    public abstract static class PaymentCommand implements Command {
        private enum State { PENDING, EXECUTED, CANCELLED, REVERSED }
        private final Account source;
        private final Account target;
        private final BigDecimal amount;
        private final LocalDate due;
        private LocalDate executedOn;
        private State state = State.PENDING;
        protected PaymentCommand(Account source, Account target, BigDecimal amount, LocalDate due) {
            if (source == target || amount.signum() <= 0) throw new IllegalArgumentException("Некоректний платіж");
            this.source = source; this.target = target; this.amount = amount; this.due = due;
        }
        public final void execute(LocalDate day) {
            if (!pending() || day.isBefore(due)) throw new IllegalStateException("Платіж не готовий");
            Logger.log(getClass().getSimpleName(), "Виконую команду " + amount);
            source.debit(amount); target.credit(amount); executedOn = day; state = State.EXECUTED;
        }
        public final void reverse(LocalDate day) {
            if (state != State.EXECUTED || !day.equals(executedOn))
                throw new IllegalStateException("Сторно дозволено один раз у день виконання");
            Logger.log(getClass().getSimpleName(), "Сторную команду " + amount);
            target.debit(amount); source.credit(amount); state = State.REVERSED;
        }
        public final void cancel() {
            if (!pending()) throw new IllegalStateException("Скасувати можна лише платіж у черзі");
            state = State.CANCELLED; Logger.log(getClass().getSimpleName(), "Скасовано до виконання");
        }
        public LocalDate due() { return due; }
        public boolean pending() { return state == State.PENDING; }
        public String description() { return getClass().getSimpleName() + " " + amount + " на " + due + ": " + state; }
    }
    public static final class Transfer extends PaymentCommand {
        public Transfer(Account source, Account target, BigDecimal amount, LocalDate due) { super(source, target, amount, due); }
    }
    public static final class Utilities extends PaymentCommand {
        public Utilities(Account source, Account target, BigDecimal amount, LocalDate due) { super(source, target, amount, due); }
    }
    public static final class DepositTopUp extends PaymentCommand {
        public DepositTopUp(Account source, Account target, BigDecimal amount, LocalDate due) { super(source, target, amount, due); }
    }
    public static final class Scheduler {
        private final List<Command> commands = new ArrayList<>();
        private LocalDate today;
        public Scheduler(LocalDate today) { this.today = today; }
        /** Each occurrence has its own lifecycle and reversal date. No command object is reused. */
        public void scheduleRecurring(java.util.function.Function<LocalDate, Command> factory,
                                      LocalDate first, int intervalDays, int occurrences) {
            if (intervalDays <= 0 || occurrences <= 0) throw new IllegalArgumentException("Період повторення");
            for (int i = 0; i < occurrences; i++) {
                LocalDate due = first.plusDays((long) i * intervalDays);
                Command command = factory.apply(due);
                if (!command.due().equals(due)) throw new IllegalArgumentException("Невідповідна дата повтору");
                enqueue(command);
            }
        }
        public void enqueue(Command command) {
            if (commands.contains(command) || !command.pending() || command.due().isBefore(today))
                throw new IllegalArgumentException("Некоректна команда в черзі");
            commands.add(command); Logger.log("Scheduler", "Отримано " + command.description());
        }
        public void cancel(Command command) { requireKnown(command); command.cancel(); }
        public void reverse(Command command) { requireKnown(command); command.reverse(today); }
        private void requireKnown(Command command) {
            if (!commands.contains(command)) throw new IllegalArgumentException("Невідома команда");
        }
        public void advanceTo(LocalDate day) {
            if (day.isBefore(today)) throw new IllegalArgumentException("Дата не може йти назад");
            today = day; Logger.log("Scheduler", "Операційний день " + day);
            commands.stream().filter(Command::pending).filter(c -> !c.due().isAfter(day))
                    .sorted(java.util.Comparator.comparing(Command::due)).forEach(c -> c.execute(day));
        }
        public void printQueue() { commands.forEach(c -> Logger.log("Scheduler", c.description())); }
    }
}
