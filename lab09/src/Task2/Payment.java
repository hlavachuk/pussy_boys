package Task2;

public class Payment {
    private final int number;
    private final long amount;
    private final String fromAccount;
    private final String toAccount;

    public Payment(int number, long amount, String fromAccount, String toAccount) {
        this.number = number;
        this.amount = amount;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    public int getNumber() {
        return number;
    }

    public long getAmount() {
        return amount;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }
}
