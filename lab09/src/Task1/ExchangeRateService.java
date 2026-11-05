package Task1;

import common.Logger;

import java.util.HashMap;
import java.util.Map;

public final class ExchangeRateService {
    private final String id;
    private final Map<String, ExchangeRate> rates = new HashMap<>();

    private ExchangeRateService() {
        this.id = "ERS-" + Integer.toHexString(System.identityHashCode(this));
        Logger.log("ExchangeRateService", "Службу курсів валют ініціалізовано");
    }

    private static final class Holder {
        private static final ExchangeRateService INSTANCE = new ExchangeRateService();
    }

    public static ExchangeRateService getInstance() {
        return Holder.INSTANCE;
    }

    public String getId() {
        return id;
    }

    public synchronized void setRate(String currency, double buy, double sell) {
        rates.put(currency, new ExchangeRate(currency, buy, sell));
    }

    public synchronized ExchangeRate getRate(String currency) {
        return rates.get(currency);
    }
}
