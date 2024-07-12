import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class deleteAccount extends JPanel {

    JTextField userField;
    JPasswordField passwordField;
    JTextField pinField;
    JButton btnDeleteAccount;
    private JLabel error;
    String user, pass, err = "All fields are required!";
    private Image backgroundImage; 
    private static final String BACKGROUND_IMAGE_PATH ="C:\\Users\\ADMIN\\Downloads\\FINAL\\Cafe Encanto\\images\\del acc bg.jpg"; 

    @SuppressWarnings("deprecation")
    public deleteAccount() {
        setLayout(null);
        setBounds(100, 100, 840, 619);
        loadImageFromPath(BACKGROUND_IMAGE_PATH); 

        JLabel lblDeleteAccount = new JLabel("DELETE AN ACCOUNT");
        lblDeleteAccount.setBounds(333, 45, 182, 21);
        lblDeleteAccount.setFont(new Font("Tahoma", Font.BOLD, 17));
        add(lblDeleteAccount);

        JLabel lblUserName = new JLabel("Account Username:");
        lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblUserName.setBounds(266, 134, 124, 21);
        add(lblUserName);

        JLabel lblPassword = new JLabel("Account Password:");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPassword.setBounds(266, 166, 124, 21);
        add(lblPassword);

        userField = new JTextField();
        userField.setBounds(409, 136, 136, 20);
        add(userField);
        userField.setColumns(10);

        btnDeleteAccount = new JButton("Delete (-)");
        btnDeleteAccount.addActionListener((ActionEvent e) -> {
            user = userField.getText().trim();
            pass = passwordField.getText().trim().toLowerCase();
            String pin = pinField.getText().trim(); 
        
            if (user.equals("") || pass.equals("") || pin.equals("")) {
                error.setText(err);
            } else {
                error.setText("");
                DB.deleteAccount(user, pass, pin); 
                userField.setText("");
                passwordField.setText("");
                pinField.setText("");
            }
        });
        
        btnDeleteAccount.setBounds(409, 234, 136, 23);
        btnDeleteAccount.setBackground(new Color(92, 64, 51)); 
        btnDeleteAccount.setForeground(Color.WHITE);
        add(btnDeleteAccount);

        passwordField = new JPasswordField();
        passwordField.setBounds(409, 168, 136, 19);
        add(passwordField);

        JLabel lblPin = new JLabel("Account PIN:");
        lblPin.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPin.setBounds(266, 198, 124, 21);
        add(lblPin);

      
        pinField = new JTextField();
        pinField.setBounds(409, 200, 136, 20);
        pinField.setColumns(6); 
        ((AbstractDocument) pinField.getDocument()).setDocumentFilter(new NumericDocumentFilter());  
        add(pinField);
        

        error = new JLabel("");
        error.setForeground(Color.RED);
        error.setBounds(357, 267, 248, 14);
        add(error);
    }

    /**
     * 
     * @param imagePath 
     */
    private void loadImageFromPath(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private class NumericDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) return;
            if (containsOnlyNumbers(string) && (fb.getDocument().getLength() + string.length() <= 6)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text == null) return;
            if (containsOnlyNumbers(text) && (fb.getDocument().getLength() + text.length() - length <= 6)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        private boolean containsOnlyNumbers(String text) {
            return text.chars().allMatch(Character::isDigit);
        }
    }
}
