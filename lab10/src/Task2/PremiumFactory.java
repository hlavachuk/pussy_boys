package Task2;

// Конкретна фабрика створює всі продукти преміального сегмента.
public final class PremiumFactory implements BankingProductFactory {
    public static final Segment SEGMENT = new Segment("Преміальний");
    @Override public Segment segment() { return SEGMENT; }
    @Override public Card createCard() { return new PremiumCard(); }
    @Override public CurrentAccount createAccount() { return new PremiumCurrentAccount(); }
    @Override public TariffPlan createTariff() { return new PremiumTariffPlan(); }
}
