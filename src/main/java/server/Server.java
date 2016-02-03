package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server implements Runnable {

    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private Authenticator authenticator;
    private ServerSocket serverSocket;

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(5000);
            while (true) {
                startNewConnection();
                checkUser();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startNewConnection() throws IOException {
        Socket socket = serverSocket.accept();
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream(), true);
    }

    private void checkUser() throws IOException {
        String user = bufferedReader.readLine();
        String password = bufferedReader.readLine();
        if (authenticator.isCorrect(user, password))
            printWriter.println("Authentication succeed!");
        else
            printWriter.println("Authentication failed!");
    }

}
