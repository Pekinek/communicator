package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends Thread {
    private boolean connected;


    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(5000);
            Socket socket = serverSocket.accept();

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println("Hello");
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void waitForConnection() throws IOException {

    }
}
