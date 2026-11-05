package Task1;

import common.Logger;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class BankStructure {
    private BankStructure() {}
    public interface Unit {
        String name(); BigDecimal cash(); int operations(); void print(String indent);
    }
    public record CashDesk(String name, BigDecimal cash, int operations) implements Unit {
        public CashDesk {
            if (name == null || cash == null || cash.signum() < 0 || operations < 0)
                throw new IllegalArgumentException("Некоректна каса");
        }
        public void print(String indent) {
            Logger.log("CashDesk", indent + name + ": " + cash + " грн; операцій " + operations);
        }
    }
    public static final class Department implements Unit {
        private final String name;
        private final List<Unit> children = new ArrayList<>();
        public Department(String name) { this.name = name; }
        public Department add(Unit unit) {
            if (unit == null || unit == this || children.contains(unit)
                    || unit instanceof Department d && d.contains(this))
                throw new IllegalArgumentException("Цикл/повтор у структурі");
            children.add(unit); return this;
        }
        private boolean contains(Unit target) {
            return children.stream().anyMatch(u -> u == target || u instanceof Department d && d.contains(target));
        }
        public String name() { return name; }
        public BigDecimal cash() { return children.stream().map(Unit::cash).reduce(BigDecimal.ZERO, BigDecimal::add); }
        public int operations() { return children.stream().mapToInt(Unit::operations).sum(); }
        public void print(String indent) {
            Logger.log("Department", indent + name + ": " + cash() + " грн; операцій " + operations());
            children.forEach(u -> u.print(indent + "  "));
        }
    }
    public static void report(Unit unit) {
        Logger.log("Treasury", unit.name() + ": залишок " + unit.cash() + ", операцій " + unit.operations());
    }
}
