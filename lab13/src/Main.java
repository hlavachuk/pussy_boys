import common.Logger;
import java.math.BigDecimal;
import java.time.LocalDate;
import Task1.LoanApproval.*;
import Task2.ScheduledPayments.*;

public final class Main {
    private Main() {}
    public static void main(String[] args) { task1(); task2(); }
    public static void task1() {
        Logger.log("Main", "1. Chain of Responsibility: погодження кредиту");
        CreditManager manager = new CreditManager(); BranchHead head = new BranchHead();
        Committee committee = new Committee(); Board board = new Board();
        manager.then(head).then(committee).then(board);
        for (String amount : new String[]{"30000", "300000", "3000000", "10000000"})
            manager.handle(new Application("Клієнт-7", new BigDecimal(amount)));
        SecurityCheck security = new SecurityCheck(new BigDecimal("1000000"));
        head.then(security).then(committee);
        Logger.log("Main", "Додано рівень служби безпеки без зміни погоджувачів");
        manager.handle(new Application("Клієнт-7", new BigDecimal("3000000")));
    }
    public static void task2() {
        Logger.log("Main", "2. Command: відкладені платежі");
        LocalDate day = LocalDate.of(2026, 11, 5);
        Account source = new Account("Поточний рахунок", new BigDecimal("10000"));
        Scheduler scheduler = new Scheduler(day);
        Command transfer = new Transfer(source, new Account("Отримувач", BigDecimal.ZERO), new BigDecimal("1000"), day.plusDays(1));
        Command utilities = new Utilities(source, new Account("Комунальні", BigDecimal.ZERO), new BigDecimal("700"), day.plusDays(2));
        Command deposit = new DepositTopUp(source, new Account("Депозит", BigDecimal.ZERO), new BigDecimal("2000"), day.plusDays(3));
        scheduler.enqueue(transfer); scheduler.enqueue(utilities); scheduler.enqueue(deposit); scheduler.printQueue();
        scheduler.cancel(utilities); scheduler.advanceTo(day.plusDays(1)); scheduler.advanceTo(day.plusDays(3));
        scheduler.reverse(deposit); scheduler.printQueue();
        Logger.log("Main", "Початковий залишок 10000; кінцевий залишок " + source.balance());
    }
}
