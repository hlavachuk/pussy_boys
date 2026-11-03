package Task2;
// Картка бізнесового сегмента; реалізує спільний інтерфейс продукту.
public final class BusinessCard implements Card {
    @Override public Segment segment() { return BusinessFactory.SEGMENT; }
    @Override public String description() { return "Корпоративна картка"; }
}
