package Task2;

import common.Logger;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;

/**
 * Клієнт пулу. Стратегія при відсутності вільного з'єднання — черга FIFO:
 * платіж не втрачається і не порушує ліміт платіжної системи.
 */
public class PaymentService {
    private static final String NAME = "PaymentService";

    private final ConnectionPool pool;
    private final Queue<Payment> queue = new ArrayDeque<>();
    private final Map<Integer, PaymentConnection> active = new HashMap<>();

    public PaymentService(ConnectionPool pool) {
        this.pool = pool;
    }

    public void submit(Payment payment) {
        PaymentConnection connection = pool.acquire();
        if (connection == null) {
            queue.add(payment);
            Logger.log(NAME, "Платіж №" + payment.getNumber() + " очікує в черзі (позиція " + queue.size() + ")");
        } else {
            start(payment, connection);
        }
    }

    public void complete(int paymentNumber) {
        PaymentConnection connection = active.remove(paymentNumber);
        Logger.log(NAME, "Платіж №" + paymentNumber + " проведено");
        pool.release(connection);
        Payment next = queue.poll();
        if (next != null) {
            start(next, pool.acquire());
        }
    }

    private void start(Payment payment, PaymentConnection connection) {
        connection.bind(payment);
        active.put(payment.getNumber(), connection);
        Logger.log(NAME, "З'єднання " + connection.getId() + " → платіж №" + payment.getNumber()
                + ", " + money(payment.getAmount()) + " грн");
    }

    private static String money(long amount) {
        return String.format(Locale.US, "%,d", amount).replace(',', ' ');
    }
}
