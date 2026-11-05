package Task1;

import common.Logger;

public class OnlineBanking {
    private static final String NAME = "Онлайн-банкінг";
    private final ExchangeRateService service = ExchangeRateService.getInstance();

    public OnlineBanking() {
        Logger.log(NAME, "Отримано службу курсів, id=" + service.getId());
    }

    public void showRate(String currency) {
        Logger.log(NAME, "Курс " + service.getRate(currency));
    }

    public String getServiceId() {
        return service.getId();
    }
}
