package Task2;
// Рахунок бізнесового сегмента; реалізує спільний інтерфейс продукту.
public final class BusinessCurrentAccount implements CurrentAccount {
    @Override public Segment segment() { return BusinessFactory.SEGMENT; }
    @Override public String description() { return "Розрахунковий рахунок підприємства"; }
}
