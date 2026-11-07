package Task3;

public final class AuditDocument implements StatementDocument {
    private final StatementData data;

    public AuditDocument(StatementData data) {
        this.data = data;
    }

    @Override
    public String format() {
        return "AUDIT-TEXT";
    }

    @Override
    public String render() {
        StringBuilder result = new StringBuilder("Аудиторська виписка\n").append(data.summary());
        for (Transaction t : data.transactions()) {
            result.append("\n").append(t.date()).append(" | ").append(t.amount().toPlainString())
                    .append(" | ").append(t.description());
        }
        return result.toString();
    }
}
