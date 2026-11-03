package Task2;
// Картка студентського сегмента; реалізує спільний інтерфейс продукту.
public final class StudentCard implements Card {
    @Override public Segment segment() { return StudentFactory.SEGMENT; }
    @Override public String description() { return "Студентська картка"; }
}
