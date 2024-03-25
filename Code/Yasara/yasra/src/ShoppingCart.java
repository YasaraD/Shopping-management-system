
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


public class ShoppingCart extends JFrame implements ActionListener {
    JTable table = new JTable(models);
    JPanel topPanel;
    JLabel label;
    JPanel middlePanel;
    static DefaultTableModel models = new DefaultTableModel();
    private WestminsterShoppingGUI shoppingGui;

    public double Total;
    public int electronic;
    public int clothing;
    public double sameCategoryDiscount;
    public double firstPurchaseDiscount;
    private static JPanel bottomPanel = new JPanel();
    private static JLabel label4 = new JLabel();
    private static JLabel label3 = new JLabel();
    private static JLabel label2 = new JLabel();
    private static JLabel label1 = new JLabel();

    void Loads() {
        Total=0;
        electronic=0;
        clothing=0;
        sameCategoryDiscount=0;
        firstPurchaseDiscount=0;
        models.setRowCount(0);
        BufferedReader reader;
        Map<String, Integer> productIdQuantityMap = new HashMap<>();
        Map<String, String[]> productDataMap = new HashMap<>();


        try {
            reader = new BufferedReader(new FileReader("Cart_Storage.txt"));
            String line = reader.readLine();

            while (line != null) {
                String[] components = line.split(",");
                if (components.length > 0) {
                    String productId = components[0].trim();
                    String productName = components[1].trim();
                    double price = Double.parseDouble(components[2].trim());
                    String info = components[3].trim();
                    String category = components[5].trim();

                    if (productIdQuantityMap.containsKey(productId)) {

                        int existingQty = productIdQuantityMap.get(productId);
                        productIdQuantityMap.put(productId, existingQty + 1);
                    } else {

                        productIdQuantityMap.put(productId, 1);
                        productDataMap.put(productId, new String[]{productName, info, String.valueOf(price)});
                    }
                    if (category.equals("Clothing")){
                        clothing++;
                    }else {
                        electronic++;
                    }

                }
                line = reader.readLine();
            }
            reader.close();

            // Update the table with aggregated data
            for (Map.Entry<String, Integer> entry : productIdQuantityMap.entrySet()) {
                String productId = entry.getKey();
                int qty = entry.getValue();
                String[] productData = productDataMap.get(productId);
                Object[] rowData = {
                        "<html>" + productId + "<br>" + productData[0] + "<br>" + productData[1] + "</html>",
                        qty,
                        qty * Double.parseDouble(productData[2])
                };
                models.addRow(rowData);
                Total+=qty*Double.parseDouble(productData[2]);
            }

            bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            bottomPanel.setLayout(new GridLayout(4, 1));
            Font labelFont = new Font("Arial", Font.PLAIN, 16);

            label1.setText("Total           "+Total+" £ ");
            label1.setFont(labelFont);
            label1.setPreferredSize(new Dimension(label1.getPreferredSize().width, 40));
            label1.setHorizontalAlignment(SwingConstants.RIGHT);
            bottomPanel.add(label1);

            if (true) {
                firstPurchaseDiscount = (Total * 10) / 100;
                label2.setText("First Purchase Discount (10%)          -" + firstPurchaseDiscount + " £ ");
                label2.setFont(labelFont);
                label2.setPreferredSize(new Dimension(label2.getPreferredSize().width, 40));
                label2.setHorizontalAlignment(SwingConstants.RIGHT);
                bottomPanel.add(label2);
            }

            if (electronic>=3 || clothing>=3){
                sameCategoryDiscount = (Total*20)/100;
                label3.setText("Three Items in same Category Discount (20%)          -"+sameCategoryDiscount+" £ ");
                label3.setFont(labelFont);
                label3.setPreferredSize(new Dimension(label3.getPreferredSize().width, 40));
                label3.setHorizontalAlignment(SwingConstants.RIGHT);
                bottomPanel.add(label3);
            }

            label4.setText("Final Total      "+String.format("%.2f",Total-(sameCategoryDiscount+firstPurchaseDiscount))+" £ ");
            label4.setFont(new Font("Arial",Font.BOLD,22));
            label4.setPreferredSize(new Dimension(label4.getPreferredSize().width, 40));
            label4.setHorizontalAlignment(SwingConstants.RIGHT);
            bottomPanel.add(label4);

            bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 110, 200));
            this.add(bottomPanel, BorderLayout.SOUTH);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ShoppingCart(WestminsterShoppingGUI shoppingGui) {

        this.shoppingGui = shoppingGui;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    FileWriter fw = new FileWriter("Cart_Storage.txt", false);
                    PrintWriter pw = new PrintWriter(fw, false);
                    pw.flush();
                    pw.close();
                    fw.close();
                } catch (Exception exception) {
                    System.out.println("Exception have been caught");
                }

                try {
                    FileWriter myWriter = new FileWriter("UsersData.txt",true);
//                    myWriter.write(+"\n");
                    myWriter.close();

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        this.setSize(880, 650);
        this.setResizable(false);

        // Top Panel
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton b1 = new JButton("Go back");
        b1.setForeground(Color.WHITE);
        b1.setBackground(Color.BLACK);
        b1.setFocusable(false);
        topPanel.add(b1);

        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (shoppingGui== null) {

                    shoppingGui.setVisible(true);
                    System.out.println("hello");
                } else {

                    shoppingGui.toFront();
                }

                shoppingGui.setVisible(true);
                ShoppingCart.this.setVisible(false);
                remove(bottomPanel);

            }
        });

        label = new JLabel("Shopping Cart.");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setBorder(BorderFactory.createEmptyBorder(20, 250, 0, 0));
        topPanel.add(label);

        // Middle Panel
        middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        models.addColumn("Product");
        models.addColumn("Quantity");
        models.addColumn("Price");
        Loads();

        // Assuming 'table' is your JTable
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(720, 200));
        table.setRowHeight(60);

        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 60));
        scrollPane.setPreferredSize(new Dimension(780, 240));

        middlePanel.add(scrollPane);
        middlePanel.setBorder(BorderFactory.createEmptyBorder(20, 35, 0, 35));

        // Bottom Panel

        // Add components to the main frame
        this.setLayout(new BorderLayout());

        this.add(topPanel, BorderLayout.NORTH);
        this.add(middlePanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.setTitle("Westminster Shopping");
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

}