import archivers.MockArchiver;
import authenticators.BasicAuthenticator;
import formatters.BasicFormatter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.ConnectionCreator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class ConnectionCreatorTest {

    private PrintWriter printWriter;
    private BufferedReader br;

    @BeforeClass
    public static void initializeServer() {
        ConnectionCreator connectionCreator = new ConnectionCreator();
        connectionCreator.setAuthenticator(new BasicAuthenticator());
        connectionCreator.setMessageFormatter(new BasicFormatter());
        connectionCreator.setArchiver(new MockArchiver());

        Thread t = new Thread(connectionCreator);
        t.start();
    }

    @Before
    public void initializeConnection() throws IOException {
        Socket socket = new Socket("127.0.0.1", 5000);
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private void sendUsernameAndPassword(String user, String password) {
        printWriter.println(user);
        printWriter.println(password);
    }

    private String getMessageFromServer() throws IOException {
        return br.readLine();
    }

    @Test
    public void loginWithSuccess() throws Exception {
        sendUsernameAndPassword("correctUser", "correctPassword");
        assertEquals("Authentication succeed!", getMessageFromServer());
    }

    @Test
    public void loginWithoutSuccess() throws Exception {
        sendUsernameAndPassword("wrongUser", "wrongPassword");
        assertEquals("Authentication failed!", getMessageFromServer());
    }

    @Test
    public void sendMessageAndReceiveItBack() throws Exception {
        sendUsernameAndPassword("correctUser", "correctPassword");
        assertEquals("Authentication succeed!", getMessageFromServer());
        String message = "Hello";
        printWriter.println(message);
        System.out.println("message sent");
        assertEquals("correctUser: " + message, getMessageFromServer());
    }
}
