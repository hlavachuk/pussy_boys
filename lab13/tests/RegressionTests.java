import Task1.LoanApproval.*;
import Task2.ScheduledPayments.*;
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

        CreditManager manager=new CreditManager(); BranchHead head=new BranchHead(); Committee committee=new Committee();
        manager.then(head).then(committee).then(new Board());
        for (String amount:List.of("30000","50000"))
            check(manager.handle(new Application("A",new BigDecimal(amount))).equals("Кредитний менеджер"),"Manager boundary");
        for (String amount:List.of("50000.01","300000","500000"))
            check(manager.handle(new Application("A",new BigDecimal(amount))).equals("Керівник відділення"),"Head boundary");
        for (String amount:List.of("500000.01","3000000","5000000"))
            check(manager.handle(new Application("A",new BigDecimal(amount))).equals("Кредитний комітет"),"Committee boundary");
        check(manager.handle(new Application("A",new BigDecimal("10000000"))).equals("Правління"),"Board");
        head.then(new SecurityCheck(new BigDecimal("1000000"))).then(committee);
        check(manager.handle(new Application("A",new BigDecimal("3000000"))).equals("Кредитний комітет"),"Security forwards");
        manager.setLimit(new BigDecimal("40000"));
        check(manager.handle(new Application("A",new BigDecimal("45000"))).equals("Керівник відділення"),"Runtime limit");
        fails(IllegalArgumentException.class, () -> new Application("A",BigDecimal.ZERO));
        LocalDate day=LocalDate.of(2026,11,5);
        Account source=new Account("Source",new BigDecimal("10000")), target=new Account("Target",BigDecimal.ZERO);
        Scheduler scheduler=new Scheduler(day);
        Command a=new Transfer(source,target,new BigDecimal("1000"),day.plusDays(1));
        Command b=new Utilities(source,target,new BigDecimal("700"),day.plusDays(2));
        Command c=new DepositTopUp(source,target,new BigDecimal("2000"),day.plusDays(3));
        scheduler.enqueue(a); scheduler.enqueue(b); scheduler.enqueue(c); money(source.balance(),"10000");
        scheduler.cancel(b); scheduler.advanceTo(day); money(source.balance(),"10000");
        scheduler.advanceTo(day.plusDays(1)); money(source.balance(),"9000");
        scheduler.advanceTo(day.plusDays(3)); money(source.balance(),"7000"); scheduler.reverse(c);
        money(source.balance(),"9000"); money(target.balance(),"1000");
        fails(IllegalStateException.class, () -> scheduler.reverse(c));
        fails(IllegalStateException.class, () -> scheduler.reverse(a));
        fails(IllegalStateException.class, () -> scheduler.cancel(a));
        scheduler.advanceTo(day.plusDays(4)); money(source.balance(),"9000");
        fails(IllegalArgumentException.class, () -> scheduler.advanceTo(day));
        scheduler.scheduleRecurring(d -> new Utilities(source,target,new BigDecimal("100"),d),day.plusDays(5),1,2);
        scheduler.advanceTo(day.plusDays(6)); money(source.balance(),"8800"); money(target.balance(),"1200");
        Account empty=new Account("Empty",BigDecimal.ZERO);
        Command unfunded=new Transfer(empty,target,BigDecimal.ONE,day);
        fails(IllegalStateException.class, () -> unfunded.execute(day)); money(target.balance(),"1200");
        Logger.log("Tests", "PASS: " + checks + " checks");
    }
}
