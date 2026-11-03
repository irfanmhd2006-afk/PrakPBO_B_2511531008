package shop.payment;

public class BankTransferPayment implements PaymentMethod {

    private String bankName;
    public BankTransferPayment(String bankName) {
        if (bankName == null || bankName.isBlank()) {
            throw new IllegalArgumentException("bankName cannot be null or blank");
        }
        this.bankName = bankName;
    }
    public String getName() {
        return "Transfer "+ bankName;
    }
    public boolean pay(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        return true;
    }
}
