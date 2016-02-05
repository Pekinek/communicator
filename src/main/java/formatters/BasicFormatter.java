package formatters;

import server.MessageFormatter;

public class BasicFormatter implements MessageFormatter {

    @Override
    public String format(String user, String message) {
        return user + ": " + message;
    }
}