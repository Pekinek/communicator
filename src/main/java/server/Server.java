package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server implements Runnable {

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server started");
            while (true) {
                Socket socket = serverSocket.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String user = br.readLine();
                String password = br.readLine();
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                if ("correctUser".equals(user) && "correctPassword".equals(password))
                    printWriter.println("Authentication succeed!");
                else
                    printWriter.println("Authentication succeed!");
                printWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
