import common.Logger;
import java.math.BigDecimal;
import Task1.LoanIssuance.*;
import Task2.ExchangeRates.*;

public final class Main {
    private Main() {}
    public static void main(String[] args) { task1(); task2(); }
    public static void task1() {
        Logger.log("Main", "1. Mediator: видача кредиту");
        LoanCoordinator coordinator = new LoanCoordinator();
        coordinator.manager().submit(new Application("Олена", new BigDecimal("300000"), true));
        coordinator.manager().submit(new Application("Іван", new BigDecimal("300000"), false));
        coordinator.addCheck(new CollateralAppraiser(coordinator));
        Logger.log("Main", "Підключено оцінювача застави, існуючих учасників не змінено");
        coordinator.manager().submit(new Application("Марія", new BigDecimal("500000"), true));
    }
    public static void task2() {
        Logger.log("Main", "2. Observer: зміна курсу валют");
        RateService service = new RateService(); ClientAlert client = new ClientAlert(new BigDecimal("41.00"));
        service.subscribe(new BranchBoard()); service.subscribe(new OnlineBanking()); service.subscribe(client);
        service.subscribe(new Conversion());
        service.setRate(new BigDecimal("41.50")); service.setRate(new BigDecimal("40.90"));
        Logger.log("Main", "Клієнт відписується; підключається мобільний застосунок");
        service.unsubscribe(client); service.subscribe(new MobileApp()); service.setRate(new BigDecimal("40.80"));
    }
}
