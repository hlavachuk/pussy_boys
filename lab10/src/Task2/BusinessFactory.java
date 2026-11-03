package Task2;

// Конкретна фабрика створює всі продукти бізнесового сегмента.
public final class BusinessFactory implements BankingProductFactory {
    public static final Segment SEGMENT = new Segment("Бізнес");
    @Override public Segment segment() { return SEGMENT; }
    @Override public Card createCard() { return new BusinessCard(); }
    @Override public CurrentAccount createAccount() { return new BusinessCurrentAccount(); }
    @Override public TariffPlan createTariff() { return new BusinessTariffPlan(); }
}
