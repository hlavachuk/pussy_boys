package Task1;

import common.Logger;

public class Branch {
    private final String name;
    private final ExchangeRateService service = ExchangeRateService.getInstance();

    public Branch(int number) {
        this.name = "Відділення №" + number;
        Logger.log(name, "Отримано службу курсів, id=" + service.getId());
    }

    public void showRate(String currency) {
        Logger.log(name, "Курс " + service.getRate(currency));
    }

    public String getServiceId() {
        return service.getId();
    }
}
