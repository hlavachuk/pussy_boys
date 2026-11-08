package Task3;

import java.util.HashMap;
import java.util.Map;

public final class MerchantFactory {
    private final Map<String, Merchant> merchants = new HashMap<>();
    public Merchant get(String id, String name, String logo, String category, String mcc, String address) {
        Merchant existing = merchants.get(id);
        if (existing != null) {
            if (!existing.name().equals(name) || !existing.logo().equals(logo)
                    || !existing.category().equals(category) || !existing.mcc().equals(mcc)
                    || !existing.address().equals(address))
                throw new IllegalArgumentException("Суперечливі дані торговця " + id);
            return existing;
        }
        Merchant candidate = new Merchant(name, logo, category, mcc, address);
        merchants.put(id, candidate); return candidate;
    }
    public int count() { return merchants.size(); }
}
