package Task3;

public final class ClientStatementGenerator extends StatementGenerator {
    @Override
    protected StatementDocument createDocument(StatementData data) {
        return new PdfDocument(data);
    }
}
