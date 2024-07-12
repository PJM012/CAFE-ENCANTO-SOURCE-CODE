import java.awt.Color;
import javax.swing.text.MaskFormatter;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
public class addAccount extends JPanel {

    JTextField userField;
    JPasswordField passwordField;
    JFormattedTextField pinField; // Changed to JFormattedTextField
    JButton btnAddAccount;
    private JLabel error;
    String err = "All fields are required!";
    String user, pass, pin; // Variables to store username, password, and PIN
    private Image backgroundImage; // Background image field

    // Single path format for the background image
    private static final String BACKGROUND_IMAGE_PATH = "C:\\Users\\ADMIN\\Downloads\\FINAL\\Cafe Encanto\\images\\add acc bg.jpg"; // Example path

    /**
     * Create the panel.
     */
    public addAccount() {
        setLayout(null);
        setBounds(100, 100, 840, 619);

        // Load background image
        loadImageFromPath(BACKGROUND_IMAGE_PATH); // Load image from the specified path

        JLabel lblAddAccount = new JLabel("ADD AN ACCOUNT");
        lblAddAccount.setBounds(348, 45, 182, 21);
        lblAddAccount.setFont(new Font("Tahoma", Font.BOLD, 17));
        add(lblAddAccount);

        JLabel lblUserName = new JLabel("Create Username:");
        lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblUserName.setBounds(256, 134, 124, 21);
        add(lblUserName);

        JLabel lblPassword = new JLabel("Create Password:");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPassword.setBounds(256, 166, 124, 21);
        add(lblPassword);

        JLabel lblPin = new JLabel("Create PIN:");
        lblPin.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPin.setBounds(256, 198, 124, 21);
        add(lblPin);

        userField = new JTextField();
        userField.setBounds(426, 136, 147, 20);
        add(userField);
        userField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(426, 168, 147, 20);
        add(passwordField);

        
       // Inside your addAccount class constructor

try {
    MaskFormatter formatter = new MaskFormatter("######"); // Expecting 6 digits
    pinField = new JFormattedTextField(formatter);
    pinField.setValue(null);

    // Add focus listener to clear value if empty
    pinField.addFocusListener(new java.awt.event.FocusAdapter() {
        @Override
        public void focusLost(java.awt.event.FocusEvent evt) {
            if (pinField.getText().trim().isEmpty()) {
                pinField.setValue(null);
            }
        }
    });
} catch (java.text.ParseException ex) {
    ex.printStackTrace();
}
pinField.setBounds(426, 200, 147, 20);
add(pinField);


        btnAddAccount = new JButton("Add (+)");
        btnAddAccount.addActionListener((ActionEvent e) -> {
            user = userField.getText().trim();
            pass = new String(passwordField.getPassword()).trim().toLowerCase();
            Object pinObj = pinField.getValue();
        
            if (user.isEmpty() || pass.isEmpty() || pinObj == null || pinObj.toString().trim().isEmpty()) {
                error.setText(err);
            } else {
                pin = pinObj.toString().trim();
                
                // Validate PIN length
                if (pin.length() != 6) {
                    error.setText("PIN must be exactly 6 digits.");
                } else if (pass.length() > 8) {
                    error.setText("Password cannot exceed 8 characters.");
                    error.setBounds(318, 273, 225, 14);
                } else {
                    // Check if username already exists in the database
                    if (DB.usernameExists(user)) {
                        error.setText("Username is already taken.");
                    } else {
                        error.setText("");
                        // Add account details to the database
                        DB.addAccount(user, pass, pin);
                        userField.setText("");
                        passwordField.setText("");
                        pinField.setValue(null); // Clear PIN field after adding account
                    }
                }
            }
        });
        
        
        btnAddAccount.setBounds(426, 240, 147, 23);
        btnAddAccount.setBackground(new Color(92, 64, 51)); // Button background color
        btnAddAccount.setForeground(Color.WHITE);
        add(btnAddAccount);

        error = new JLabel("");
        error.setForeground(Color.RED);
        error.setBounds(368, 273, 201, 14);
        add(error);
    }

    /**
     * Load the background image from the specified path.
     *
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
