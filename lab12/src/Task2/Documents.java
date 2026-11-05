package Task2;

import common.Logger;

public final class Documents {
    private Documents() {}
    public interface Delivery { String deliver(String content); }
    public static final class Print implements Delivery {
        public String deliver(String content) { return "Друк у відділенні: " + content; }
    }
    public static final class Pdf implements Delivery {
        public String deliver(String content) { return "PDF онлайн: " + content; }
    }
    public static final class Signed implements Delivery {
        public String deliver(String content) { return "Е-документ з цифровим підписом: " + content; }
    }
    public static final class Diia implements Delivery {
        public String deliver(String content) { return "Надсилання в Дію: " + content; }
    }
    public abstract static class Document {
        private Delivery delivery;
        protected Document(Delivery delivery) { this.delivery = java.util.Objects.requireNonNull(delivery); }
        public void setDelivery(Delivery delivery) { this.delivery = java.util.Objects.requireNonNull(delivery); }
        protected abstract String content();
        public String issue() {
            String result = delivery.deliver(content());
            Logger.log(getClass().getSimpleName() + "/" + delivery.getClass().getSimpleName(), result);
            return result;
        }
    }
    public static final class Receipt extends Document {
        private final String payment;
        public Receipt(String payment, Delivery delivery) { super(delivery); this.payment = payment; }
        protected String content() { return "Квитанція: " + payment; }
    }
    public static final class AccountStatement extends Document {
        private final String account;
        public AccountStatement(String account, Delivery delivery) { super(delivery); this.account = account; }
        protected String content() { return "Довідка про стан рахунку " + account; }
    }
    public static final class PaymentOrder extends Document {
        private final String payment;
        public PaymentOrder(String payment, Delivery delivery) { super(delivery); this.payment = payment; }
        protected String content() { return "Платіжне доручення: " + payment; }
    }
}
