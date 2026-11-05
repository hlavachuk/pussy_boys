package Task3;

public class PaymentScheduleItem {
    private final int month;
    private double payout;

    public PaymentScheduleItem(int month, double payout) {
        this.month = month;
        this.payout = payout;
    }

    public PaymentScheduleItem(PaymentScheduleItem source) {
        this.month = source.month;
        this.payout = source.payout;
    }

    public int getMonth() {
        return month;
    }

    public double getPayout() {
        return payout;
    }

    public void setPayout(double payout) {
        this.payout = payout;
    }
}
