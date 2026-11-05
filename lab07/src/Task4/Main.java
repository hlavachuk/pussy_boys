package Task4;

public class Main {
    public static void main(String[] args) {
        SelfServiceCustomerPortal portal = new SelfServiceCustomerPortal();
        AuthGatewayService gateway = new AuthGatewayService();
        AdminConsoleService admin = new AdminConsoleService();

        new LoginHandler(portal, portal).login("Макс", "123");
        new LoginHandler(gateway, gateway).login("Валерій", "123");

        new AdminPanel(admin, admin).promoteToAdmin(7);

        portal.registerUser("Андрій", "123");
        portal.exportUserDataGdpr(7);
    }
}
