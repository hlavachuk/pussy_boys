package Task1;

import common.Logger;

public class Treasury {
    private static final String NAME = "Казначейство";
    private final ExchangeRateService service = ExchangeRateService.getInstance();

    public Treasury() {
        Logger.log(NAME, "Отримано службу курсів, id=" + service.getId());
    }

    public void setRate(String currency, double buy, double sell) {
        service.setRate(currency, buy, sell);
        Logger.log(NAME, "Встановлено курс " + service.getRate(currency));
    }

    public String getServiceId() {
        return service.getId();
    }
}
