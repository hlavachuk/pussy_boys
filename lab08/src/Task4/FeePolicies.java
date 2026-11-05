package Task4;

import java.util.Map;

public final class FeePolicies {
    private static final Map<String, FeePolicy> BY_CATEGORY = Map.of(
            "general", new GeneralFee(),
            "specialist", new SpecialistFee(),
            "professor", new ProfessorFee());

    private FeePolicies() { }

    public static FeePolicy of(String category) {
        FeePolicy policy = BY_CATEGORY.get(category.trim().toLowerCase());
        if (policy == null) {
            throw new IllegalArgumentException("Невідома категорія лікаря: " + category);
        }
        return policy;
    }
}
