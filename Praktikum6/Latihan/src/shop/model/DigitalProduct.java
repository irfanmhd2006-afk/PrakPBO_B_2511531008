package shop.model;

import shop.discount.Discountable;

public class DigitalProduct extends Product implements Discountable{
    private final String downloadUrl;
    private static final int DIGITAL_DISCOUNT_PERCENT = 10;
    @Override 
    public int discountPercent() {
        return DIGITAL_DISCOUNT_PERCENT; // 10% discount for digital products
    }
    
    public DigitalProduct(String sku, String name, long price, String downloadUrl) {
        super(sku, name, price);
        if(downloadUrl == null || downloadUrl.isEmpty()){
            throw new IllegalArgumentException("Download URL cannot be null or empty");
        }
        this.downloadUrl = downloadUrl;
    }
    public String getDownloadUrl(){return downloadUrl;}
    @Override
    public String category(){       
        return "Digital";
    }

    @Override
    public long getFinalPrice() {
        return applyDiscount(getPrice());
    }
}