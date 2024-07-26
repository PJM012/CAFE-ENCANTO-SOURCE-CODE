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
    private JDialog pinDialog;
    private JPasswordField pinField;
    private JButton btnVerifyPin;

   
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

    public Login() {
        GUI();
        setResizable(false);     
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
        usernameField.setForeground(Color.BLACK); 
        usernameField.setBackground(Color.WHITE); 
        background.add(usernameField);
        usernameField.setColumns(10);
    
        JLabel lblPassword = new JLabel("Enter Password:");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(144, 174, 120, 14);
        background.add(lblPassword);
    
        ImageIcon eyeOpenIcon = new ImageIcon(new ImageIcon("C:\\Users\\ADMIN\\Downloads\\FINAL\\Cafe Encanto\\images\\eye open.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
        ImageIcon eyeClosedIcon = new ImageIcon(new ImageIcon("C:\\Users\\ADMIN\\Downloads\\FINAL\\Cafe Encanto\\images\\eye closed.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBounds(280, 173, 129, 20); 
    
        passwordField = new JPasswordField();
        passwordField.setEchoChar('*'); 
        passwordField.setForeground(Color.BLACK); 
        passwordField.setBackground(Color.WHITE); 
    
      
        JLabel eyeLabel = new JLabel(eyeClosedIcon);
        eyeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        eyeLabel.setPreferredSize(new Dimension(24, 24));
    
        eyeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (passwordField.getEchoChar() == '\0') {
                    passwordField.setEchoChar('*'); 
                    eyeLabel.setIcon(eyeClosedIcon);
                } else {
                    passwordField.setEchoChar('\0'); 
                    eyeLabel.setIcon(eyeOpenIcon);
                }
            }
        });
    
     
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.add(eyeLabel, BorderLayout.EAST);
    
        background.add(passwordPanel);
    
        passwordField.addActionListener((ActionEvent e) -> btnLogin.doClick());
    
        btnLogin = new JButton("SIGN-IN");
        btnLogin.setBackground(new Color(92, 64, 51)); 
        btnLogin.setForeground(Color.WHITE); 
    
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
        error.setFont(new Font("Tahoma", Font.PLAIN, 15));
        background.add(error);
    
        lblCaddeyLogin = new JLabel("Welcome to Café Encanto Product Inventory System!");
        lblCaddeyLogin.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblCaddeyLogin.setForeground(Color.WHITE);
        lblCaddeyLogin.setBounds(70, 35, 392, 40);
        background.add(lblCaddeyLogin);
    
        label = new JLabel("");
        label.setIcon(new ImageIcon("E:\\XAMPP\\htdocs\\logo.png"));
        label.setBounds(10, 11, 167, 91);
        background.add(label);
    }
    
    private void verifyAdminPin() {
        pinDialog = new JDialog(this, "Verification Panel", true);
        pinDialog.setSize(450, 300);
        pinDialog.setLocationRelativeTo(this);
        pinDialog.setLayout(new BorderLayout());
        pinDialog.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\ADMIN\\Downloads\\FINAL\\Cafe Encanto\\images\\app logo.jpg")); 
        pinDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        pinDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(pinDialog, "Do you wish to continue?", "Verification Failed", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(pinDialog, "Verification Failed. Exiting application.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0); 
                } else {     
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
    
     
        ImageIcon eyeOpenIcon = new ImageIcon(new ImageIcon("C:\\Users\\ADMIN\\Downloads\\FINAL\\Cafe Encanto\\images\\eye open.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
        ImageIcon eyeClosedIcon = new ImageIcon(new ImageIcon("C:\\Users\\ADMIN\\Downloads\\FINAL\\Cafe Encanto\\images\\eye closed.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
        JPanel pinPanel = new JPanel(new BorderLayout());
        pinPanel.setPreferredSize(new Dimension(210, 30)); 
    
        pinField = new JPasswordField(10); 
        pinField.setEchoChar('*'); 
        pinField.setDocument(new NumericDocument()); 
    
      
        JLabel eyeLabel = new JLabel(eyeClosedIcon);
        eyeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        eyeLabel.setPreferredSize(new Dimension(24, 24)); 
    
        eyeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (pinField.getEchoChar() == '\0') {
                    pinField.setEchoChar('*'); 
                    eyeLabel.setIcon(eyeClosedIcon);
                } else {
                    pinField.setEchoChar('\0'); 
                    eyeLabel.setIcon(eyeOpenIcon);
                }
            }
        });
    
        
        pinPanel.add(pinField, BorderLayout.CENTER);
        pinPanel.add(eyeLabel, BorderLayout.EAST);
    
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(pinPanel, gbc);
    
        btnVerifyPin = new JButton("Verify");
        btnVerifyPin.setBackground(new Color(92, 64, 51)); 
        btnVerifyPin.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(btnVerifyPin, gbc);
    
        btnVerifyPin.addActionListener((ActionEvent e) -> {
            String enteredPin = new String(pinField.getPassword());
            String storedPin = DB.getAdminPin(); 
    
            if (enteredPin.equals(storedPin)) {
                pinDialog.dispose();
                setVisible(true); 
            } else {
                JOptionPane.showMessageDialog(pinDialog, "Invalid PIN. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
                pinField.setText(""); 
            }
        });
    
        pinDialog.add(panel, BorderLayout.CENTER);
        pinDialog.setVisible(true);
    }
    
    

private static class NumericDocument extends javax.swing.text.PlainDocument {
    private static final int LIMIT = 6; 
    
    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null) {
            return;
        }
        char[] chars = str.toCharArray();
        for (char ch : chars) {
            if (!Character.isDigit(ch)) {
                return;
            }
        }      
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
        super.setExtendedState(JFrame.NORMAL); 
    }
}