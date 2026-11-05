import Task1.Branch;
import Task1.OnlineBanking;
import Task1.Treasury;
import Task2.ConnectionPool;
import Task2.Payment;
import Task2.PaymentConnection;
import Task2.PaymentService;
import Task3.Contract;
import common.Logger;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        task1();
        task2();
        task3();
    }

    // Завдання 1. Курси валют у відділеннях (Singleton)
    static void task1() {
        header("Завдання 1. Курси валют у відділеннях");
        Treasury treasury = new Treasury();
        treasury.setRate("USD", 41.20, 41.70);

        Branch branch3 = new Branch(3);
        Branch branch5 = new Branch(5);
        OnlineBanking online = new OnlineBanking();

        branch3.showRate("USD");
        branch5.showRate("USD");
        online.showRate("USD");

        Logger.log("Main", "Казначейство змінює курс");
        treasury.setRate("USD", 41.50, 42.00);
        branch3.showRate("USD");
        branch5.showRate("USD");
        online.showRate("USD");

        Logger.log("Main", "Ідентифікатори служби: казначейство=" + treasury.getServiceId()
                + ", відділення №3=" + branch3.getServiceId()
                + ", відділення №5=" + branch5.getServiceId()
                + ", онлайн-банкінг=" + online.getServiceId());
    }

    // Завдання 2. З'єднання з платіжною системою (Object Pool)
    static void task2() {
        header("Завдання 2. З'єднання з платіжною системою");
        ConnectionPool pool = new ConnectionPool(4);
        PaymentService service = new PaymentService(pool);

        long[] amounts = {2_500, 8_000, 120_000, 15_000, 3_200, 47_000, 900};
        Logger.log("Main", "Одночасно надходить 7 платежів");
        for (int i = 0; i < amounts.length; i++) {
            int number = 1001 + i;
            service.submit(new Payment(number, amounts[i], "UA-ACC-" + number, "UA-DST-" + number));
        }

        Logger.log("Main", "Проведення платежів");
        service.complete(1001);
        service.complete(1002);
        service.complete(1003);
        service.complete(1004);
        service.complete(1005);
        service.complete(1006);
        service.complete(1007);

        Logger.log("Main", "Статистика:");
        for (PaymentConnection c : pool.getAll()) {
            Logger.log("Main", c.getId() + ": платежів " + c.getPaymentsCount());
        }
        Logger.log("Main", "Загальна кількість встановлених з'єднань: " + pool.getCreatedCount());
    }

    // Завдання 3. Оформлення договорів (Prototype)
    static void task3() {
        header("Завдання 3. Оформлення договорів");
        Contract template = new Contract("Депозит Стабільний, 12 міс.", 14.5, 12, 10_000,
                "повернення вкладу зі ставкою 1% річних");
        List<String> templateBefore = template.describe();

        Contract c1 = template.copy();
        c1.setClientName("Коваленко І. П.");
        Contract c2 = template.copy();
        c2.setClientName("Мельник О. В.");
        Contract c3 = template.copy();
        c3.setClientName("Ткачук Н. С.");

        Logger.log("Main", "Договір Коваленка: додаємо додаткову угоду, змінюємо графік виплат і суму");
        c1.addAddendum("Дод. угода №1: автоматична пролонгація");
        c1.changePayout(12, 5_000.00);
        c1.setAmount(50_000);

        Logger.log("Main", "Типовий договір (ліворуч) і договір клієнта (праворуч):");
        printSideBySide(template.describe(), c1.describe());

        Logger.log("Main", "Типовий договір не змінився: " + template.describe().equals(templateBefore));
        Logger.log("Main", "Договір Мельника не зазнав змін: "
                + c2.describe().get(3).equals("Сума: 10 000 грн")
                + ", додаткових угод немає: " + c2.describe().contains("  (немає)"));
        Logger.log("Main", "Договір Ткачука не зазнав змін: " + c3.describe().contains("  (немає)"));
    }

    private static void header(String text) {
        System.out.println();
        System.out.println("===== " + text + " =====");
    }

    private static void printSideBySide(List<String> left, List<String> right) {
        int rows = Math.max(left.size(), right.size());
        for (int i = 0; i < rows; i++) {
            String l = i < left.size() ? left.get(i) : "";
            String r = i < right.size() ? right.get(i) : "";
            System.out.println(String.format("%-52s | %s", l, r));
        }
    }
}
