package shop.payment;

import shop.StudentInfo;

public class BankTransferPayment implements PaymentMethod {

    private String bankName;
    public BankTransferPayment(String bankName) {
        if (bankName == null || bankName.isBlank()) {
            throw new IllegalArgumentException("bankName cannot be null or blank");
        }
        this.bankName = bankName;
    }

    @Override
    public String getName() {
        return "Transfer "+ bankName;
    }

    @Override
    public boolean pay(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        return true;
    }

    @Override
    public long adminFee(long amount) {
        return StudentInfo.TRANSFER_ADMIN_FEE;   // biaya tetap 4000
    }
}
