package Task2;

// Abstract Factory створює картку, рахунок і тариф одного сегмента.
public interface BankingProductFactory {
    Segment segment();
    Card createCard();
    CurrentAccount createAccount();
    TariffPlan createTariff();
}
