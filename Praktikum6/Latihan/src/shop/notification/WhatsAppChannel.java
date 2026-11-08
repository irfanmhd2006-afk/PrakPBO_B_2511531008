package shop.notification;

public class WhatsAppChannel extends AbstractChannel {
    public WhatsAppChannel() { }

    @Override public String channelName() { return "WhatsApp"; }

    @Override
    protected String resolveRecipient(CustomerContact c) {
        String p = c.getPhone();
        boolean valid = (p.startsWith("08") || p.startsWith("+62")) && p.length() >= 10;
        return valid ? p : "";
    }

    @Override
    protected String prepare(String message) {
        return "[Toko UMKM] " + message;
    }

    @Override
    protected boolean deliver(String recipient, String message) {
        System.out.println("[WA -> " + recipient + "] " + message);
        return true;
    }
}