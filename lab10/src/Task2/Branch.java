package Task2;
import common.Logger;

// Відділення працює з абстракціями, не вибираючи конкретні продукти.
public final class Branch {
    public BankingPackage openService(String client, BankingProductFactory factory) {
        // Новий сегмент додається новою фабрикою без зміни цього методу.
        BankingPackage products = BankingPackage.from(factory);
        Logger.log("Відділення", "Відкрито обслуговування: " + client + "; " + products);
        return products;
    }
}
