package Task2;

import common.Logger;
import java.math.BigDecimal;

public abstract class Option implements Card {
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
