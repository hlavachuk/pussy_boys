package Task2;
// Тариф студентського сегмента; реалізує спільний інтерфейс продукту.
public final class StudentTariffPlan implements TariffPlan {
    @Override public Segment segment() { return StudentFactory.SEGMENT; }
    @Override public String description() { return "Студентський без абонплати"; }
}
