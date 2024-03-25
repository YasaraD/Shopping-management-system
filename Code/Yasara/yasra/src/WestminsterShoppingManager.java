import Product.Product;
import Product.Electronic;
import Product.Clothing;
import org.junit.Test;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class WestminsterShoppingManager implements ShoppingManager {
     private static ArrayList<Product>productsList;
     private final int maxProduct = 50;

     public WestminsterShoppingManager(){
        this.productsList= new ArrayList<>(maxProduct);
    }


    @Test
     public  void addProduct(){

        if (productsList.size()>maxProduct){
            System.out.println("Maximum no of products added");
            return;
        }else{
            Scanner scanner = new Scanner(System.in);
            System.out.println("-------------------------------------");
            System.out.println("      [1] Add Electronic");
            System.out.println("      [2] Add Clothing");

            int choice = Validation.intValidatorOneAndTwo("Enter your Choice: ");

            switch (choice){
                case  1:
                    addElectronic();
                    break;
                case 2:
                    addClothing();
                    break;
                default:
                    System.out.println("Invalid Choice,Return to menu!!!");
            }
        }
    }

        @Test
        public void addElectronic(){
            System.out.println("\nProducts adding to ELECTRONIC!!");
        String productID = Validation.charValidation("Enter product ID: ");
        String productName = Validation.StringOnlyValidator("Enter product Name: ");
        int numberOfItemsAvailable = Validation.intValidator("Enter number of items available: ");
        double price = Validation.doubleValidator("Enter price: ");
        String brand = Validation.StringOnlyValidator("Enter brand: ");
        int warrantyperiod = Validation.intValidator("Enter warranty period(Weeks): ");

        productsList.add(new Electronic(productID,productName,numberOfItemsAvailable,price,brand,warrantyperiod));
            System.out.println("Products added to ELECTRONIC!!!\n");
        }

        @Test
        public void addClothing(){
                System.out.println("\nProducts adding to CLOTHING!!");
            String productID = Validation.charValidation("Enter Product ID: ");
            String productName = Validation.StringOnlyValidator("Enter Product Name: ");
            int numberOfItemsAvailable = Validation.intValidator("Enter number of items available: ");
            double price = Validation.doubleValidator("Enter price: ");
            String size = Validation.sizeValidation("Enter Size(S,M,L,XL): ");
            String colour = Validation.StringOnlyValidator("Enter Colour: ");

            productsList.add(new Clothing(productID,productName,numberOfItemsAvailable,price,size,colour));
            System.out.println("Products added to ELECTRONIC!!!\n");
        }

        @Test
        public  void deleteProduct(){
        Scanner scanner = new Scanner(System.in);
        String productIdToDelete = Validation.charValidation("Enter product ID to Delete:");

        boolean productFound = false;
        // Iterate through the productsList to find and remove the product
        for (Product product : productsList) {
            if (product.getProductID().equals(productIdToDelete)) {
                System.out.println( product+" Deleted !!!");
                productsList.remove(product);
                productFound = true;
                break;
            }
        }

        if (!productFound) {
            System.out.println("Product with ID " + productIdToDelete + " not found.");
        }

    }

    @Test
     public  void printProducts(){
        Collections.sort(productsList);
        for (Product product :productsList) {
            System.out.println(product.toString());
        }
    }

    @Test
     public void saveProductsToFile() {
        String directoryPathForCloths = "Products/Clothing";
        String directoryPathForElectronics = "Products/Electronics";
        String filename = "ProductsList.txt";
         System.out.println("Products have been SAVE to the file!!");

        File directoryC = new File(directoryPathForCloths);
        File directoryE = new File(directoryPathForElectronics);

        // Checking if directories exist, and creating them if they don't
        if (!directoryC.exists()) {
            directoryC.mkdirs(); // Creates the directory and any missing parent directories
        }
        if (!directoryE.exists()) {
            directoryE.mkdirs();
        }

        try {
            BufferedWriter writerC = new BufferedWriter(new FileWriter(directoryPathForCloths + "/" + filename));
            BufferedWriter writerE = new BufferedWriter(new FileWriter(directoryPathForElectronics + "/" + filename));

            for (Product product : productsList) {
                if(product.getNumberOfItemsAvailable() >0){
                    if (product instanceof Clothing clothing) {
                        writerC.write(clothing.getProductID() + "|" + clothing.getProductName() + "|" + clothing.getSize() + "|" + clothing.getColour() + "|" + clothing.getNumberOfItemsAvailable() + "|" + clothing.getPrice() + "|" );
                        writerC.newLine();
                    } else if (product instanceof Electronic electronic) {
                        writerE.write(electronic.getProductID() + "|" + electronic.getProductName() + "|" + electronic.getBrand() + "|" + electronic.getWarrantyperiod() + "|" + electronic.getNumberOfItemsAvailable() + "|" + electronic.getPrice() + "|" );
                        writerE.newLine();
                    }
                }

            }

            // Close the writers after writing data
            writerC.close();
            writerE.close();

            System.out.println("CLI.User data saved to file: " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }

    }

     public  ArrayList<Product> getProductFromFiles() {
        String directoryPathForCloths = "Products/Clothing";
        String directoryPathForElectronics = "Products/Electronics";
        String filename = "ProductsList.txt";
         System.out.println("Read From File!!");

        try {
            // Reading Products.Clothing file
            BufferedReader readerC = new BufferedReader(new FileReader(directoryPathForCloths + "/" + filename));
            String line;
            while ((line = readerC.readLine()) != null) {

                String[] data = line.split("\\|");


                String productID = data[0];
                String productName = data[1];
                String size = (data[2]);
                String color = data[3];
                int availableItems = Integer.parseInt(data[4]);
                double price = Double.parseDouble(data[5]);


                productsList.add(new Clothing(productID, productName, availableItems, price, size, color));
            }
            readerC.close();

            // Reading Products.Electronics file
            BufferedReader readerE = new BufferedReader(new FileReader(directoryPathForElectronics + "/" + filename));
            while ((line = readerE.readLine()) != null) {
                // Splitting the line by "|" to extract individual data
                String[] data = line.split("\\|");
                // Assuming the structure: ID|Name|Brand|WarrantyPeriod|AvailableItems|Price|Info
                String productID = data[0];
                String productName = data[1];
                String brand = data[2];
                int warrantyPeriod = Integer.parseInt(data[3]);
                int availableItems = Integer.parseInt(data[4]);
                double price = Double.parseDouble(data[5]);


                productsList.add(new Electronic(productID, productName, availableItems, price, brand , warrantyPeriod));
            }
            readerE.close();
            return productsList;

        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        return productsList;
    }

}
