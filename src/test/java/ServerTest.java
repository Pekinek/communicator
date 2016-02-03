import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class ServerTest {

    @BeforeClass
    public static void initializeServer() {
        Thread t = new Thread(new Server());
        System.out.println("Hello");
        t.start();
    }

    @Test
    public void createServerAndLoginWithSuccess() throws Exception {
        Socket socket = new Socket("127.0.0.1", 5000);
        Thread.sleep(3000);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        printWriter.println("correctUser");
        printWriter.println("correctPassword");
        InputStreamReader is = new InputStreamReader(socket.getInputStream());
        BufferedReader br = new BufferedReader(is);
        assertEquals("Authentication succeed!", br.readLine());
    }

    @Ignore
    @Test
    public void createServerAndLoginWithoutSuccess() throws Exception {
        Socket socket = new Socket("127.0.0.1", 5000);
        Thread.sleep(3000);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter.println("wrongUser\n");
        printWriter.println("wrongPassword\n");
        System.out.println("data sent");
        assertEquals("Authentication failed!", br.readLine());

    }
}
