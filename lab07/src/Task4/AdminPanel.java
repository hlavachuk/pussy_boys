package Task4;

public class AdminPanel {
    private final RoleManager roleManager;
    private final AuditLogReader auditLogReader;

    public AdminPanel(RoleManager roleManager, AuditLogReader auditLogReader) {
        this.roleManager = roleManager;
        this.auditLogReader = auditLogReader;
    }

    public void promoteToAdmin(int userId) {
        roleManager.assignRole(userId, "АДМІН");
        auditLogReader.auditUserLogs(userId);
    }
}
