package Task2;

// Незмінна назва сегмента; новий сегмент не потребує зміни цього типу.
public record Segment(String name) {
    public Segment {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Порожній сегмент");
    }
    @Override public String toString() { return name; }
}
