package shop.model;
public final class Address {
    private final String street; 
    private final String city;
    private final String postalCode;
    public Address(String street , String city, String postalCode){
        if(street == null || street.isBlank()){
            throw new IllegalArgumentException("street cannot be null or empty");
        }
        if(city == null || city.isBlank()){
            throw new IllegalArgumentException("street cannot be null or empty");
        }
        if(postalCode == null || postalCode.isBlank()){
            throw new IllegalArgumentException("street cannot be null or empty");
        }
        this.street = street;
        this.city = city;
        this.postalCode= postalCode;
    }

    public String toString(){
        return street + ", " + city + " " + postalCode;
    }
}
