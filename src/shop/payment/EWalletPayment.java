package shop.payment;

public class EWalletPayment implements RefundablePayment {
    private final String provider;
    private long balance; // Harus bertipe long dan bisa diubah (tidak final) agar saldo bisa berkurang

    public EWalletPayment(String provider, long balance) {
        if (provider == null || provider.isBlank()) {
            throw new IllegalArgumentException("Provider must not be blank");
        }
        if (balance < 0) {
            throw new IllegalArgumentException("Balance must not be negative");
        }
        this.provider = provider;
        this.balance = balance;
    }

    @Override
    public String getName() {
        return "E-Wallet " + provider;
    }

    public long getBalance() {
        return balance;
    }

    @Override
    public boolean pay(long amount) {
        if (amount <= 0 || amount > balance) {
            return false; // Gagal jika amount tidak valid atau melebihi saldo
        }
        balance -= amount; // Kurangi saldo jika berhasil
        return true;
    }
    public boolean refund(long amount) {
        if (amount <= 0) {
            return false; // Gagal jika amount tidak valid
        }
        balance += amount; // Tambahkan saldo jika refund berhasil
        return true;
    }
}