package Product;

public class Clothing extends Product {
    private String size;
    private String colour;

    public Clothing(String productID, String productName, int numberOfItemsAvailable, double price, String size, String colour) {
        super(productID, productName, numberOfItemsAvailable, price);
        this.size = size;
        this.colour = colour;
    }

    public String getSize() {
        return size;
    }

    public String getColour() {
        return colour;
    }

    @Override
    public String toString() {
        return ("\n"+
                " Clothing Product Name:" + getProductName() + "\n"+
                " Product ID:" + getProductID() +"\n"+
                " Size of the clothe:" + getSize()+"\n"+
                " Colour:" +getColour()+"\n"+
                " Price:"+getPrice()+"\n"+
                " Available Items:"+getNumberOfItemsAvailable());
    }

   @Override
   public int compareTo(Product o) {
        return this.getProductID().compareTo(o.getProductID());}
}

