package Task3;

import common.Logger;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/** Percentages are explicit educational assumptions, not actual NBU requirements. */
public final class BankProducts {
    private BankProducts() {}
    public interface Product { void accept(Visitor visitor); BigDecimal amount(); }
    public interface Visitor {
        void visit(Deposit deposit); void visit(Loan loan); void visit(Card card); void visit(Investment investment);
    }
    public record Deposit(BigDecimal amount) implements Product { public void accept(Visitor visitor) { visitor.visit(this); } }
    public record Loan(BigDecimal amount) implements Product { public void accept(Visitor visitor) { visitor.visit(this); } }
    public record Card(BigDecimal amount) implements Product { public void accept(Visitor visitor) { visitor.visit(this); } }
    public record Investment(BigDecimal amount) implements Product { public void accept(Visitor visitor) { visitor.visit(this); } }
    public static final class Portfolio {
        private final List<Product> products;
        public Portfolio(List<Product> products) { this.products = List.copyOf(products); }
        public void accept(Visitor visitor) {
            Logger.log("Portfolio", "Початок проходу " + visitor.getClass().getSimpleName() + "; продуктів " + products.size());
            products.forEach(p -> p.accept(visitor));
            Logger.log("Portfolio", "Прохід завершено, склад портфеля незмінний");
        }
    }
    public abstract static class TotalVisitor implements Visitor {
        private BigDecimal total = BigDecimal.ZERO;
        protected void add(String type, BigDecimal amount, String rate) {
            BigDecimal before = total;
            BigDecimal value = amount.multiply(new BigDecimal(rate)).setScale(2, RoundingMode.HALF_UP);
            total = total.add(value);
            Logger.log(getClass().getSimpleName(), type + ": " + value + "; підсумок " + before + " -> " + total);
        }
        public BigDecimal total() { return total; }
    }
    public static final class Reserves extends TotalVisitor {
        public void visit(Deposit d) { add("Депозит", d.amount(), "0.02"); }
        public void visit(Loan l) { add("Кредит", l.amount(), "0.05"); }
        public void visit(Card c) { add("Картка", c.amount(), "0.01"); }
        public void visit(Investment i) { add("Інвестиційний рахунок", i.amount(), "0.03"); }
    }
    public static final class Profitability extends TotalVisitor {
        public void visit(Deposit d) { add("Депозит - витрата банку", d.amount(), "-0.12"); }
        public void visit(Loan l) { add("Кредит", l.amount(), "0.20"); }
        public void visit(Card c) { add("Картка", c.amount(), "0.02"); }
        public void visit(Investment i) { add("Інвестиційний рахунок", i.amount(), "0.01"); }
    }
    public static final class ClientReport implements Visitor {
        public void visit(Deposit d) { Logger.log("ClientReport", "Депозит: " + d.amount()); }
        public void visit(Loan l) { Logger.log("ClientReport", "Кредит: " + l.amount()); }
        public void visit(Card c) { Logger.log("ClientReport", "Картка: " + c.amount()); }
        public void visit(Investment i) { Logger.log("ClientReport", "Інвестиційний рахунок: " + i.amount()); }
    }
}
