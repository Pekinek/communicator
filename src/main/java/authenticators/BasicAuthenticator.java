package authenticators;

import server.Authenticator;

public class BasicAuthenticator implements Authenticator {

    @Override
    public boolean isCorrect(String user, String password) {
        return "correctUser".equals(user) && "correctPassword".equals(password);
    }
}
