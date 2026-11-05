package Task3;

import common.Logger;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/** All rates are educational assumptions, not actual bank tariffs or regulations. */
public final class DepositInterest {
    private DepositInterest() {}
    private static final MathContext MC = MathContext.DECIMAL128;
    public interface Strategy { BigDecimal income(BigDecimal principal, int months); }
    public static final class Simple implements Strategy {
        private final BigDecimal annualRate;
        public Simple(BigDecimal annualRate) { this.annualRate = annualRate; }
        public BigDecimal income(BigDecimal p, int months) {
            return p.multiply(annualRate).multiply(BigDecimal.valueOf(months)).divide(BigDecimal.valueOf(12), MC);
        }
    }
    public static final class MonthlyCapitalization implements Strategy {
        private final BigDecimal annualRate;
        public MonthlyCapitalization(BigDecimal annualRate) { this.annualRate = annualRate; }
        public BigDecimal income(BigDecimal p, int months) {
            BigDecimal factor = BigDecimal.ONE.add(annualRate.divide(BigDecimal.valueOf(12), MC));
            return p.multiply(factor.pow(months, MC).subtract(BigDecimal.ONE), MC);
        }
    }
    public static final class DailyAccrual implements Strategy {
        private final BigDecimal annualRate;
        public DailyAccrual(BigDecimal annualRate) { this.annualRate = annualRate; }
        public BigDecimal income(BigDecimal p, int months) {
            // Educational ACT/365 convention: each contractual month is 30 days, without capitalization.
            return p.multiply(annualRate).multiply(BigDecimal.valueOf(months * 30L)).divide(BigDecimal.valueOf(365), MC);
        }
    }
    public static final class Tiered implements Strategy {
        public BigDecimal income(BigDecimal p, int months) {
            BigDecimal rate = p.compareTo(new BigDecimal("100000")) >= 0 ? new BigDecimal("0.13") : new BigDecimal("0.10");
            return new Simple(rate).income(p, months);
        }
    }
    public static final class BonusRate implements Strategy {
        public BigDecimal income(BigDecimal p, int months) { return new Simple(new BigDecimal("0.14")).income(p, months); }
    }
    public static final class Deposit {
        private final BigDecimal principal;
        private final int months;
        private Strategy strategy;
        public Deposit(BigDecimal principal, int months, Strategy strategy) {
            if (principal.signum() <= 0 || months <= 0) throw new IllegalArgumentException("Сума і строк мають бути додатними");
            this.principal = principal; this.months = months; setStrategy(strategy);
        }
        public void setStrategy(Strategy strategy) { this.strategy = java.util.Objects.requireNonNull(strategy); }
        public BigDecimal calculate() {
            BigDecimal result = strategy.income(principal, months).setScale(2, RoundingMode.HALF_UP);
            Logger.log("Deposit/" + strategy.getClass().getSimpleName(), principal + " грн, " + months + " місяців: дохід " + result);
            return result;
        }
    }
}
