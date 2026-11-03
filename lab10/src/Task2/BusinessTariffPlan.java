package Task2;
// Тариф бізнесового сегмента; реалізує спільний інтерфейс продукту.
public final class BusinessTariffPlan implements TariffPlan {
    @Override public Segment segment() { return BusinessFactory.SEGMENT; }
    @Override public String description() { return "Бізнес"; }
}
