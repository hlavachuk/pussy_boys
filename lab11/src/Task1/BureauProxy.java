package Task1;

import common.Logger;
import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

public final class BureauProxy implements Bureau {
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
