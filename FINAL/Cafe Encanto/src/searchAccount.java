import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class searchAccount extends JPanel {
    private JTextField pinField;
    private JButton btnSearchAccount;
    private JLabel error;
    private String pin;
    private final String err = "Enter account PIN!";
    private Image backgroundImage;

    // Single path format for the background image
    private static final String BACKGROUND_IMAGE_PATH = "C:\\Users\\ADMIN\\Downloads\\FINAL\\Cafe Encanto\\images\\search acc bg.png"; // Example path

    public searchAccount() {
        setLayout(null);
        setBounds(100, 100, 840, 619);

        // Load background image
        loadImageFromPath(BACKGROUND_IMAGE_PATH);

        JLabel lblsearch = new JLabel("SEARCH ACCOUNT INFO");
        lblsearch.setBounds(319, 84, 222, 21);
        lblsearch.setFont(new Font("Tahoma", Font.BOLD, 17));
        add(lblsearch);

        JLabel lblPin = new JLabel("Account PIN:");
        lblPin.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPin.setBounds(283, 156, 124, 21);
        add(lblPin);

        pinField = new JTextField();
        pinField.setBounds(419, 158, 136, 20);
        pinField.setColumns(6); // Set maximum length to 6 characters
        pinField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || pinField.getText().length() >= 6) {
                    e.consume(); // Ignore non-numeric input or limit to 6 characters
                }
            }
        });
        add(pinField);

        btnSearchAccount = new JButton("Search");
        btnSearchAccount.addActionListener((ActionEvent e) -> {
            if (pinField.getText().equals("")) {
                error.setText(err);
            } else {
                error.setText("");
                pin = pinField.getText().trim();
                DB.searchAccountByPIN(pin); // Call method to search by PIN
                pinField.setText("");
            }
        });
        btnSearchAccount.setBounds(419, 219, 136, 23);
        btnSearchAccount.setForeground(Color.WHITE);
        btnSearchAccount.setBackground(new Color(92, 64, 51)); // Button background color
        add(btnSearchAccount);

        error = new JLabel("");
        error.setForeground(Color.RED);
        error.setBounds(379, 277, 217, 14);
        add(error);
    }

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
