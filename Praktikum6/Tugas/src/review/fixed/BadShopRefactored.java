package review.fixed;

import java.util.ArrayList;
import java.util.List;

/* T3 - Refactored version of review.BadShop */

// Fix 1: small interfaces (ISP). Voucher is not forced to implement ship().
interface PaymentMethod {
    boolean pay(long amount);

    String printInvoice();
}

interface RefundablePayment extends PaymentMethod {
    boolean refund(long amount);
}

interface Shippable {
    int getWeightGrams();
}

class GiftVoucher implements PaymentMethod {
    private long balance = 100_000; // Fix 5: money uses long, not double

    @Override
    public boolean pay(long amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    @Override
    public String printInvoice() {
        return "sisa Rp" + balance;
    }
}

// Fix 2: abstract class with abstract hook; the formula is final
abstract class Courier {
    private final String name;

    protected Courier(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected abstract long ratePerKg();

    public final long cost(int kilograms) {
        if (kilograms <= 0) {
            return 0;
        }
        return kilograms * ratePerKg();
    }
}

class RegularCourier extends Courier {
    RegularCourier() { super("Reguler"); }

    @Override
    protected long ratePerKg() { return 10_000; }
}

class ExpressCourier extends Courier {
    ExpressCourier() { super("Ekspres"); }

    @Override
    protected long ratePerKg() { return 18_000; }
}

class SameDayCourier extends Courier {
    SameDayCourier() { super("Same Day"); }

    @Override
    protected long ratePerKg() { return 30_000; }
}

class Cart {
    private final List<Item> items = new ArrayList<>();

    // Fix 4: static nested, Item does not use any Cart data
    static class Item {
        private final String name;
        private final long price;
        private final int quantity;

        Item(String name, long price, int quantity) {
            if (name == null || name.isBlank() || price < 0 || quantity <= 0) {
                throw new IllegalArgumentException("Invalid item");
            }
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        long subtotal() { return price * quantity; }

        String getName() { return name; }
    }

    void add(Item item) {
        items.add(item);
    }

    long total() {
        long total = 0;
        for (Item item : items) {
            total += item.subtotal();
        }
        return total;
    }
}

// Fix 3: immutable value object (final class, final fields, no setter)
final class Address {
    private final String street;
    private final String city;

    Address(String street, String city) {
        if (street == null || street.isBlank() || city == null || city.isBlank()) {
            throw new IllegalArgumentException("Street and city are required");
        }
        this.street = street;
        this.city = city;
    }

    Address withCity(String newCity) {
        return new Address(street, newCity);
    }

    @Override
    public String toString() {
        return street + ", " + city;
    }
}

class SimpleOrder {
    private final String id;
    private final Address address;

    SimpleOrder(String id, Address address) {
        this.id = id;
        this.address = address;
    }

    Address getAddress() { return address; }

    @Override
    public String toString() {
        return id + " -> " + address;
    }
}

// Fix 6: each discount states its own percent; no instanceof chain
interface Discount {
    int percent();

    default long apply(long price) {
        return price - price * percent() / 100;
    }
}

class MemberDiscount implements Discount {
    @Override
    public int percent() { return 5; }
}

class FlashSaleDiscount implements Discount {
    @Override
    public int percent() { return 30; }
}

public class BadShopRefactored {
    public static void main(String[] args) {
        Cart cart = new Cart();
        cart.add(new Cart.Item("Kopi Gayo", 85_000, 2));
        System.out.println("Total cart: " + cart.total());

        Courier express = new ExpressCourier();
        System.out.println("Ongkir Ekspres 2 kg: " + express.cost(2));

        Address home = new Address("Jl. Sudirman 10", "Padang");
        SimpleOrder first = new SimpleOrder("ORD-1", home);
        SimpleOrder second = new SimpleOrder("ORD-2", home.withCity("Bukittinggi"));
        System.out.println(first);
        System.out.println(second);

        Discount flashSale = new FlashSaleDiscount();
        System.out.println("Harga flash sale: " + flashSale.apply(100_000));

        PaymentMethod voucher = new GiftVoucher();
        System.out.println(voucher.pay(30_000) + " | " + voucher.printInvoice());
    }
}
