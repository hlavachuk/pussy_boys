package Task4;

public class AdminConsoleService implements RoleManager, AuditLogReader {
    @Override public void assignRole(int userId, String role) { System.out.println("Роль " + role + " призначено користувачеві за ID " + userId); }
    @Override public void auditUserLogs(int userId) { System.out.println("Журнали аудиту, що відображаються для користувача за ID " + userId); }
}
