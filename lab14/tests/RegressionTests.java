import Task1.LoanIssuance.*;
import Task2.ExchangeRates.*;
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

        LoanCoordinator coordinator=new LoanCoordinator();
        coordinator.manager().submit(new Application("A",new BigDecimal("100000"),true));
        check(coordinator.payouts()==1,"Approved payout");
        coordinator.manager().submit(new Application("B",new BigDecimal("100000"),false));
        check(coordinator.payouts()==1,"Security denial stops process");
        coordinator.addCheck(new CollateralAppraiser(coordinator));
        coordinator.manager().submit(new Application("C",new BigDecimal("100000"),true));
        check(coordinator.payouts()==2,"Additional participant");
        coordinator.addCheck(new Check(coordinator) {
            public void check(Application app) { emit(Event.CHECK_FAILED,app); }
        });
        coordinator.manager().submit(new Application("D",new BigDecimal("100000"),true));
        check(coordinator.payouts()==2,"Extra check must pass before contract");
        RateService rates=new RateService(); ClientAlert client=new ClientAlert(new BigDecimal("41.00"));
        Conversion conversion=new Conversion(); int[] calls={0}; Task2.ExchangeRates.Observer receiver=r -> calls[0]++;
        rates.subscribe(client); rates.subscribe(conversion); rates.subscribe(receiver); rates.subscribe(receiver);
        rates.setRate(new BigDecimal("41.50")); check(client.notifications()==0,"Above threshold");
        rates.setRate(new BigDecimal("40.90")); check(client.notifications()==1,"Below threshold");
        money(conversion.convert(new BigDecimal("100")),"4090");
        rates.setRate(new BigDecimal("41.00")); check(client.notifications()==2,"Inclusive threshold");
        rates.unsubscribe(client); rates.setRate(new BigDecimal("40.80"));
        check(client.notifications()==2,"Unsubscribed client stays silent"); check(calls[0]==4,"Other observers still updated, no duplicates");
        fails(IllegalArgumentException.class, () -> rates.setRate(BigDecimal.ZERO));
        rates.subscribe(new MobileApp()); rates.setRate(new BigDecimal("40.70")); check(calls[0]==5,"New observer supported");
        Logger.log("Tests", "PASS: " + checks + " checks");
    }
}
