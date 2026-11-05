package Task1;

import common.Logger;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/** Protection and caching proxy. Cache lifetime is an explicitly supplied business day. */
public final class CreditHistory {
    private CreditHistory() {}
    public enum Role { CREDIT_MANAGER, CASHIER }
    public record User(String name, Role role) {}
    public interface Bureau { String history(String client, User user, LocalDate day); }
    public static final class PaidBureau implements Bureau {
        private int requests;
        public int requests() { return requests; }
        @Override public String history(String client, User user, LocalDate day) {
            requests++;
            Logger.log("PaidBureau", "Платний запит №" + requests + ": " + client);
            return "Історія " + client + ": прострочень немає";
        }
    }
    public static final class BureauProxy implements Bureau {
        private record Key(String client, LocalDate day) {}
        private final Bureau real;
        private final Map<Key, String> cache = new HashMap<>();
        public BureauProxy(Bureau real) { this.real = real; }
        @Override public String history(String client, User user, LocalDate day) {
            if (user.role() != Role.CREDIT_MANAGER) {
                Logger.log("BureauProxy", user.name() + ": Недостатньо прав");
                throw new SecurityException("Недостатньо прав");
            }
            Key key = new Key(client, day);
            if (cache.containsKey(key)) {
                Logger.log("BureauProxy", "Кеш за " + day + ": " + client);
                return cache.get(key);
            }
            Logger.log("BureauProxy", "Передаю запит реальному бюро");
            String result = real.history(client, user, day);
            cache.put(key, result);
            return result;
        }
    }
    public static final class Lending {
        private final Bureau bureau;
        public Lending(Bureau bureau) { this.bureau = bureau; }
        public String check(String client, User user, LocalDate day) {
            String result = bureau.history(client, user, day);
            Logger.log("Lending", result);
            return result;
        }
    }
}
