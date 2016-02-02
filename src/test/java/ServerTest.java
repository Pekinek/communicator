import org.junit.Test;
import server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class ServerTest {

    @Test
    public void createServerAndReadData() throws Exception {
        Thread t = new Server();
        t.start();
        Socket socket = new Socket("127.0.0.1", 5000);
        InputStreamReader is = new InputStreamReader(socket.getInputStream());
        BufferedReader br = new BufferedReader(is);
        assertEquals("Hello", br.readLine());
        br.close();
    }
}
