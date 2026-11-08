import common.Logger;
import java.math.BigDecimal;
import java.time.LocalDate;
import Task1.*;
import Task2.*;
import Task3.InternationalPayments.*;
import Task4.AccountOpening.*;

public final class Main {
    private Main() {}
    public static void main(String[] args) { task1(); task2(); task3(); task4(); }
    public static void task1() {
        Logger.log("Main", "1. Proxy: бюро кредитних історій");
        PaidBureau real = new PaidBureau(); Lending lending = new Lending(new BureauProxy(real));
        User manager = new User("Менеджер", Role.CREDIT_MANAGER);
        LocalDate day = LocalDate.of(2026, 11, 5);
        for (int i = 0; i < 3; i++) lending.check("Клієнт-7", manager, day);
        Logger.log("Main", "Кількість платних запитів: " + real.requests());
        try { lending.check("Клієнт-7", new User("Касир", Role.CASHIER), day); }
        catch (SecurityException expected) { Logger.log("Main", expected.getMessage()); }
    }
    public static void task2() {
        Logger.log("Main", "2. Decorator: опції карток");
        Card base = new BasicCard(); showCard(base);
        showCard(new Sms(new TravelInsurance(new Cashback(base))));
        showCard(new Lounge(new HigherLimit(new BasicCard())));
    }
    private static void showCard(Card card) {
        Logger.log("CardSystem", card.description() + ": " + card.monthlyFee() + " грн/місяць");
    }

    public static void task3() {
        Logger.log("Main", "3. Adapter: SWIFT");
        Treasury treasury = new Treasury(new SwiftAdapter(new SwiftLibrary()));
        treasury.pay(new Payment("UA-SENDER", "EU-RECIPIENT", new BigDecimal("1000.00"), "EUR"));
        treasury.pay(new Payment("UA-SENDER", "BLOCKED", new BigDecimal("1000.00"), "EUR"));
    }
    public static void task4() {
        Logger.log("Main", "4. Facade: відкриття рахунку");
        Cards cards = new Cards();
        OpeningFacade facade = new OpeningFacade(new Identification(), new Sanctions(), new Accounts(),
                cards, new OnlineBanking(), new Contracts());
        String first = new Channel("Відділення", facade).open("Олена");
        new Channel("Онлайн-канал", facade).open("Іван");
        Logger.log("Main", "Окреме використання підсистеми карток"); cards.issue(first);
    }
}
