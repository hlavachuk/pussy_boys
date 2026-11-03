package Task2;
// Рахунок преміального сегмента; реалізує спільний інтерфейс продукту.
public final class PremiumCurrentAccount implements CurrentAccount {
    @Override public Segment segment() { return PremiumFactory.SEGMENT; }
    @Override public String description() { return "Персональний преміальний рахунок"; }
}
