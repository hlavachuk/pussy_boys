package Task2;

import common.Logger;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public final class OperatingDay {
    private OperatingDay() {}
    /** Marker interface is the only state view available to the caretaker. */
    public interface Checkpoint {}
    public static final class Ledger {
        private final Map<String, BigDecimal> balances = new java.util.TreeMap<>();
        private static final class Snapshot implements Checkpoint {
            private final Ledger owner;
            private final Map<String, BigDecimal> values;
            private Snapshot(Ledger owner, Map<String, BigDecimal> values) {
                this.owner = owner; this.values = Map.copyOf(values);
            }
        }
        public Ledger(Map<String, BigDecimal> initial) { balances.putAll(initial); }
        public BigDecimal balance(String account) {
            BigDecimal result = balances.get(account);
            if (result == null) throw new IllegalArgumentException("Невідомий рахунок");
            return result;
        }
        public void adjust(String account, BigDecimal delta) {
            BigDecimal before = balance(account);
            balances.put(account, before.add(delta));
            Logger.log("Ledger", account + ": " + before + " -> " + balance(account));
        }
        public Checkpoint save() { Logger.log("Ledger", "Створено закритий знімок"); return new Snapshot(this, balances); }
        public void restore(Checkpoint checkpoint) {
            if (!(checkpoint instanceof Snapshot s) || s.owner != this) throw new IllegalArgumentException("Чужий знімок");
            Logger.log("Ledger", "До відновлення " + balances);
            balances.clear(); balances.putAll(s.values); Logger.log("Ledger", "Після відновлення " + balances);
        }
        public void print() { Logger.log("Ledger", "Залишки " + balances); }
    }
    public static final class History {
        private final Map<String, Checkpoint> checkpoints = new LinkedHashMap<>();
        public void save(String name, Ledger ledger) {
            checkpoints.put(name, ledger.save()); Logger.log("History", "Збережено точку " + name);
        }
        public void restore(String name, Ledger ledger) {
            Checkpoint checkpoint = checkpoints.get(name);
            if (checkpoint == null) throw new IllegalArgumentException("Невідома контрольна точка");
            Logger.log("History", "Відновлюю " + name); ledger.restore(checkpoint);
        }
    }
}
