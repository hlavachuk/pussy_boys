package Task2;
// Рахунок студентського сегмента; реалізує спільний інтерфейс продукту.
public final class StudentCurrentAccount implements CurrentAccount {
    @Override public Segment segment() { return StudentFactory.SEGMENT; }
    @Override public String description() { return "Студентський поточний рахунок"; }
}
