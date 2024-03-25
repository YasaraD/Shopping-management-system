package Product;

//import java.io.Serializable;

public abstract  class Product implements Comparable<Product>  {
    private  String productID;

    private String productName;
    private int numberOfItemsAvailable;
    private double price;

    public Product(String productID, String productName, int numberOfItemsAvailable, double price) {
        this.productID = productID;
        this.productName = productName;
        this.numberOfItemsAvailable = numberOfItemsAvailable;
        this.price = price;
    }

    public String getProductID() {
        return productID;
    }
    public String getProductName() {
        return productName;
    }
    public int getNumberOfItemsAvailable() {
        return numberOfItemsAvailable;
    }
    public double getPrice() {
        return price;
    }
    public void setProductID(String productID) {
        this.productID = productID;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public void setNumberOfItemsAvailable(int numberOfItemsAvailable) {
        this.numberOfItemsAvailable = numberOfItemsAvailable;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID='" + productID + "\n" +
                ", productName='" + productName + "\n" +
                ", numberOfItemsAvailable=" + numberOfItemsAvailable +"\n"+
                ", price=" + price +
                '}';
    }
    @Override
    public int compareTo(Product otherProduct) {
        // Implement your comparison logic here
        return this.productID.compareTo(otherProduct.getProductID());

    }
    }


