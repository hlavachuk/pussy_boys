package Task4;

public class AuthGatewayService implements Authenticator, SecretResetter {
    @Override public boolean authenticate(String userIdentifier, String secretKey) { return true; }
    @Override public void resetSecret(String userIdentifier) { System.out.println("Скидання шлюзу (секретний спосіб) для " + userIdentifier); }
}
