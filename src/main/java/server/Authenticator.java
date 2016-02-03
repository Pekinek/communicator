package server;

public interface Authenticator {
    boolean isCorrect(String user, String password);
}
