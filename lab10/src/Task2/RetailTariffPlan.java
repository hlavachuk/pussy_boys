package Task2;
// Тариф роздрібного сегмента; реалізує спільний інтерфейс продукту.
public final class RetailTariffPlan implements TariffPlan {
    @Override public Segment segment() { return RetailFactory.SEGMENT; }
    @Override public String description() { return "Базовий"; }
}
