package Task2;
// Рахунок роздрібного сегмента; реалізує спільний інтерфейс продукту.
public final class RetailCurrentAccount implements CurrentAccount {
    @Override public Segment segment() { return RetailFactory.SEGMENT; }
    @Override public String description() { return "Поточний рахунок фізичної особи"; }
}
