package Task2;
// Картка преміального сегмента; реалізує спільний інтерфейс продукту.
public final class PremiumCard implements Card {
    @Override public Segment segment() { return PremiumFactory.SEGMENT; }
    @Override public String description() { return "Platinum"; }
}
