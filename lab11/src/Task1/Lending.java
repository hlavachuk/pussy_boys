package Task1;

import common.Logger;
import java.time.LocalDate;

public final class Lending {
    private final Bureau bureau;
    public Lending(Bureau bureau) { this.bureau = bureau; }
    public String check(String client, User user, LocalDate day) {
        String result = bureau.history(client, user, day);
        Logger.log("Lending", result);
        return result;
    }
}
