package Task3;

public final class AuditorStatementGenerator extends StatementGenerator {
    @Override
    protected StatementDocument createDocument(StatementData data) {
        return new AuditDocument(data);
    }
}
