package Task2;

import common.Logger;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/** Object Pool: не більше maxSize з'єднань, повторне використання без повторного встановлення. */
public class ConnectionPool {
    private final int maxSize;
    private final List<PaymentConnection> all = new ArrayList<>();
    private final Deque<PaymentConnection> free = new ArrayDeque<>();

    public ConnectionPool(int maxSize) {
        this.maxSize = maxSize;
    }

    /** Повертає вільне з'єднання або null, якщо ліміт вичерпано. */
    public synchronized PaymentConnection acquire() {
        PaymentConnection connection = free.pollFirst();
        if (connection == null && all.size() < maxSize) {
            connection = new PaymentConnection(all.size() + 1);
            all.add(connection);
        }
        return connection;
    }

    public synchronized void release(PaymentConnection connection) {
        connection.reset();
        free.addLast(connection);
        Logger.log("ConnectionPool", connection.getId() + " повернено, дані платежу очищено");
    }

    public synchronized int getCreatedCount() {
        return all.size();
    }

    public synchronized List<PaymentConnection> getAll() {
        return new ArrayList<>(all);
    }
}
