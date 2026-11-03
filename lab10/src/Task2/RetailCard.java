package Task2;
// Картка роздрібного сегмента; реалізує спільний інтерфейс продукту.
public final class RetailCard implements Card {
    @Override public Segment segment() { return RetailFactory.SEGMENT; }
    @Override public String description() { return "Дебетова картка"; }
}
