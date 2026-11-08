package Task2;

import common.Logger;
import java.math.BigDecimal;

public final class BasicCard implements Card {
    public String description() { return "Базова картка"; }
    public BigDecimal monthlyFee() {
        Logger.log("BasicCard", "Базова вартість 50.00 грн/місяць");
        return new BigDecimal("50.00");
    }
}
