import common.Logger;
import java.math.BigDecimal;
import Task1.ProductClosing.*;
import Task2.CardLifecycle.Card;
import Task3.DepositInterest.*;

public final class Main {
    private Main() {}
    public static void main(String[] args) { task1(); task2(); task3(); }
    public static void task1() {
        Logger.log("Main", "1. Template Method: закриття продуктів");
        new DepositClosing("Олена", new BigDecimal("12000")).close();
        new LoanClosing("Іван", new BigDecimal("500")).close();
        new CardClosing("Олена").close();
    }
    public static void task2() {
        Logger.log("Main", "2. State: картка до операцій - Issued");
        Card card = new Card(); card.activate(); card.pay(new BigDecimal("100"));
        card.wrongPin(); card.wrongPin(); card.wrongPin(); card.pay(new BigDecimal("100"));
        card.unblock(); card.blockForever(); card.unblock();
        Logger.log("Main", "Кінцевий стан: " + card.stateName());
        card.reissue(); card.activate(); card.expire(); card.pay(BigDecimal.TEN); card.reissue();
    }
    public static void task3() {
        Logger.log("Main", "3. Strategy: умовна базова річна ставка 12%");
        BigDecimal rate = new BigDecimal("0.12");
        Deposit deposit = new Deposit(new BigDecimal("100000"), 12, new Simple(rate)); deposit.calculate();
        deposit.setStrategy(new MonthlyCapitalization(rate)); deposit.calculate();
        deposit.setStrategy(new DailyAccrual(rate)); deposit.calculate();
        Logger.log("Main", "Пролонгація: зміна схеми на ступінчасту");
        deposit.setStrategy(new Tiered()); deposit.calculate();
        deposit.setStrategy(new BonusRate()); deposit.calculate();
    }
}
