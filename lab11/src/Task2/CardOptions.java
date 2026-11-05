package Task2;

import common.Logger;
import java.math.BigDecimal;

public final class CardOptions {
    private CardOptions() {}
    public interface Card { String description(); BigDecimal monthlyFee(); }
    public static final class BasicCard implements Card {
        public String description() { return "Базова картка"; }
        public BigDecimal monthlyFee() {
            Logger.log("BasicCard", "Базова вартість 50.00 грн/місяць");
            return new BigDecimal("50.00");
        }
    }
    public abstract static class Option implements Card {
        protected final Card wrapped;
        private final String name;
        private final BigDecimal fee;
        protected Option(Card wrapped, String name, String fee) {
            this.wrapped = wrapped; this.name = name; this.fee = new BigDecimal(fee);
        }
        public String description() { return wrapped.description() + " + " + name; }
        public BigDecimal monthlyFee() {
            BigDecimal total = wrapped.monthlyFee().add(fee);
            Logger.log(getClass().getSimpleName(), "Обгортка додає " + fee + "; разом " + total);
            return total;
        }
    }
    public static final class Cashback extends Option {
        public Cashback(Card card) { super(card, "кешбек", "20.00"); }
    }
    public static final class TravelInsurance extends Option {
        public TravelInsurance(Card card) { super(card, "страхування подорожей", "70.00"); }
    }
    public static final class Sms extends Option {
        public Sms(Card card) { super(card, "SMS", "10.00"); }
    }
    public static final class HigherLimit extends Option {
        public HigherLimit(Card card) { super(card, "підвищений ліміт", "30.00"); }
    }
    public static final class Lounge extends Option {
        public Lounge(Card card) { super(card, "бізнес-зали", "100.00"); }
    }
    public static void show(Card card) {
        Logger.log("CardSystem", card.description() + ": " + card.monthlyFee() + " грн/місяць");
    }
}
