import authenticators.BasicAuthenticator;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class ServerTest {

    private PrintWriter printWriter;
    private BufferedReader br;

    @BeforeClass
    public static void initializeServer() {
        Server server = new Server();
        server.setAuthenticator(new BasicAuthenticator());
        Thread t = new Thread(server);
        t.start();
    }

    private void initializeConnection() throws IOException {
        Socket socket = new Socket("127.0.0.1", 5000);
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private void sendUsernameAndPassword(String correctUser, String correctPassword) {
        printWriter.println(correctUser);
        printWriter.println(correctPassword);
    }

    private String getMessageFromServer() throws IOException {
        return br.readLine();
    }

    @Test
    public void loginWithSuccess() throws Exception {
        initializeConnection();
        sendUsernameAndPassword("correctUser", "correctPassword");
        assertEquals("Authentication succeed!", getMessageFromServer());
    }

    @Test
    public void LoginWithoutSuccess() throws Exception {
        initializeConnection();
        sendUsernameAndPassword("wrongUser", "wrongPassword");
        assertEquals("Authentication failed!", getMessageFromServer());
    }
}
