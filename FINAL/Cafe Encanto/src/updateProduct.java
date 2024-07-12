import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    JLabel error;
    String id, detail, comp;
    int quan;
    String err = "All fields are required!";
    private final Image backgroundImage;

    /**
     * Create the panel.
     */
    public updateProduct() {
        // Load background image
        backgroundImage = new ImageIcon("C:\\Users\\ADMIN\\Downloads\\FINAL\\Cafe Encanto\\images\\update bg.jpg").getImage();

        setLayout(null);
        setBounds(100, 100, 840, 619);

        JLabel lblUpdateProduct = new JLabel("UPDATE A PRODUCT");
        lblUpdateProduct.setBounds(335, 45, 182, 21);
        lblUpdateProduct.setFont(new Font("Tahoma", Font.BOLD, 17));
        add(lblUpdateProduct);

        JLabel lblProductName = new JLabel("Product ID:");
        lblProductName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblProductName.setBounds(266, 136, 124, 21);
        add(lblProductName);

        JLabel lblProductDescription = new JLabel("Product details:\r\n");
        lblProductDescription.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblProductDescription.setBounds(266, 178, 139, 21);
        add(lblProductDescription);

        idField = new JTextField();
        idField.setBounds(439, 137, 136, 20);
        add(idField);
        idField.setColumns(10);

        descField = new JTextArea();
        descField.setBounds(439, 178, 136, 25);
        descField.setColumns(20); 
        add(descField);

        JLabel lblCategories = new JLabel("Categories:");
        lblCategories.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCategories.setBounds(266, 241, 124, 21);
        add(lblCategories);

        idField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                id = idField.getText().trim() + e.getKeyChar();
                ArrayList<String> data = DB.searchP(id);
                if (data.size() == 3) {
                    descField.setText(data.get(0));
                    quanField.setText(data.get(2));
                    switch (data.get(1)) {
                        case "Pastry" -> categories.setSelectedIndex(0);
                        case "Coffee" -> categories.setSelectedIndex(1);
                        case "Fruit Tea" -> categories.setSelectedIndex(2);
                        case "Espresso" -> categories.setSelectedIndex(3);
                        case "Desserts" -> categories.setSelectedIndex(4);
                        case "Snacks" -> categories.setSelectedIndex(5);
                        case "Rice Meal" -> categories.setSelectedIndex(6);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent arg0) {
            }

            @Override
            public void keyPressed(KeyEvent arg0) {
            }
        });

        btnUpdateProduct = new JButton("UPDATE (✅)");
        btnUpdateProduct.addActionListener((ActionEvent arg0) -> {
            if (quanField.getText().isEmpty() || idField.getText().isEmpty()) {
                error.setText(err);
            } else {
                try {
                    quan = Integer.parseInt(quanField.getText().trim());
                    error.setText("");
                    id = idField.getText().trim();
                    detail = descField.getText().trim();
                    comp = categories.getSelectedItem().toString();
                    DB.updateProductToDB(id, detail, comp, quan);
                    idField.setText("");
                    quanField.setText("");
                    descField.setText("");
                } catch (NumberFormatException e) {
                    error.setText("Quantity must be a number!");
                }
            }
        });
        btnUpdateProduct.setBounds(439, 338, 136, 23);
        btnUpdateProduct.setBackground(new Color(92, 64, 51)); // Button background color
        btnUpdateProduct.setForeground(Color.WHITE); 
        add(btnUpdateProduct);

        quanField = new JTextField();
        quanField.setColumns(10);
        quanField.setBounds(439, 278, 136, 20);
        // Add key listener to allow only numeric input
        quanField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0' && c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        add(quanField);

        JLabel lblQuantity = new JLabel("Items Available:");
        lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblQuantity.setBounds(266, 276, 124, 21);
        add(lblQuantity);

        categories = new JComboBox<>();
        categories.setBounds(439, 243, 136, 20);
        categories.setBackground(new Color(196, 164, 132)); // Button background color
        categories.setForeground(Color.WHITE); 
        add(categories);

        error = new JLabel("");
        error.setForeground(Color.RED);
        error.setBounds(359, 95, 286, 14);
        add(error);

        categories.addItem("General");
        categories.addItem("Pastry");
        categories.addItem("Coffee");
        categories.addItem("Fruit Tea");
        categories.addItem("Espresso");
        categories.addItem("Desserts");
        categories.addItem("Snacks");
        categories.addItem("Rice Meal");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
