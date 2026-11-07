package Task3;

public final class AccountingStatementGenerator extends StatementGenerator {
    @Override
    protected StatementDocument createDocument(StatementData data) {
        return new CsvDocument(data);
    }
}
