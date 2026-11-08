package shop.notification;

public class EmailChannel extends AbstractChannel {
    public EmailChannel() { }

    @Override public String channelName() { return "Email"; }

    @Override
    protected String resolveRecipient(CustomerContact c) {
        return c.getEmail().contains("@") ? c.getEmail() : "";
    }

    @Override
    protected boolean deliver(String recipient, String message) {
        System.out.println("[EMAIL -> " + recipient + "] " + message);
        return true;
    }
}