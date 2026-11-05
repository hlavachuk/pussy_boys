package Task3;

import common.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Concrete Prototype: договір банку. Глибоке копіювання вкладених даних. */
public class Contract implements Prototype<Contract> {
    private static final Locale UA = Locale.forLanguageTag("uk-UA");

    private final String product;
    private String clientName;
    private final double rate;
    private final int termMonths;
    private long amount;
    private final String earlyTerminationTerms;
    private final List<PaymentScheduleItem> schedule = new ArrayList<>();
    private final List<String> addenda = new ArrayList<>();

    /** Дороге формування договору з нуля. */
    public Contract(String product, double rate, int termMonths, long amount, String earlyTerminationTerms) {
        Logger.log("Contract", "Формування договору «" + product
                + "» з нуля: ставка, строк, графік платежів, умови розірвання...");
        Logger.pause(500);
        this.product = product;
        this.rate = rate;
        this.termMonths = termMonths;
        this.amount = amount;
        this.earlyTerminationTerms = earlyTerminationTerms;
        double quarterPayout = amount * rate / 100 / 4;
        for (int month = 3; month <= termMonths; month += 3) {
            schedule.add(new PaymentScheduleItem(month, quarterPayout));
        }
        Logger.log("Contract", "Договір «" + product + "» сформовано");
    }

    /** Конструктор копіювання: нові об'єкти для всіх вкладених даних. */
    private Contract(Contract source) {
        this.product = source.product;
        this.clientName = source.clientName;
        this.rate = source.rate;
        this.termMonths = source.termMonths;
        this.amount = source.amount;
        this.earlyTerminationTerms = source.earlyTerminationTerms;
        for (PaymentScheduleItem item : source.schedule) {
            this.schedule.add(new PaymentScheduleItem(item));
        }
        this.addenda.addAll(source.addenda);
    }

    @Override
    public Contract copy() {
        Logger.log("Contract", "Договір скопійовано з типового «" + product + "» (без повторного формування)");
        return new Contract(this);
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void addAddendum(String text) {
        addenda.add(text);
    }

    public void changePayout(int month, double payout) {
        for (PaymentScheduleItem item : schedule) {
            if (item.getMonth() == month) {
                item.setPayout(payout);
            }
        }
    }

    public List<String> describe() {
        List<String> lines = new ArrayList<>();
        lines.add("Продукт: " + product);
        lines.add("Клієнт: " + (clientName == null ? "—" : clientName));
        lines.add(String.format(UA, "Ставка: %.1f%%, строк: %d міс.", rate, termMonths));
        lines.add("Сума: " + String.format(Locale.US, "%,d", amount).replace(',', ' ') + " грн");
        lines.add("Розірвання: " + earlyTerminationTerms);
        lines.add("Графік виплат:");
        for (PaymentScheduleItem item : schedule) {
            lines.add(String.format(UA, "  міс. %d: %.2f грн", item.getMonth(), item.getPayout()));
        }
        lines.add("Додаткові угоди:");
        if (addenda.isEmpty()) {
            lines.add("  (немає)");
        }
        for (String addendum : addenda) {
            lines.add("  " + addendum);
        }
        return lines;
    }
}
