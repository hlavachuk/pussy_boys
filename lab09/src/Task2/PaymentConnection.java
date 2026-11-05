package Task2;

import common.Logger;

/** Pooled object: захищене з'єднання з платіжною системою. */
public class PaymentConnection {
    private final String id;
    private final String sessionId;
    private Payment currentPayment;
    private int paymentsCount;

    // Конструктор пакетний: створювати з'єднання може лише пул
    PaymentConnection(int number) {
        this.id = "C-" + number;
        Logger.log(id, "Встановлення з'єднання: обмін сертифікатами, автентифікація, узгодження ключів...");
        Logger.pause(300);
        this.sessionId = "SES-" + number;
        Logger.log(id, "З'єднання встановлено, сесія " + sessionId);
    }

    public String getId() {
        return id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Payment getCurrentPayment() {
        return currentPayment;
    }

    public int getPaymentsCount() {
        return paymentsCount;
    }

    void bind(Payment payment) {
        this.currentPayment = payment;
        this.paymentsCount++;
    }

    /** Видаляє дані попереднього платежу (сума, рахунки, ідентифікатор операції). */
    void reset() {
        this.currentPayment = null;
    }
}
