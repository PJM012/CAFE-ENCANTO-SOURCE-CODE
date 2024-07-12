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

public class deleteProduct extends JPanel {

    JTextField idField;
    JButton btnDeleteProduct;
    private JLabel error;
    String id, err = "The field can't be empty!";
    private final Image backgroundImage;

    /**
     * Create the panel.
     */
    public deleteProduct() {
        // Load background image
        backgroundImage = new ImageIcon("C:\\Users\\ADMIN\\Downloads\\FINAL\\Cafe Encanto\\images\\del bg.png").getImage();

        setLayout(null);
        setBounds(100, 100, 840, 619);

        JLabel lblDeleteProduct = new JLabel("DELETE A PRODUCT");
        lblDeleteProduct.setBounds(369, 84, 182, 21);
        lblDeleteProduct.setFont(new Font("Tahoma", Font.BOLD, 17));
        add(lblDeleteProduct);

        JLabel lblProductName = new JLabel("Enter Product ID:");
        lblProductName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblProductName.setBounds(289, 156, 124, 21);
        add(lblProductName);

        idField = new JTextField();
        idField.setBounds(439, 158, 136, 20);
        add(idField);
        idField.setColumns(10);

        btnDeleteProduct = new JButton("Delete (-)");
        btnDeleteProduct.addActionListener((ActionEvent e) -> {
            if (idField.getText().equals("")) {
                error.setText(err);
            } else {
                error.setText("");
                id = idField.getText().trim();
                DB.deleteProductToDB(id);
                idField.setText("");
            }
        });
        btnDeleteProduct.setBounds(439, 219, 136, 23);
		btnDeleteProduct.setBackground(new Color(92, 64, 51)); // Button background color
        btnDeleteProduct.setForeground(Color.WHITE); 
        add(btnDeleteProduct);

        error = new JLabel("");
        error.setForeground(Color.RED);
        error.setBounds(379, 277, 217, 14);
        add(error);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
