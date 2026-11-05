package Task2;

import common.Logger;
import java.math.BigDecimal;

public final class CardLifecycle {
    private CardLifecycle() {}
    public interface State {
        default void activate(Card card) { denied("Активувати"); }
        default void pay(Card card, BigDecimal amount) { denied("Платити"); }
        default void wrongPin(Card card) { denied("Ввести PIN"); }
        default void unblock(Card card) { denied("Розблокувати"); }
        default void blockForever(Card card) { denied("Заблокувати назавжди"); }
        default void expire(Card card) { card.transition(new Expired()); }
        default void reissue(Card card) { denied("Перевипустити"); }
        default void denied(String action) { Logger.log(getClass().getSimpleName(), "Відмова: " + action + " недоступно у цьому стані"); }
    }
    public static final class Card {
        private State state = new Issued();
        public void transition(State state) {
            Logger.log("Card", "Стан: " + this.state.getClass().getSimpleName() + " -> " + state.getClass().getSimpleName());
            this.state = java.util.Objects.requireNonNull(state);
        }
        public String stateName() { return state.getClass().getSimpleName(); }
        public void activate() { state.activate(this); }
        public void pay(BigDecimal amount) { state.pay(this, amount); }
        public void wrongPin() { state.wrongPin(this); }
        public void unblock() { state.unblock(this); }
        public void blockForever() { state.blockForever(this); }
        public void expire() { state.expire(this); }
        public void reissue() { state.reissue(this); }
    }
    public static final class Issued implements State {
        public void activate(Card card) { card.transition(new Active()); }
        public void blockForever(Card card) { card.transition(new PermanentlyBlocked()); }
    }
    public static final class Active implements State {
        private int failedPins;
        public void pay(Card card, BigDecimal amount) {
            if (amount.signum() <= 0) { denied("Платіж з недодатною сумою"); return; }
            Logger.log("Active", "Платіж проведено: " + amount);
        }
        public void wrongPin(Card card) {
            Logger.log("Active", "Неправильний PIN: " + (++failedPins) + "/3");
            if (failedPins == 3) card.transition(new TemporarilyBlocked());
        }
        public void blockForever(Card card) { card.transition(new PermanentlyBlocked()); }
    }
    public static final class TemporarilyBlocked implements State {
        public void unblock(Card card) { card.transition(new Active()); }
        public void blockForever(Card card) { card.transition(new PermanentlyBlocked()); }
    }
    public static final class PermanentlyBlocked implements State {
        public void reissue(Card card) { card.transition(new Issued()); }
        public void expire(Card card) { denied("Зміна остаточного блокування на прострочення"); }
    }
    public static final class Expired implements State {
        public void reissue(Card card) { card.transition(new Issued()); }
        public void expire(Card card) { denied("Повторне прострочення"); }
    }
}
