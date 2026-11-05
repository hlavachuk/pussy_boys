package Task1;

import java.time.LocalTime;
import java.util.Locale;

public class ExchangeRate {
    private static final Locale UA = Locale.forLanguageTag("uk-UA");

    private final String currency;
    private final double buy;
    private final double sell;
    private final LocalTime setAt;

    public ExchangeRate(String currency, double buy, double sell) {
        this.currency = currency;
        this.buy = buy;
        this.sell = sell;
        this.setAt = LocalTime.now();
    }

    public String getCurrency() {
        return currency;
    }

    public double getBuy() {
        return buy;
    }

    public double getSell() {
        return sell;
    }

    public LocalTime getSetAt() {
        return setAt;
    }

    @Override
    public String toString() {
        return String.format(UA, "%s %.2f / %.2f", currency, buy, sell);
    }
}
