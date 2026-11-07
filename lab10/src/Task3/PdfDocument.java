package Task3;

public final class PdfDocument implements StatementDocument {
    private final StatementData data;

    public PdfDocument(StatementData data) {
        this.data = data;
    }

    @Override
    public String format() {
        return "PDF";
    }

    @Override
    public String render() {
        StringBuilder result = new StringBuilder("Виписка для клієнта\n").append(data.summary());
        for (Transaction t : data.transactions()) {
            result.append("\n").append(t.date()).append(" | ").append(t.description())
                    .append(" | ").append(t.amount().toPlainString());
        }
        return result.toString();
    }
}
