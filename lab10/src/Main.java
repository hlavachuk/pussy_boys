import Task1.*;
import Task2.*;
import Task3.*;
import common.Logger;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public final class Main {
    public static void main(String[] args) { task1(); task2(); task3(); }
    static void task1() {
        Logger.log("Main", "Завдання 1. Кредитна заявка — Builder");
        LoanApplication application = new LoanApplication.Builder().client("Коваленко І. П.")
            .amount(new BigDecimal("300000")).termMonths(36).purpose("Купівля автомобіля")
            .collateral("Автомобіль").guarantor("Мельник О. В.")
            .incomeCertificate("Довідка за 6 місяців").insured(true).build();
        Logger.log("Builder", application.toString());
        LoanDirector director = new LoanDirector();
        Logger.log("Director", director.consumerLoan("Ткачук Н. С.").toString());
        Logger.log("Director", director.carLoan("Бойко А. М.").toString());
        try {
            new LoanApplication.Builder().client("Коваленко І. П.").termMonths(36).build();
        } catch (IllegalStateException e) { Logger.log("Builder", e.getMessage()); }
    }
    static void task2() {
        Logger.log("Main", "Завдання 2. Пакети продуктів — Abstract Factory");
        Branch branch = new Branch();
        branch.openService("Коваленко", new RetailFactory());
        branch.openService("Мельник", new PremiumFactory());
        branch.openService("ТОВ Прут", new BusinessFactory());
        Logger.log("Main", "Додано студентську фабрику та продукти; код відділення той самий");
        branch.openService("Студент Бойко", new StudentFactory());
    }
    static void task3() {
        Logger.log("Main", "Завдання 3. Формування виписок — Factory Method");
        List<Transaction> transactions = List.of(
            new Transaction(LocalDate.of(2026, 9, 30), "До періоду", new BigDecimal("999")),
            new Transaction(LocalDate.of(2026, 10, 1), "Зарплата", new BigDecimal("30000")),
            new Transaction(LocalDate.of(2026, 10, 15), "Покупка", new BigDecimal("-2500.50")),
            new Transaction(LocalDate.of(2026, 10, 31), "Переказ", new BigDecimal("-1200")),
            new Transaction(LocalDate.of(2026, 11, 1), "Після періоду", new BigDecimal("777")));
        LocalDate from = LocalDate.of(2026, 10, 1), to = LocalDate.of(2026, 10, 31);
        new ClientStatementGenerator().generate(transactions, from, to, "Клієнт");
        new TaxStatementGenerator().generate(transactions, from, to, "Податкова");
        new AccountingStatementGenerator().generate(transactions, from, to, "Бухгалтерія");
        Logger.log("Main", "Додано аудиторський формувач та документ; загальний процес той самий");
        new AuditorStatementGenerator().generate(transactions, from, to, "Аудитор");
    }
}
