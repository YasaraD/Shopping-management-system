package Product;

public class Electronic extends Product{
    private String brand;
    private int warrantyperiod;

    public Electronic(String productID, String productName, int numberOfItemsAvailable, double price, String brand, int warrantyperiod) {
        super(productID, productName, numberOfItemsAvailable, price);
        this.brand = brand;
        this.warrantyperiod = warrantyperiod;
    }
    public String getBrand() {
        return brand;
    }
    public int getWarrantyperiod() {
        return warrantyperiod;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public void setWarrantyperiod(int warrantyperiod) {
        this.warrantyperiod = warrantyperiod;
    }


    @Override
    public String toString() {
        return ("\n" +
                " Electronics Product Name:" + getProductName() + "\n" +
                " Product ID:" + getProductID() + "\n" +
                " Brand of the Item:" + getBrand() + "\n" +
                " Warranty period of the Item(Weeks):" + getWarrantyperiod() + "\n" +
                " Price:" + getPrice() + "\n" +
                " Available Items:" + getNumberOfItemsAvailable())+"\n";
    }
    @Override
    public int compareTo(Product o) {
        return this.getProductID().compareTo(o.getProductID());
    }
}

