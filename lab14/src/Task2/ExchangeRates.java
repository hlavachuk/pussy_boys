package Task2;

import common.Logger;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

public final class ExchangeRates {
    private ExchangeRates() {}
    public interface Observer { void update(BigDecimal rate); }
    public static final class RateService {
        private final Set<Observer> observers = new LinkedHashSet<>();
        private BigDecimal rate;
        public void subscribe(Observer observer) { observers.add(java.util.Objects.requireNonNull(observer)); }
        public void unsubscribe(Observer observer) { observers.remove(observer); }
        public void setRate(BigDecimal rate) {
            if (rate.signum() <= 0) throw new IllegalArgumentException("Курс має бути додатним");
            this.rate = rate; Logger.log("RateService", "Новий курс " + rate);
            for (Observer observer : java.util.List.copyOf(observers)) observer.update(this.rate);
        }
    }
    public static final class BranchBoard implements Observer {
        public void update(BigDecimal rate) { Logger.log("BranchBoard", "Табло оновлено: " + rate); }
    }
    public static final class OnlineBanking implements Observer {
        public void update(BigDecimal rate) { Logger.log("OnlineBanking", "Курс у банкінгу: " + rate); }
    }
    public static final class Conversion implements Observer {
        private BigDecimal rate;
        public void update(BigDecimal rate) { this.rate = rate; Logger.log("Conversion", "Курс конвертації: " + rate); }
        public BigDecimal convert(BigDecimal eur) {
            if (rate == null) throw new IllegalStateException("Курс ще не отримано");
            return eur.multiply(rate);
        }
    }
    public static final class ClientAlert implements Observer {
        private final BigDecimal threshold;
        private int notifications;
        public ClientAlert(BigDecimal threshold) { this.threshold = threshold; }
        public int notifications() { return notifications; }
        public void update(BigDecimal rate) {
            if (rate.compareTo(threshold) <= 0) {
                notifications++; Logger.log("ClientAlert", "Сповіщення клієнту: курс " + rate + " досяг порогу " + threshold);
            }
        }
    }
    public static final class MobileApp implements Observer {
        public void update(BigDecimal rate) { Logger.log("MobileApp", "Мобільний застосунок отримав " + rate); }
    }
}
