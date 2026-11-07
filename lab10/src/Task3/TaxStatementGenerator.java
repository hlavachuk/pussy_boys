package Task3;

public final class TaxStatementGenerator extends StatementGenerator {
    @Override
    protected StatementDocument createDocument(StatementData data) {
        return new XmlDocument(data);
    }
}
