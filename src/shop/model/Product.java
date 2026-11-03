package shop.model;

public abstract class Product {
    private final String sku;
    private final String name;
    private final long price; // in rupiah

    protected Product(String sku, String name, long price){
        if(sku == null || sku.isEmpty()){
            throw new IllegalArgumentException("SKU cannot be null or empty");
        }
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if(price < 0){
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.sku = sku;
        this.name = name;
        this.price = price;
    }
    public String getSku(){return sku;}
    public String getName(){return name;}
    public long getPrice(){return price;}
        
    // Price the customer actually pays , subclasses may override it
        public long getFinalPrice(){return price;}

    // every concrete product must state its category
    public abstract String category();

    // A concrete method that relies on the abstract method category().
    public String getInfo(){
        return "[" + category() + "] " + name + " - Rp" + getFinalPrice();
    }
    


}
