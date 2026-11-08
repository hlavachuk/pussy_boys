import Task1.*;
import Task2.*;
import Task3.InternationalPayments.*;
import Task4.AccountOpening.*;
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

        LocalDate day = LocalDate.of(2026,11,5);
        PaidBureau real = new PaidBureau(); BureauProxy proxy = new BureauProxy(real);
        User manager = new User("M", Role.CREDIT_MANAGER), cashier = new User("C", Role.CASHIER);
        fails(SecurityException.class, () -> proxy.history("A", cashier, day));
        check(real.requests() == 0, "Unauthorized call must not reach bureau");
        String first = proxy.history("A", manager, day);
        check(first.equals(proxy.history("A", manager, day)), "Cached result");
        proxy.history("A", manager, day); check(real.requests() == 1, "Only one paid request");
        fails(SecurityException.class, () -> proxy.history("A", cashier, day));
        check(real.requests() == 1, "Cached history also protected");
        proxy.history("A", manager, day.plusDays(1)); proxy.history("B", manager, day);
        check(real.requests() == 3, "Day and client separate cache entries");
        money(new BasicCard().monthlyFee(), "50");
        money(new Sms(new TravelInsurance(new Cashback(new BasicCard()))).monthlyFee(), "150");
        money(new Lounge(new HigherLimit(new BasicCard())).monthlyFee(), "180");
        money(new Sms(new Sms(new BasicCard())).monthlyFee(), "70");
        check(new Sms(new BasicCard()).description().contains("SMS"), "Description composition");
        Treasury treasury = new Treasury(new SwiftAdapter(new SwiftLibrary()));
        check(treasury.pay(new Payment("UA", "EU", new BigDecimal("1000"), "EUR")).accepted(), "Accepted transfer");
        check(!treasury.pay(new Payment("UA", "BLOCKED", BigDecimal.TEN, "EUR")).accepted(), "Rejected transfer");
        fails(IllegalArgumentException.class, () -> new Payment("UA:59:HACK", "EU", BigDecimal.ONE, "EUR"));
        fails(IllegalArgumentException.class, () -> new Payment("UA", "EU", BigDecimal.ZERO, "EUR"));
        fails(IllegalArgumentException.class, () -> new Payment("UA", "EU", new BigDecimal("1.001"), "EUR"));
        check(new Treasury(p -> new Status(true,"Alternative provider")).pay(
                new Payment("UA","EU",BigDecimal.ONE,"EUR")).accepted(), "Treasury independent of provider");
        Cards cards = new Cards(); OpeningFacade facade = new OpeningFacade(new Identification(), new Sanctions(),
                new Accounts(), cards, new OnlineBanking(), new Contracts());
        String account = new Channel("Branch", facade).open("A");
        check(account.equals("UA1"), "Branch account");
        fails(SecurityException.class, () -> facade.open("BLOCKED"));
        check(new Channel("Online",facade).open("B").equals("UA2"), "Denied client creates no account");
        cards.issue(account);
        Logger.log("Tests", "PASS: " + checks + " checks");
    }
}
