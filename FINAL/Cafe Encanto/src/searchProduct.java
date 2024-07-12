import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class searchProduct extends JPanel {
    
    JTextField idField;
    JButton btnSearch;
    private JLabel error;
    String id, err = "Enter a Product ID!";
    private Image backgroundImage; // Background image field

    // Single path format for the background image
    private static final String BACKGROUND_IMAGE_PATH = "C:\\Users\\ADMIN\\Downloads\\FINAL\\Cafe Encanto\\images\\search p bg.png"; // Example path

    /**
     * Create the panel.
     */
    public searchProduct() {
        setLayout(null);
        setBounds(100, 100, 840, 619);

        // Load background image
        loadImageFromPath(BACKGROUND_IMAGE_PATH); // Load image from the specified path

        JLabel lblSearchProduct = new JLabel("SEARCH A PRODUCT");
        lblSearchProduct.setBounds(349, 84, 182, 21);
        lblSearchProduct.setFont(new Font("Tahoma", Font.BOLD, 17));
        add(lblSearchProduct);

        JLabel lblProductName = new JLabel("Search Product ID:");
        lblProductName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblProductName.setBounds(263, 156, 124, 21);
        add(lblProductName);

        idField = new JTextField();
        idField.setBounds(439, 158, 136, 20);
        add(idField);
        idField.setColumns(10);

        btnSearch = new JButton("Search");
        btnSearch.addActionListener((ActionEvent e) -> {
            if (idField.getText().equals("")) {
                error.setText(err);
            } else {
                error.setText("");
                id = idField.getText().trim();
                DB.searchProduct(id);
                idField.setText("");
            }
        });
        btnSearch.setBounds(439, 219, 136, 23);
		btnSearch.setBackground(new Color(92, 64, 51)); // Button background color
        btnSearch.setForeground(Color.WHITE); 
        add(btnSearch);

        error = new JLabel("");
        error.setForeground(Color.RED);
        error.setBounds(379, 277, 217, 14);
        add(error);
    }

    /**
     * Load the background image from the specified path.
     * @param imagePath The full path to the image file.
     */
    private void loadImageFromPath(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image if loaded
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
