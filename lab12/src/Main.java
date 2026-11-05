import common.Logger;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import Task1.BankStructure;
import Task1.BankStructure.*;
import Task2.Documents.*;
import Task3.OperationHistory.*;

public final class Main {
    private Main() {}
    public static void main(String[] args) { task1(); task2(); task3(); }
    public static void task1() {
        Logger.log("Main", "1. Composite: структура банку");
        Department mini = new Department("Міні-відділення").add(new CashDesk("Каса М", new BigDecimal("20000"), 8));
        Department branch = new Department("Відділення Чернівці")
                .add(new CashDesk("Каса 1", new BigDecimal("100000"), 20)).add(mini);
        Department west = new Department("Західне управління").add(branch);
        Department east = new Department("Східне управління").add(new Department("Відділення Київ")
                .add(new CashDesk("Каса 2", new BigDecimal("80000"), 15)));
        Department bank = new Department("Головний офіс").add(west).add(east);
        bank.print(""); BankStructure.report(bank); BankStructure.report(west);
    }
    public static void task2() {
        Logger.log("Main", "2. Bridge: документи і способи надання");
        Receipt receipt = new Receipt("Платіж №7, 1000 грн", new Print());
        receipt.issue(); receipt.setDelivery(new Pdf()); receipt.issue();
        AccountStatement statement = new AccountStatement("UA7, залишок 5000 грн", new Signed());
        statement.issue(); statement.setDelivery(new Diia()); statement.issue();
        new PaymentOrder("Переказ 500 грн", new Pdf()).issue();
    }
    public static void task3() {
        Logger.log("Main", "3. Flyweight: 50 000 операцій, 20 торговців");
        MerchantFactory factory = new MerchantFactory(); List<Operation> history = new ArrayList<>();
        for (int i = 0; i < 50000; i++) {
            int m = i % 20;
            Merchant merchant = factory.get("M" + m, "Супермаркет " + m, "logo" + m,
                    "Продукти", "5411", "Адреса " + m);
            history.add(new Operation(BigDecimal.valueOf(10000L + i, 2),
                    LocalDate.of(2026, 11, 1).plusDays(i % 5), "UA" + (i % 100), merchant));
        }
        Logger.log("Main", "Операцій: " + history.size() + "; об'єктів торговців: " + factory.count());
        Logger.log("Main", history.get(0)); Logger.log("Main", history.get(20));
        Logger.log("Main", "Той самий об'єкт торговця (==): " + (history.get(0).merchant() == history.get(20).merchant()));
    }
}
