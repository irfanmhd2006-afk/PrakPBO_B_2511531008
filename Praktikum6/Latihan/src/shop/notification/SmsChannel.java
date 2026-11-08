package shop.notification;

public class SmsChannel extends AbstractChannel {
    private final int maxLength;

    public SmsChannel(int maxLength) {
        if (maxLength < 3) {
            throw new IllegalArgumentException("maxLength must be at least 3");
        }
        this.maxLength = maxLength;
    }

    @Override public String channelName() { return "SMS"; }

    @Override
    protected String resolveRecipient(CustomerContact c) {
        return c.getPhone().startsWith("08") ? c.getPhone() : "";
    }

    @Override
    protected String prepare(String message) {
        if (message.length() <= maxLength) {
            return message;
        }
        return message.substring(0, maxLength - 3) + "...";   // total tepat maxLength
    }

    @Override
    protected boolean deliver(String recipient, String message) {
        System.out.println("[SMS -> " + recipient + "] " + message);
        return true;
    }
}