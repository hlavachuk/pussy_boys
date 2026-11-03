package Task2;

// Спільний інтерфейс тарифного плану: сегмент і опис продукту.
public interface TariffPlan {
    Segment segment();
    String description();
}
