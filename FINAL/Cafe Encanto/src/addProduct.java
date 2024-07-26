import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class addProduct extends JPanel {
    
    JTextField idField;
    JTextArea descField;
    JTextField quanField;
    JTextField expDateField;
    JLabel error;
    JComboBox<String> categories;
    String id, detail, comp;
    int quan;
    String err = "All fields are required!";
    private Image backgroundImage;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public addProduct() {
        setLayout(null);
        setBounds(100, 100, 840, 619);
        
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\ADMIN\\Downloads\\FINAL\\Cafe Encanto\\images\\add p bg.png")); 
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        
        JLabel lblAddProduct = new JLabel("ADD A NEW PRODUCT");
        lblAddProduct.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblAddProduct.setBounds(335, 45, 195, 21);
        add(lblAddProduct);
        
        JLabel lblProductName = new JLabel("Product ID:");
        lblProductName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblProductName.setBounds(266, 136, 124, 21);
        add(lblProductName);
        
        JLabel lblProductDescription = new JLabel("Product Details:");
        lblProductDescription.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblProductDescription.setBounds(266, 178, 139, 21);
        add(lblProductDescription);
        
        idField = new JTextField();
        idField.setColumns(20); 
        idField.setBounds(439, 137, 136, 20);
        add(idField);   
        
        descField = new JTextArea();
        descField.setBounds(439, 178, 136, 25);
        descField.setColumns(20); 
        add(descField);
        
        JLabel lblCategories = new JLabel("Categories:");
        lblCategories.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCategories.setBounds(266, 241, 124, 21);
        add(lblCategories);
        
        categories = new JComboBox<>();
        categories.setBounds(439, 243, 136, 20);
        categories.setBackground(new Color(196, 164, 132)); 
        categories.setForeground(Color.WHITE); 
        add(categories);
        categories.addItem("All");
        categories.addItem("Pastry");
        categories.addItem("Coffee");
        categories.addItem("Fruit Tea");
        categories.addItem("Espresso");
        categories.addItem("Desserts");
        categories.addItem("Snacks");
        categories.addItem("Rice Meal");
        
        JLabel lblQuantity = new JLabel("Items available:");
        lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblQuantity.setBounds(266, 273, 124, 21);
        add(lblQuantity);
        
        quanField = new JTextField();
        quanField.setColumns(10);
        quanField.setBounds(439, 274, 136, 20);
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

        JLabel lblExpDate = new JLabel("Exp Date(YYYY-MM-DD):");
        lblExpDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblExpDate.setBounds(266, 305, 154, 21);
        add(lblExpDate);
        
        expDateField = new JTextField();
        expDateField.setColumns(10);
        expDateField.setBounds(439, 306, 136, 20);
        expDateField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0' && c <= '9') || (c == '-') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
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
        add(expDateField);

        JButton btnAddProduct = new JButton("Add (+)");
        btnAddProduct.setBounds(439, 339, 136, 23);
        btnAddProduct.setBackground(new Color(92, 64, 51)); 
        btnAddProduct.setForeground(Color.WHITE); 

        btnAddProduct.addActionListener((ActionEvent arg0) -> {
            if (quanField.getText().isEmpty() || idField.getText().isEmpty() || expDateField.getText().isEmpty()) {
                error.setText(err);
            } else {
                try {
                    quan = Integer.parseInt(quanField.getText().trim());
                    id = idField.getText().trim();
                    detail = descField.getText().trim();
                    comp = categories.getSelectedItem().toString();
                    String expDate = expDateField.getText().trim();
                    
                    // Validate date format (e.g., "yyyy-MM-dd")
                    if (!expDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        error.setText("Invalid Expiration Date format!");
                        return;
                    }
                    
                    Date expirationDate = dateFormat.parse(expDate);
                    Date currentDate = new Date();
                    
                    // Check if expiration date is before the current date
                    if (expirationDate.before(currentDate)) {
                        error.setText("Exp date can't be in past/current!");
                        return;
                    }
                    
                    error.setText("");
                    DB.addProductToDB(id, detail, comp, quan, expDate);           
                    idField.setText("");
                    quanField.setText("");
                    descField.setText("");
                    expDateField.setText("");
                } catch (NumberFormatException e) {
                    error.setText("Quantity must be a number!");
                } catch (ParseException e) {
                    error.setText("Invalid Expiration Date format!");
                }
            }
        });
        add(btnAddProduct);
        
        error = new JLabel("");
        error.setForeground(Color.RED);
        error.setBounds(376, 364, 200, 21);
        add(error);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
      
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
