import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class updateProduct extends JPanel {

    JTextField idField;
    JTextArea descField;
    JButton btnUpdateProduct;
    JComboBox<String> categories;
    JTextField quanField;
    JTextField expDateField; // Text field for expiration date
    JLabel error;
    String id, detail, comp, expDate;
    int quan;
    String err = "All fields are required!";
    private final Image backgroundImage;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Create the panel.
     */
    public updateProduct() {
        backgroundImage = new ImageIcon("C:\\Users\\ADMIN\\Downloads\\FINAL\\Cafe Encanto\\images\\update bg.jpg").getImage();

        setLayout(null);
        setBounds(100, 100, 840, 619);

        // Panel title
        JLabel lblUpdateProduct = new JLabel("UPDATE A PRODUCT");
        lblUpdateProduct.setBounds(335, 45, 182, 21);
        lblUpdateProduct.setFont(new Font("Tahoma", Font.BOLD, 17));
        add(lblUpdateProduct);

        // Product ID label and field
        JLabel lblProductName = new JLabel("Product ID:");
        lblProductName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblProductName.setBounds(266, 136, 124, 21);
        add(lblProductName);

        idField = new JTextField();
        idField.setBounds(439, 137, 136, 20);
        idField.setColumns(10);
        add(idField);

        // Product description label and field
        JLabel lblProductDescription = new JLabel("Product details:");
        lblProductDescription.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblProductDescription.setBounds(266, 178, 139, 21);
        add(lblProductDescription);

        descField = new JTextArea();
        descField.setBounds(439, 178, 136, 25);
        descField.setColumns(20);
        add(descField);

        // Categories label and dropdown
        JLabel lblCategories = new JLabel("Categories:");
        lblCategories.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCategories.setBounds(266, 241, 124, 21);
        add(lblCategories);

        categories = new JComboBox<>();
        categories.setBounds(439, 243, 136, 20);
        categories.setBackground(new Color(196, 164, 132));
        categories.setForeground(Color.WHITE);
        add(categories);

        // Quantity label and field
        JLabel lblQuantity = new JLabel("Items Available:");
        lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblQuantity.setBounds(266, 276, 124, 21);
        add(lblQuantity);

        quanField = new JTextField();
        quanField.setBounds(439, 278, 136, 20);
        quanField.setColumns(10);
        quanField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0' && c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) { }

            @Override
            public void keyReleased(KeyEvent e) { }
        });
        add(quanField);

        // Expiration date label and field
        JLabel lblExpirationDate = new JLabel("Exp Date(YYYY-MM-DD):");
        lblExpirationDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblExpirationDate.setBounds(266, 314, 200, 21);
        add(lblExpirationDate);

        expDateField = new JTextField();
        expDateField.setBounds(439, 315, 136, 20);
        expDateField.setColumns(10);
        expDateField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0' && c <= '9') || (c == '-') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) { }

            @Override
            public void keyReleased(KeyEvent e) { }
        });
        add(expDateField);

        // Error label
        error = new JLabel("");
        error.setForeground(Color.RED);
        error.setBounds(359, 95, 286, 14);
        add(error);

        // Populate category dropdown
        categories.addItem("All");
        categories.addItem("Pastry");
        categories.addItem("Coffee");
        categories.addItem("Fruit Tea");
        categories.addItem("Espresso");
        categories.addItem("Desserts");
        categories.addItem("Snacks");
        categories.addItem("Rice Meal");

        // Listener for Product ID input
        idField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Clear fields if no ID is entered
                if (idField.getText().trim().isEmpty()) {
                    clearFields();
                    return;
                }

                // Get updated ID
                id = idField.getText().trim() + e.getKeyChar();
                ArrayList<String> data = DB.searchP(id);

                if (data.size() == 4) { // Ensure data contains all 4 expected values
                    descField.setText(data.get(0));
                    quanField.setText(data.get(2));
                    expDateField.setText(data.get(3)); // Set expiration date
                    // Set category selection
                    setCategorySelection(data.get(1));
                }
            }

            @Override
            public void keyReleased(KeyEvent arg0) { }

            @Override
            public void keyPressed(KeyEvent arg0) { }
        });

        // Update button action
        btnUpdateProduct = new JButton("UPDATE (✅)");
        btnUpdateProduct.setBounds(439, 378, 136, 23);
        btnUpdateProduct.setBackground(new Color(92, 64, 51));
        btnUpdateProduct.setForeground(Color.WHITE);
        btnUpdateProduct.addActionListener((ActionEvent arg0) -> {
            // Validate fields
            if (quanField.getText().isEmpty() || idField.getText().isEmpty() || expDateField.getText().isEmpty()) {
                error.setText(err);
                return;
            }

            try {
                quan = Integer.parseInt(quanField.getText().trim());
                expDate = expDateField.getText().trim();

                // Validate expiration date format
                if (!expDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    error.setText("Invalid Expiration Date format!");
                    return;
                }

                // Check if expiration date is before the current date
                Date expirationDate = dateFormat.parse(expDate);
                Date currentDate = new Date();
                if (expirationDate.before(currentDate)) {
                    error.setText("Exp date can't be in past/current!");
                    return;
                }

                // Clear error and update database
                error.setText("");
                id = idField.getText().trim();
                detail = descField.getText().trim();
                comp = categories.getSelectedItem().toString();
                DB.updateProductToDB(id, detail, comp, quan, expDate); // Update method to handle expiration date
                clearFields(); // Clear all fields after update
            } catch (NumberFormatException e) {
                error.setText("Quantity must be a number!");
            } catch (ParseException e) {
                error.setText("Invalid Expiration Date format!");
            }
        });
        add(btnUpdateProduct);
    }

    // Method to set the category selection based on retrieved data
    private void setCategorySelection(String category) {
        switch (category) {
            case "Pastry" -> categories.setSelectedIndex(0);
            case "Coffee" -> categories.setSelectedIndex(1);
            case "Fruit Tea" -> categories.setSelectedIndex(2);
            case "Espresso" -> categories.setSelectedIndex(3);
            case "Desserts" -> categories.setSelectedIndex(4);
            case "Snacks" -> categories.setSelectedIndex(5);
            case "Rice Meal" -> categories.setSelectedIndex(6);
            default -> categories.setSelectedIndex(0); // Default or "All" if unknown category
        }
    }

    // Method to clear all fields
    private void clearFields() {
        idField.setText("");
        descField.setText("");
        quanField.setText("");
        expDateField.setText("");
        categories.setSelectedIndex(0); // Default to "All"
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
