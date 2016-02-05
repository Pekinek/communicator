package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Connection implements Runnable {
    private final String user;
    private final BufferedReader bufferedReader;
    private final PrintWriter printWriter;
    private final ConnectionCreator connectionCreator;

    private final MessageFormatter messageFormatter;

    public Connection(String user, ConnectionCreator connectionCreator) {
        this.user = user;
        bufferedReader = connectionCreator.getBufferedReader();
        printWriter = connectionCreator.getPrintWriter();
        this.connectionCreator = connectionCreator;
        messageFormatter = connectionCreator.getMessageFormatter();
    }

    @Override
    public void run() {
        while (true) {
            try {
                readMessageAndAddItToBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readMessageAndAddItToBuffer() throws IOException {
        if (bufferedReader.ready()) {
            String message = messageFormatter.format(user, bufferedReader.readLine());
            connectionCreator.sendMessageToEverybody(message);
        }
    }

    void sendMessage(String message) {
        printWriter.println(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }


}
