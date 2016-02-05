package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;


public class ConnectionCreator implements Runnable {
    private static final String AUTHENTICATION_SUCCEED = "Authentication succeed!";
    private static final String AUTHENTICATION_FAILED = "Authentication failed!";
    private final Set<Connection> connections = new HashSet<>();
    private Authenticator authenticator;
    private MessageFormatter messageFormatter;
    private Archiver archiver;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private ServerSocket serverSocket;
    private String user;

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    public void setArchiver(Archiver archiver) {
        this.archiver = archiver;
    }

    public MessageFormatter getMessageFormatter() {
        return messageFormatter;
    }

    public void setMessageFormatter(MessageFormatter messageFormatter) {
        this.messageFormatter = messageFormatter;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    @Override
    public void run() {
        try {
            startServer();
            while (true) {
                waitForNewConnection();
                validateConnection();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMessageToEverybody(String message) {
        for (Connection c : connections) {
            c.sendMessage(message);
        }
        archiver.saveMessage(message);
    }

    private void startServer() throws IOException {
        serverSocket = new ServerSocket(5000);
    }

    private void waitForNewConnection() throws IOException {
        Socket socket = serverSocket.accept();
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private void validateConnection() throws IOException {
        if (isUserAndPasswordCorrect()) {
            printWriter.println(AUTHENTICATION_SUCCEED);
            saveConnection();
        } else {
            printWriter.println(AUTHENTICATION_FAILED);
            printWriter.close();
        }
    }

    private boolean isUserAndPasswordCorrect() throws IOException {
        user = bufferedReader.readLine();
        String password = bufferedReader.readLine();
        return authenticator.isCorrect(user, password);
    }

    private void saveConnection() {
        Connection connection = new Connection(user, this);
        connections.add(connection);
        new Thread(connection).start();

    }
}
