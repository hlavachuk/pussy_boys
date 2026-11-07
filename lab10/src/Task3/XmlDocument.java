package Task3;

public final class XmlDocument implements StatementDocument {
    private final StatementData data;

    public XmlDocument(StatementData data) {
        this.data = data;
    }

    @Override
    public String format() {
        return "XML";
    }

    @Override
    public String render() {
        StringBuilder result = new StringBuilder("<statement from=\"").append(data.from())
                .append("\" to=\"").append(data.to()).append("\">\n");
        for (Transaction t : data.transactions()) {
            result.append("  <transaction date=\"").append(t.date()).append("\" amount=\"")
                    .append(t.amount().toPlainString()).append("\">")
                    .append(t.description().replace("&", "&amp;").replace("<", "&lt;")
                            .replace(">", "&gt;"))
                    .append("</transaction>\n");
        }
        return result.append("  <totals income=\"").append(data.income().toPlainString())
                .append("\" expense=\"").append(data.expense().toPlainString())
                .append("\" net=\"").append(data.net().toPlainString())
                .append("\"/>\n</statement>").toString();
    }
}
