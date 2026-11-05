package Task4;

public class LoginHandler {
    private final Authenticator authenticator;
    private final SecretResetter secretResetter;

    public LoginHandler(Authenticator authenticator, SecretResetter secretResetter) {
        this.authenticator = authenticator;
        this.secretResetter = secretResetter;
    }

    public void login(String user, String secret) {
        if (authenticator.authenticate(user, secret)) {
            System.out.println("Ласкаво просимо, " + user);
        } else {
            secretResetter.resetSecret(user);
        }
    }
}
