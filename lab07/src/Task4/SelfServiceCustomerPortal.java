package Task4;

public class SelfServiceCustomerPortal implements UserRegistration, Authenticator, SecretResetter, GdprExporter {
    @Override public void registerUser(String userIdentifier, String secretKey) { System.out.println("Користувача зареєстровано: " + userIdentifier); }
    @Override public boolean authenticate(String userIdentifier, String secretKey) { return true; }
    @Override public void resetSecret(String userIdentifier) { System.out.println("Надіслано секретний запит на скидання для " + userIdentifier); }
    @Override public void exportUserDataGdpr(int userId) { System.out.println("Експортований архів GDPR для користувача за ID " + userId); }
}
