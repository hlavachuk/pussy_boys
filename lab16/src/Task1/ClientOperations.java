package Task1;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import common.Logger;

public final class ClientOperations {
    private ClientOperations() {}
    public record Operation(String id, String account, LocalDate date, BigDecimal amount) {}
    public static final class Archive {
        private final Map<String, Map<YearMonth, List<Operation>>> accounts = new LinkedHashMap<>();
        public void add(Operation operation) {
            accounts.computeIfAbsent(operation.account(), k -> new LinkedHashMap<>())
                    .computeIfAbsent(YearMonth.from(operation.date()), k -> new ArrayList<>()).add(operation);
        }
        private List<Operation> snapshot() {
            List<Operation> result = new ArrayList<>();
            accounts.values().forEach(months -> months.values().forEach(result::addAll));
            return result;
        }
        public Iterator<Operation> latest() { return new LatestIterator(snapshot()); }
        public Iterator<Operation> forYear(int year) { return new YearIterator(snapshot(), year); }
        public Iterator<Operation> monitoring() { return new MonitoringIterator(snapshot()); }
    }
    public abstract static class OperationIterator implements Iterator<Operation> {
        private final List<Operation> operations;
        private int index;
        protected OperationIterator(List<Operation> operations) { this.operations = List.copyOf(operations); }
        public boolean hasNext() { return index < operations.size(); }
        public Operation next() {
            if (!hasNext()) throw new NoSuchElementException();
            Operation operation = operations.get(index++);
            Logger.log(getClass().getSimpleName(), "Курсор " + (index - 1) + " -> " + index + ": " + operation);
            return operation;
        }
    }
    private static final Comparator<Operation> CHRONOLOGICAL = Comparator.comparing(Operation::date).thenComparing(Operation::id);
    public static final class LatestIterator extends OperationIterator {
        public LatestIterator(List<Operation> operations) {
            super(operations.stream().sorted(CHRONOLOGICAL.reversed()).toList());
        }
    }
    public static final class YearIterator extends OperationIterator {
        public YearIterator(List<Operation> operations, int year) {
            super(operations.stream().filter(o -> o.date().getYear() == year).sorted(CHRONOLOGICAL).toList());
        }
    }
    public static final class MonitoringIterator extends OperationIterator {
        public MonitoringIterator(List<Operation> operations) {
            super(operations.stream().filter(o -> o.amount().abs().compareTo(new BigDecimal("400000")) > 0)
                    .sorted(CHRONOLOGICAL).toList());
        }
    }
}
