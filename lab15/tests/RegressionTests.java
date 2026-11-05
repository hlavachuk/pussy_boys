import Task1.ProductClosing.*;
import Task2.CardLifecycle.*;
import Task3.DepositInterest.*;
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

        DepositClosing depositClose=new DepositClosing("A",new BigDecimal("12000"));
        check(depositClose.close(),"Deposit closes"); check(depositClose.closed(),"Closed flag");
        fails(IllegalStateException.class,depositClose::close);
        LoanClosing debt=new LoanClosing("A",BigDecimal.ONE); check(!debt.close(),"Debt stops procedure");
        check(!debt.closed(),"Debt product remains open");
        CardClosing cardClose=new CardClosing("A"); check(cardClose.close(),"Card closes");
        check(depositClose.certificate().equals(cardClose.certificate()),"Uniform certificate");
        check(new LoanClosing("A",BigDecimal.ZERO).close(),"Paid loan closes");
        Card card=new Card(); check(card.stateName().equals("Issued"),"Issued");
        card.pay(BigDecimal.TEN); check(card.stateName().equals("Issued"),"Payment denied before activation");
        card.activate(); card.wrongPin(); card.wrongPin(); check(card.stateName().equals("Active"),"Two PIN errors");
        card.wrongPin(); check(card.stateName().equals("TemporarilyBlocked"),"Third PIN error blocks");
        card.pay(BigDecimal.TEN); check(card.stateName().equals("TemporarilyBlocked"),"Payment cannot unblock");
        card.unblock(); check(card.stateName().equals("Active"),"Temporary unblock");
        card.wrongPin(); check(card.stateName().equals("Active"),"PIN counter reset");
        card.blockForever(); card.unblock(); check(card.stateName().equals("PermanentlyBlocked"),"Permanent block");
        card.expire(); check(card.stateName().equals("PermanentlyBlocked"),"Expiry cannot bypass permanent block");
        card.reissue(); check(card.stateName().equals("Issued"),"Reissue permanent card");
        card.activate(); card.expire(); card.unblock(); check(card.stateName().equals("Expired"),"Expired cannot unblock");
        card.reissue(); check(card.stateName().equals("Issued"),"Reissue expired card");
        card.transition(new State() {}); card.activate(); check(!card.stateName().equals("Active"),"New state without changing Card");
        Deposit deposit=new Deposit(new BigDecimal("100000"),12,new Simple(new BigDecimal("0.12")));
        money(deposit.calculate(),"12000");
        deposit.setStrategy(new MonthlyCapitalization(new BigDecimal("0.12"))); money(deposit.calculate(),"12682.50");
        deposit.setStrategy(new DailyAccrual(new BigDecimal("0.12"))); money(deposit.calculate(),"11835.62");
        deposit.setStrategy(new Tiered()); money(deposit.calculate(),"13000");
        deposit.setStrategy(new BonusRate()); money(deposit.calculate(),"14000");
        money(new Deposit(new BigDecimal("99999"),12,new Tiered()).calculate(),"9999.90");
        fails(IllegalArgumentException.class, () -> new Deposit(BigDecimal.ZERO,12,new BonusRate()));
        fails(IllegalArgumentException.class, () -> new Deposit(BigDecimal.ONE,0,new BonusRate()));
        Logger.log("Tests", "PASS: " + checks + " checks");
    }
}
