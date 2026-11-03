package Task2;
// Тариф преміального сегмента; реалізує спільний інтерфейс продукту.
public final class PremiumTariffPlan implements TariffPlan {
    @Override public Segment segment() { return PremiumFactory.SEGMENT; }
    @Override public String description() { return "Premium"; }
}
