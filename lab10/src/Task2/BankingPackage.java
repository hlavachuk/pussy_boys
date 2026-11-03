package Task2;
import java.util.Objects;

// Узгоджений пакет: окремі продукти не можна передати ззовні.
public final class BankingPackage {
    private final Segment segment;
    private final Card card;
    private final CurrentAccount account;
    private final TariffPlan tariff;
    // Усі продукти отримуємо від однієї фабрики; null не допускається.
    private BankingPackage(BankingProductFactory factory) {
        segment = Objects.requireNonNull(factory.segment());
        card = Objects.requireNonNull(factory.createCard());
        account = Objects.requireNonNull(factory.createAccount());
        tariff = Objects.requireNonNull(factory.createTariff());
        // Відхиляємо змішаний набір, навіть якщо фабрика реалізована помилково.
        if (!segment.equals(card.segment()) || !segment.equals(account.segment())
                || !segment.equals(tariff.segment())) {
            throw new IllegalArgumentException("Продукти різних сегментів не можна об'єднати в пакет");
        }
    }
    // Повертаємо лише повністю перевірений пакет.
    public static BankingPackage from(BankingProductFactory factory) {
        return new BankingPackage(Objects.requireNonNull(factory));
    }
    public Segment getSegment() { return segment; }
    public Card getCard() { return card; }
    public CurrentAccount getAccount() { return account; }
    public TariffPlan getTariff() { return tariff; }
    // У журналі видно склад пакета та сегмент кожного продукту.
    @Override public String toString() {
        return "Пакет{сегмент=" + segment + ", картка=" + card.description() + " [" + card.segment()
            + "], рахунок=" + account.description() + " [" + account.segment()
            + "], тариф=" + tariff.description() + " [" + tariff.segment() + "]}";
    }
}
