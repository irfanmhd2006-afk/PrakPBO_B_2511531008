package shop.app;

import java.util.List;
import shop.StudentInfo;
import shop.model.DigitalProduct;
import shop.model.PhysicalProduct;
import shop.model.Product;
import shop.notification.*;
import shop.payment.*;
import shop.service.CheckoutService;
import shop.shipping.ExpressShipping;
import shop.shipping.ShippingService;

public class TugasApp {
    public static void main(String[] args) {
        List<Product> cart = List.of(
            new PhysicalProduct("KOPI-001", "Kopi Arabika Gayo 250g", 85_000, 300),
            new PhysicalProduct("RDG-002", "Rendang Kemasan 250g", 120_000, 400),
            new DigitalProduct("EBK-003", "E-book Resep Kue", 50_000L, "https://x.example/ebk"));

        CheckoutService checkout = new CheckoutService();
        ShippingService express = new ExpressShipping();

        // 1. Grand total (kurir Ekspres) untuk tiga metode pembayaran
        PaymentMethod[] methods = {
            new EWalletPayment("DompetKu", 500_000),
            new BankTransferPayment("Bank Nagari"),
            new CreditCardPayment("Siti Rahma", 1_000_000)
        };
        System.out.println("== Grand Total (Ekspres) ==");
        for (PaymentMethod m : methods) {
            System.out.println(m.getName() + " : Rp" + checkout.grandTotal(cart, express, m));
        }

        // 2. policy()
        System.out.println("\n== Policy ==");
        System.out.println("Kartu kredit: " + ((CreditCardPayment) methods[2]).policy());
        System.out.println("E-wallet    : " + ((EWalletPayment) methods[0]).policy());

        // 3. Daftarkan kanal
        NotificationService service = new NotificationService();
        service.register(new EmailChannel());
        service.register(new SmsChannel(StudentInfo.SMS_MAX_LENGTH));
        service.register(new WhatsAppChannel());
        service.register(new NotificationChannel() {   // anonymous class: kanal log
            @Override public String channelName() { return "Log"; }
            @Override public boolean send(CustomerContact c, String msg) {
                System.out.println("[LOG] untuk " + c.getName() + ": " + msg.length() + " karakter");
                return true;
            }
        });

        // 4. Pesan panjang (> 200 karakter) ke dua kontak
        String longMessage = "Pesanan Anda telah kami terima dan sedang diproses. ".repeat(5);
        CustomerContact siti = new CustomerContact("Siti Rahma", "siti@example.com", "081234567890");
        CustomerContact budi = new CustomerContact("Budi", null, "+6281234567890");

        System.out.println("\n== Broadcast ==");
        System.out.println("Siti: " + service.broadcast(siti, longMessage) + " kanal berhasil");
        System.out.println("Budi: " + service.broadcast(budi, longMessage) + " kanal berhasil");
    }
}