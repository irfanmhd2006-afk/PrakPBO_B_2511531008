package shop.notification;

public abstract class AbstractChannel implements NotificationChannel {
    private int sentCount;
    private String lastDelivered;

    @Override
    public final boolean send(CustomerContact contact, String message) {
        String recipient = resolveRecipient(contact);
        if (recipient == null || recipient.isEmpty()) {
            return false;                       // tidak ada alamat tujuan
        }
        String prepared = prepare(message);
        if (!deliver(recipient, prepared)) {
            return false;
        }
        sentCount++;
        lastDelivered = prepared;               // hanya dicatat jika berhasil
        return true;
    }

    protected abstract String resolveRecipient(CustomerContact contact);
    protected abstract boolean deliver(String recipient, String message);

    protected String prepare(String message) {  // boleh di-override
        return message;
    }

    public int getSentCount()         { return sentCount; }
    public String getLastDelivered()  { return lastDelivered; }
}