package Task2;

// Конкретна фабрика створює всі продукти студентського сегмента.
public final class StudentFactory implements BankingProductFactory {
    public static final Segment SEGMENT = new Segment("Студентський");
    @Override public Segment segment() { return SEGMENT; }
    @Override public Card createCard() { return new StudentCard(); }
    @Override public CurrentAccount createAccount() { return new StudentCurrentAccount(); }
    @Override public TariffPlan createTariff() { return new StudentTariffPlan(); }
}
