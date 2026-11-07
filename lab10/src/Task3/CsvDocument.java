package Task3;

public final class CsvDocument implements StatementDocument {
    private final StatementData data;

    public CsvDocument(StatementData data) {
        this.data = data;
    }

    @Override
    public String format() {
        return "CSV";
    }

    @Override
    public String render() {
        StringBuilder result = new StringBuilder("date,description,amount\n");
        for (Transaction t : data.transactions()) {
            result.append(t.date()).append(",\"").append(t.description().replace("\"", "\"\""))
                    .append("\",").append(t.amount().toPlainString()).append('\n');
        }
        return result.append(",,").append(data.net().toPlainString()).toString();
    }
}
