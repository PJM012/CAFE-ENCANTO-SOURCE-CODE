import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

public class Login extends JFrame {

    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private String password, username;
    private JLabel error;
    private final String errorText = "INVALID USERNAME OR PASSWORD!";
    private JLabel lblCaddeyLogin;
    private JButton btnLogin;
    private JLabel label;
    private AdminPanel adminPanel;

    // Admin PIN verification fields
    private JDialog pinDialog;
    private JPasswordField pinField;
    private JButton btnVerifyPin;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Login frame = new Login();
                frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\ADMIN\\Downloads\\FINAL\\Cafe Encanto\\images\\app logo.jpg"));
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public Login() {
        GUI();
        setResizable(false); // Set frame not resizable

        // Verify admin PIN before showing login
        verifyAdminPin();
    }

    void GUI() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 531, 387);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Load the background image
        JLabel background = new JLabel(new ImageIcon("C:\\Users\\ADMIN\\Downloads\\FINAL\\Cafe Encanto\\images\\login cafe bg.jpg"));
        background.setBounds(0, 0, 531, 387);
        contentPane.add(background);
        background.setLayout(null);

        JLabel lblUserName = new JLabel("Enter Username:");
        lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblUserName.setForeground(Color.WHITE);
        lblUserName.setBounds(144, 141, 120, 14);
        background.add(lblUserName);

        usernameField = new JTextField();
        usernameField.setBounds(280, 140, 129, 20);
        usernameField.setForeground(Color.BLACK); // Text color inside the field
        usernameField.setBackground(Color.WHITE); // Background color of the field
        background.add(usernameField);
        usernameField.setColumns(10);

        JLabel lblPassword = new JLabel("Enter Password:");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(144, 174, 120, 14);
        background.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(280, 173, 129, 20);
        passwordField.setForeground(Color.BLACK); // Text color inside the field
        passwordField.setBackground(Color.WHITE); // Background color of the field
        background.add(passwordField);

        // Show/Hide Password toggle button
    JToggleButton toggleShowPassword = new JToggleButton("Show/Hide Pass");
    toggleShowPassword.addActionListener(e -> {
        if (toggleShowPassword.isSelected()) {
            passwordField.setEchoChar('\0'); // Show password
        } else {
            passwordField.setEchoChar('*'); // Hide password
        }
    });
    toggleShowPassword.setBounds(282, 197, 126, 23); // Adjust position as needed
    toggleShowPassword.setBackground(new Color(255,234,230));
    background.add(toggleShowPassword);


        passwordField.addActionListener((ActionEvent e) -> btnLogin.doClick());

        btnLogin = new JButton("SIGN-IN");
        btnLogin.setBackground(new Color(92, 64, 51)); // Button background color
        btnLogin.setForeground(Color.WHITE); // Button text color

        btnLogin.addActionListener((var arg0) -> {
            password = new String(passwordField.getPassword());
            username = usernameField.getText().toLowerCase();

            if (password.isEmpty() || username.isEmpty()) {
                error.setText(errorText);
            } else {
                error.setText("");
                if (DB.verifyLogin(username, password)) {
                    error.setText("");
                    adminPanel.displayHome();
                    dispose();
                } else {
                    error.setText(errorText);
                }
            }
        });
        btnLogin.setBounds(301, 227, 89, 23);
        background.add(btnLogin);

        error = new JLabel("");
        error.setForeground(new Color(92, 64, 51));
        error.setBounds(150, 270, 270, 20);
        error.setFont(new Font("Tahoma", Font.PLAIN, 15)); // Adjust the font size here
        background.add(error);

        lblCaddeyLogin = new JLabel("Welcome to Cafe Encanto Inventory System!");
    
        lblCaddeyLogin.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblCaddeyLogin.setForeground(Color.WHITE);
        lblCaddeyLogin.setBounds(70, 26, 382, 40);
        background.add(lblCaddeyLogin);

        label = new JLabel("");
        label.setIcon(new ImageIcon("E:\\XAMPP\\htdocs\\logo.png"));
        label.setBounds(10, 11, 167, 91);
        background.add(label);
    }

    private void verifyAdminPin() {
        pinDialog = new JDialog(this, "Verification Panel", true); // Set modal dialog
        pinDialog.setSize(450, 300);
        pinDialog.setLocationRelativeTo(this);
        pinDialog.setLayout(new BorderLayout());
        pinDialog.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\ADMIN\\Downloads\\FINAL\\Cafe Encanto\\images\\app logo.jpg")); // Replace with your icon path
    
        // Prevent the user from closing the dialog manually
        pinDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        pinDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(pinDialog, "Do you wish to continue?", "Verification Failed", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(pinDialog, "Verification Failed. Exiting application.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0); // Exit the application
                } else {
                    // Clear PIN field and allow user to retry
                    pinField.setText("");
                }
            }
        });
    
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
    
        JLabel lblPin = new JLabel("To Continue, Enter Admin PIN:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblPin, gbc);
    
        pinField = new JPasswordField(10); // 10 is initial size, but custom document will limit to 6 digits
        pinField.setEchoChar('*'); // Set echo character to hide PIN input
        pinField.setDocument(new NumericDocument()); 
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(pinField, gbc);
    
        // Add toggle button to show/hide PIN
        JToggleButton toggleShowPin = new JToggleButton("Show/Hide PIN");
        toggleShowPin.setBackground(new Color(255,234,230));
        toggleShowPin.addActionListener(e -> {
            if (toggleShowPin.isSelected()) {
                pinField.setEchoChar('\0'); // Show PIN
            } else {
                pinField.setEchoChar('*'); // Hide PIN
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(toggleShowPin, gbc);
    
        btnVerifyPin = new JButton("Verify");
        btnVerifyPin.setBackground(new Color(92, 64, 51)); // Button background color
        btnVerifyPin.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(btnVerifyPin, gbc);
    
        btnVerifyPin.addActionListener((ActionEvent e) -> {
            String enteredPin = new String(pinField.getPassword());
            String storedPin = DB.getAdminPin(); // Replace with your method to retrieve the stored admin PIN from DB
    
            if (enteredPin.equals(storedPin)) {
                pinDialog.dispose(); // Close PIN verification dialog
                setVisible(true); // Show main login frame
            } else {
                JOptionPane.showMessageDialog(pinDialog, "Invalid PIN. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
                pinField.setText(""); // Clear PIN field
            }
        });
    
        pinDialog.add(panel, BorderLayout.CENTER);
        pinDialog.setVisible(true);
    }
    

   // Custom Document class to allow only numeric input and limit length in PIN field
private static class NumericDocument extends javax.swing.text.PlainDocument {
    private static final int LIMIT = 6; // Limit PIN length to 6 digits
    
    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null) {
            return;
        }

        // Check if the input is numeric
        char[] chars = str.toCharArray();
        for (char ch : chars) {
            if (!Character.isDigit(ch)) {
                return;
            }
        }

        // Check if adding this string would exceed the limit
        if (getLength() + str.length() <= LIMIT) {
            super.insertString(offs, new String(chars), a);
        }
    }
}


    public void setAdminPanel(AdminPanel adminPanel) {
        this.adminPanel = adminPanel;
    }

    @Override
    public void setExtendedState(int state) {
        super.setExtendedState(JFrame.NORMAL); // Always keep in normal state
    }
}