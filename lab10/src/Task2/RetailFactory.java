package Task2;

// Конкретна фабрика створює всі продукти роздрібного сегмента.
public final class RetailFactory implements BankingProductFactory {
    public static final Segment SEGMENT = new Segment("Роздрібний");
    @Override public Segment segment() { return SEGMENT; }
    @Override public Card createCard() { return new RetailCard(); }
    @Override public CurrentAccount createAccount() { return new RetailCurrentAccount(); }
    @Override public TariffPlan createTariff() { return new RetailTariffPlan(); }
}
