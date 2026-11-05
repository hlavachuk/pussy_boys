package Task3;

import common.Logger;
import java.math.BigDecimal;
import java.util.Objects;

public final class InternationalPayments {
    private InternationalPayments() {}
    public record Payment(String sender, String recipient, BigDecimal amount, String currency) {
        public Payment {
            for (String field : new String[]{sender, recipient, currency})
                if (field == null || !field.matches("[A-Z0-9-]+"))
                    throw new IllegalArgumentException("Некоректне поле платежу");
            if (Objects.requireNonNull(amount).signum() <= 0 || amount.scale() > 2)
                throw new IllegalArgumentException("Потрібна додатна сума з точністю до копійок");
            if (!currency.matches("[A-Z]{3}")) throw new IllegalArgumentException("Код валюти");
        }
    }
    public record Status(boolean accepted, String reason) {}
    public interface PaymentGateway { Status send(Payment payment); }
    /** Immutable external API emulator. Its format/status dictionary is foreign to Treasury. */
    public static final class SwiftLibrary {
        public String transmit(String message) {
            Logger.log("SwiftLibrary", "Отримано " + message);
            return message.contains(":59:BLOCKED") ? "R17" : "A00";
        }
    }
    public static final class SwiftAdapter implements PaymentGateway {
        private final SwiftLibrary library;
        public SwiftAdapter(SwiftLibrary library) { this.library = library; }
        public Status send(Payment p) {
            String message = "{1:F01PRUTCREDIT}{4::50:" + p.sender() + ":59:" + p.recipient()
                    + ":32A:" + p.currency() + p.amount().setScale(2).toPlainString().replace('.', ',') + "-}";
            Logger.log("SwiftAdapter", "Сформовано SWIFT-повідомлення: " + message);
            String code = library.transmit(message);
            return switch (code) {
                case "A00" -> new Status(true, "Платіж прийнято");
                case "R17" -> new Status(false, "Платіж відхилено: отримувач заблокований");
                default -> new Status(false, "Невідомий статус постачальника: " + code);
            };
        }
    }
    public static final class Treasury {
        private final PaymentGateway gateway;
        public Treasury(PaymentGateway gateway) { this.gateway = gateway; }
        public Status pay(Payment payment) {
            Status status = gateway.send(payment);
            Logger.log("Treasury", status.reason());
            return status;
        }
    }
}
