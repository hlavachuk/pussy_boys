import Task1.ClientOperations.*;
import Task2.OperatingDay.*;
import Task3.BankProducts.*;
import common.Logger;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
public final class RegressionTests {
    private static int checks;
    private static void check(boolean condition, String message) {
        checks++; if (!condition) throw new AssertionError(message);
    }
    private static void money(BigDecimal actual, String expected) {
        check(actual.compareTo(new BigDecimal(expected)) == 0, actual + " != " + expected);
    }
    private static void fails(Class<? extends Throwable> type, Runnable operation) {
        checks++;
        try { operation.run(); } catch (Throwable ex) {
            if (type.isInstance(ex)) return;
            throw new AssertionError("Unexpected exception", ex);
        }
        throw new AssertionError("Expected " + type.getSimpleName());
    }
    public static void main(String[] args) {

        Archive archive=new Archive();
        archive.add(new Operation("old","UA1",LocalDate.of(2025,12,31),new BigDecimal("500000")));
        archive.add(new Operation("boundary","UA2",LocalDate.of(2026,1,1),new BigDecimal("400000")));
        archive.add(new Operation("debit","UA1",LocalDate.of(2026,11,5),new BigDecimal("-400000.01")));
        archive.add(new Operation("last","UA2",LocalDate.of(2027,1,1),BigDecimal.ONE));
        Iterator<Operation> a=archive.latest(), b=archive.latest();
        check(a.next().id().equals("last"),"Newest across accounts"); a.next();
        check(b.next().id().equals("last"),"Independent cursor");
        List<Operation> year=new ArrayList<>(); archive.forYear(2026).forEachRemaining(year::add);
        check(year.size()==2,"Year boundaries"); check(year.get(0).id().equals("boundary"),"Chronological order");
        List<Operation> monitored=new ArrayList<>(); archive.monitoring().forEachRemaining(monitored::add);
        check(monitored.size()==2,"Strict threshold, absolute debit");
        check(monitored.stream().noneMatch(o -> o.id().equals("boundary")),"Exactly 400000 excluded");
        Iterator<Operation> empty=new Archive().latest(); check(!empty.hasNext(),"Empty iterator");
        fails(NoSuchElementException.class,empty::next); fails(UnsupportedOperationException.class,b::remove);
        Iterator<Operation> snapshot=archive.latest();
        archive.add(new Operation("new","UA1",LocalDate.of(2028,1,1),BigDecimal.TEN));
        check(snapshot.next().id().equals("last"),"Snapshot stable after storage change");
        Map<String,BigDecimal> initial=new HashMap<>(Map.of("A",new BigDecimal("10000"),"B",new BigDecimal("20000")));
        Ledger ledger=new Ledger(initial); History history=new History(); history.save("start",ledger);
        initial.put("A",BigDecimal.ZERO); money(ledger.balance("A"),"10000");
        ledger.adjust("A",new BigDecimal("1000")); ledger.adjust("B",new BigDecimal("-500")); history.save("second",ledger);
        ledger.adjust("A",new BigDecimal("-9000")); ledger.adjust("B",new BigDecimal("99000"));
        history.restore("second",ledger); money(ledger.balance("A"),"11000"); money(ledger.balance("B"),"19500");
        history.restore("start",ledger); money(ledger.balance("A"),"10000"); money(ledger.balance("B"),"20000");
        ledger.adjust("A",BigDecimal.TEN); history.restore("second",ledger); money(ledger.balance("A"),"11000");
        fails(IllegalArgumentException.class, () -> ledger.restore(new Checkpoint() {}));
        fails(IllegalArgumentException.class, () -> new Ledger(Map.of()).restore(ledger.save()));
        fails(IllegalArgumentException.class, () -> history.restore("missing",ledger));
        Portfolio portfolio=new Portfolio(List.of(new Deposit(new BigDecimal("100000")),new Loan(new BigDecimal("200000")),
            new Card(new BigDecimal("10000")),new Investment(new BigDecimal("50000"))));
        Reserves reserves=new Reserves(); portfolio.accept(reserves); money(reserves.total(),"13600");
        Profitability profit=new Profitability(); portfolio.accept(profit); money(profit.total(),"28700");
        portfolio.accept(new ClientReport());
        int[] visits={0}; portfolio.accept(new Visitor() {
            public void visit(Deposit d) { visits[0]++; } public void visit(Loan l) { visits[0]++; }
            public void visit(Card c) { visits[0]++; } public void visit(Investment i) { visits[0]++; }
        });
        check(visits[0]==4,"New visitor processes each product once");
        Reserves emptyReserves=new Reserves(); new Portfolio(List.of()).accept(emptyReserves); money(emptyReserves.total(),"0");
        Logger.log("Tests", "PASS: " + checks + " checks");
    }
}
