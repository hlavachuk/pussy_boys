import common.Logger;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import Task1.ClientOperations.*;
import Task2.OperatingDay.*;
import Task3.BankProducts.*;

public final class Main {
    private Main() {}
    public static void main(String[] args) { task1(); task2(); task3(); }
    public static void task1() {
        Logger.log("Main", "1. Iterator: до обходів архів містить 14 операцій на двох рахунках");
        Archive archive = new Archive();
        for (int i = 0; i < 14; i++) archive.add(new Operation("OP" + i, "UA" + (i % 2),
                LocalDate.of(2025, 12, 1).plusMonths(i), new BigDecimal(i % 3 == 0 ? "450000" : "400000")));
        Logger.log("Main", "10 останніх операцій з усіх рахунків");
        Iterator<Operation> latest = archive.latest();
        for (int i = 0; i < 10 && latest.hasNext(); i++) latest.next();
        Logger.log("Main", "Податковий обхід за 2026 рік хронологічно"); archive.forYear(2026).forEachRemaining(o -> {});
        Logger.log("Main", "Незалежні курсори: фінмоніторинг і онлайн-банкінг");
        Iterator<Operation> monitoring = archive.monitoring(); Iterator<Operation> other = archive.latest();
        while (monitoring.hasNext()) { monitoring.next(); if (other.hasNext()) other.next(); }
        Logger.log("Main", "Після обходів архів незмінний, курсори незалежні");
    }
    public static void task2() {
        Logger.log("Main", "2. Memento: контрольні точки дня");
        Ledger ledger = new Ledger(Map.of("UA1", new BigDecimal("10000"), "UA2", new BigDecimal("20000")));
        History history = new History(); ledger.print(); history.save("Початок дня", ledger);
        ledger.adjust("UA1", new BigDecimal("1000")); ledger.adjust("UA2", new BigDecimal("-500"));
        history.save("Перед коригуванням", ledger);
        ledger.adjust("UA1", new BigDecimal("-9000")); ledger.adjust("UA2", new BigDecimal("99000"));
        history.restore("Перед коригуванням", ledger); ledger.print(); history.restore("Початок дня", ledger); ledger.print();
    }
    public static void task3() {
        Logger.log("Main", "3. Visitor: умовні навчальні ставки резервів і прибутковості");
        Portfolio portfolio = new Portfolio(List.of(new Deposit(new BigDecimal("100000")), new Loan(new BigDecimal("200000")),
                new Card(new BigDecimal("10000")), new Investment(new BigDecimal("50000"))));
        Reserves reserves = new Reserves(); portfolio.accept(reserves); Logger.log("Main", "Резерви: " + reserves.total());
        Profitability profit = new Profitability(); portfolio.accept(profit); Logger.log("Main", "Прибутковість: " + profit.total());
        portfolio.accept(new ClientReport());
    }
}
