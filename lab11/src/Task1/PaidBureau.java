package Task1;

import common.Logger;
import java.time.LocalDate;

public final class PaidBureau implements Bureau {
    private int requests;
    public int requests() { return requests; }
    @Override public String history(String client, User user, LocalDate day) {
        requests++;
        Logger.log("PaidBureau", "Платний запит №" + requests + ": " + client);
        return "Історія " + client + ": прострочень немає";
    }
}
