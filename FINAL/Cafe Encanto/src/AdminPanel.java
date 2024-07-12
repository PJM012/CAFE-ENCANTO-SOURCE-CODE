import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public final class AdminPanel extends JFrame implements ActionListener {
    JMenuItem itmAddProduct;
    JMenu mnProduct;
    JMenuItem itmUpdateProduct;
    JMenuItem itmDeleteProduct;
    JMenu mnAccount;
    JMenuItem itmDeleteAccount;
    JMenuItem itmAddAccount;
    JMenu mnStock;
    JMenuItem itmShowStock;
    JMenu mnExport;
    ArrayList<JPanel> panels = new ArrayList<>();
    int cPanel = 0;
    private final JMenu mnSearch;
    private final JMenuItem mntmSearchProduct;
    private final JMenuItem mntmSearchAccount;
    private final JMenu mnHome;
    private final JMenuItem itmHomePage; // Added as a class field

    public AdminPanel() {
        // Initialize JFrame settings
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\ADMIN\\Downloads\\FINAL\\Cafe Encanto\\images\\app logo.jpg"));
        setTitle("CAFE ENCANTO INVENTORY SYSTEM");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false); 
        setBounds(100, 100, 840, 619);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        menuBar.setBackground(new Color(139, 69, 19));

        Color panelBgColor = new Color(196, 164, 132);
        Color textFgColor = Color.WHITE; // Black text for labels and menu items
        Color buttonBgColor = Color.WHITE; // background for buttons
        Color buttonTextColor = new Color(139, 69, 19); // color of text for buttons

        mnHome = new JMenu("Home");
        menuBar.add(mnHome);
        StyleUtil.styleMenu(mnHome, new Font("Times New Roman", Font.PLAIN, 14), textFgColor, panelBgColor);

        itmHomePage = new JMenuItem("Home Page");
        itmHomePage.addActionListener(extracted()); // ActionListener is implemented in AdminPanel
        mnHome.add(itmHomePage);

        mnProduct = new JMenu("Product");
        itmAddProduct = new JMenuItem("Add Product");
        itmAddProduct.addActionListener(extracted());
        itmUpdateProduct = new JMenuItem("Update Product");
        itmUpdateProduct.addActionListener(extracted());
        itmDeleteProduct = new JMenuItem("Delete Product");
        itmDeleteProduct.addActionListener(extracted());
        mnProduct.add(itmAddProduct);
        mnProduct.add(itmUpdateProduct);
        mnProduct.add(itmDeleteProduct);
        menuBar.add(mnProduct);
        StyleUtil.styleMenuItem(itmHomePage, new Font("Times New Roman", Font.PLAIN, 14), buttonTextColor, buttonBgColor);
        StyleUtil.styleMenu(mnProduct, new Font("Times New Roman", Font.PLAIN, 14), textFgColor, panelBgColor);
        StyleUtil.styleMenuItem(itmAddProduct, new Font("Times New Roman", Font.PLAIN, 14), buttonTextColor, buttonBgColor);
        StyleUtil.styleMenuItem(itmUpdateProduct, new Font("Times New Roman", Font.PLAIN, 14), buttonTextColor, buttonBgColor);
        StyleUtil.styleMenuItem(itmDeleteProduct, new Font("Times New Roman", Font.PLAIN, 14), buttonTextColor, buttonBgColor);

        mnStock = new JMenu("Stock");
        itmShowStock = new JMenuItem("Show Stock");
        itmShowStock.addActionListener(extracted());
        mnStock.add(itmShowStock);
        menuBar.add(mnStock);
        StyleUtil.styleMenu(mnStock, new Font("Times New Roman", Font.PLAIN, 14), textFgColor, panelBgColor);
        StyleUtil.styleMenuItem(itmShowStock, new Font("Times New Roman", Font.PLAIN, 14), buttonTextColor, buttonBgColor);

        mnSearch = new JMenu("Search");
        mntmSearchProduct = new JMenuItem("Search Product");
        mntmSearchProduct.addActionListener(extracted());
        mnSearch.add(mntmSearchProduct);
        mntmSearchAccount = new JMenuItem("Search Account");
        mntmSearchAccount.addActionListener(extracted());
        mnSearch.add(mntmSearchAccount);
        menuBar.add(mnSearch);
        StyleUtil.styleMenu(mnSearch, new Font("Times New Roman", Font.PLAIN, 14), textFgColor, panelBgColor);
        StyleUtil.styleMenuItem(mntmSearchProduct, new Font("Times New Roman", Font.PLAIN, 14), buttonTextColor, buttonBgColor);
        StyleUtil.styleMenuItem(mntmSearchAccount, new Font("Times New Roman", Font.PLAIN, 14), buttonTextColor, buttonBgColor);

        mnAccount = new JMenu("Options");
        menuBar.add(mnAccount);
        itmAddAccount = new JMenuItem("Add Account");
        mnAccount.add(itmAddAccount);
        itmAddAccount.addActionListener(extracted());
        itmDeleteAccount = new JMenuItem("Delete Account");
        mnAccount.add(itmDeleteAccount);
        itmDeleteAccount.addActionListener(extracted());
        StyleUtil.styleMenu(mnAccount, new Font("Times New Roman", Font.PLAIN, 14), textFgColor, panelBgColor);
        StyleUtil.styleMenuItem(itmAddAccount, new Font("Times New Roman", Font.PLAIN, 14), buttonTextColor, buttonBgColor);
        StyleUtil.styleMenuItem(itmDeleteAccount, new Font("Times New Roman", Font.PLAIN, 14), buttonTextColor, buttonBgColor);

        mnExport = new JMenu("Account");
        JMenuItem logout = new JMenuItem("Logout");
        logout.addActionListener(extracted());
        mnExport.add(logout);
        menuBar.add(mnExport);
        StyleUtil.styleMenu(mnExport, new Font("Times New Roman", Font.PLAIN, 14), textFgColor, panelBgColor);
        StyleUtil.styleMenuItem(logout, new Font("Times New Roman", Font.PLAIN, 14), buttonTextColor, buttonBgColor);

        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel homePanel = new HomePanel();
        panels.add(homePanel); // Index 0
        panels.add(new addProduct()); // Index 1
        panels.add(new updateProduct()); // Index 2
        panels.add(new deleteProduct()); // Index 3
        panels.add(new addAccount()); // Index 4
        panels.add(new deleteAccount()); // Index 5
        panels.add(new showStock()); // Index 6
        panels.add(new searchProduct()); // Index 7
        panels.add(new searchAccount()); // Index 8

        getContentPane().add(panels.get(0)); // Default to home panel
        StyleUtil.stylePanel((JPanel) getContentPane(), panelBgColor, null);
        
        // Set the Home Page button active initially
        setMenuItemActive(itmHomePage, true);
    }

    private AdminPanel extracted() {
        return this;
    }

    @Override
    public void setExtendedState(int state) {
        super.setExtendedState(JFrame.NORMAL); // Always keep in normal state
    }

    public void displayHome() {
        JMenuBar menuBar = getJMenuBar();
        menuBar.add(mnHome, 0); // Add the Home menu at the beginning
        menuBar.add(mnProduct); // Add the Product menu
        menuBar.add(mnStock); // Add the Stock menu
        menuBar.add(mnSearch);
        menuBar.add(mnAccount); // Add the Account menu
        menuBar.add(mnExport); // Add the Export menu
        
        menuBar.revalidate();
        menuBar.repaint();
        setTitle("Cafe Encanto | Inventory System");
        setVisible(true);
        setMenuItemActive(itmHomePage, true); // Ensure the Home Page button is active
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Selected: " + e.getActionCommand());
        JMenuItem clickedMenuItem = (JMenuItem) e.getSource();
    
        // Reset all menu items to inactive state
        resetMenuItems();
    
        // Set active state for clicked menu item
        setMenuItemActive(clickedMenuItem, true);
    
        // Base title
        String baseTitle = "Cafe Encanto | ";
    
        if (e.getActionCommand().equals("Home Page")) {
            // Display the initial home panel
            remove(panels.get(cPanel));
            getContentPane().add(panels.get(0)); // Display initial home panel
            cPanel = 0;
            setTitle(baseTitle + "Inventory System"); // Update title with specific page
            revalidate();
            repaint();
            setVisible(true);
        } else {
            // Determine panel index based on action command for other menu items
            int panelIndex = switch (e.getActionCommand()) {
                case "Home" -> 0;
                case "Add Product" -> 1;
                case "Update Product" -> 2;
                case "Delete Product" -> 3;
                case "Options", "Add Account" -> 4;
                case "Delete Account" -> 5;
                case "Show Stock" -> 6;
                case "Search Product" -> 7;
                case "Search Account" -> 8;
                case "Logout" -> {
                    dispose();
                    Login loginFrame = new Login();
                    loginFrame.setAdminPanel(new AdminPanel());
                    loginFrame.setVisible(true);
                    yield -1;
                }
                default -> -1;
            };
    
            if (panelIndex != -1) {
                remove(panels.get(cPanel));
                getContentPane().add(panels.get(panelIndex));
                cPanel = panelIndex;
                setTitle(baseTitle + e.getActionCommand()); // Update title with specific page
                revalidate();
                repaint();
                setVisible(true);
            }
        }
    }
    
    private void resetMenuItems() {
        // Reset all menu items to inactive state
        for (Component menuComponent : mnHome.getMenuComponents()) {
            if (menuComponent instanceof JMenuItem jMenuItem) {
                setMenuItemActive(jMenuItem, false);
            }
        }
        for (Component menuComponent : mnProduct.getMenuComponents()) {
            if (menuComponent instanceof JMenuItem jMenuItem) {
                setMenuItemActive(jMenuItem, false);
            }
        }
        for (Component menuComponent : mnStock.getMenuComponents()) {
            if (menuComponent instanceof JMenuItem jMenuItem) {
                setMenuItemActive(jMenuItem, false);
            }
        }
        for (Component menuComponent : mnSearch.getMenuComponents()) {
            if (menuComponent instanceof JMenuItem jMenuItem) {
                setMenuItemActive(jMenuItem, false);
            }
        }
        for (Component menuComponent : mnAccount.getMenuComponents()) {
            if (menuComponent instanceof JMenuItem jMenuItem) {
                setMenuItemActive(jMenuItem, false);
            }
        }
        for (Component menuComponent : mnExport.getMenuComponents()) {
            if (menuComponent instanceof JMenuItem jMenuItem) {
                setMenuItemActive(jMenuItem, false);
            }
        }
    }

    public void setMenuItemActive(JMenuItem menuItem, boolean active) {
        if (active) {
            menuItem.setBackground(new Color(139, 69, 19)); // Set active background color
            menuItem.setForeground(Color.WHITE); // Set active text color
        } else {
            menuItem.setBackground(Color.WHITE);
            menuItem.setForeground(new Color(139, 69, 19)); // Reset to default text color
        }
    }

    public static void main(String[] args) {
        Login loginFrame = new Login();
        loginFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\ADMIN\\Downloads\\FINAL\\Cafe Encanto\\images\\app logo.jpg"));
        loginFrame.setAdminPanel(new AdminPanel());
        loginFrame.setVisible(true);
    }

    class HomePanel extends JPanel {
        private Image backgroundImage;
        private String displayText = "Cafe Encanto Inventory System";
        private final Timer animationTimer;
        private int animationIndex = 0;
        private int waveAmplitude = 20; // Amplitude of the waveS
        private int waveFrequency = 30; // Frequency of the wave
        private Color textColor = Color.WHITE; // Text color
        private Color shadowColor = new Color(0, 0, 0, 100); // Shadow color (semi-transparent black)

        public HomePanel() {
            try {
                // Load the background image from a file
                backgroundImage = ImageIO.read(new File("C:\\Users\\ADMIN\\Downloads\\FINAL\\Cafe Encanto\\images\\cafe bg 3.jpg")); // Replace with your image path
            } catch (IOException e) {
                
            }

            setLayout(new BorderLayout());

            // Initialize timer for animation
            animationTimer = new Timer(50, (ActionEvent e) -> {
                animationIndex++;
                repaint();
            });
            animationTimer.start(); // Start the animation timer
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw background image
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

            // Draw animated text with shadow
            Font font = new Font("Times New Roman", Font.BOLD, 42);
            g.setFont(font);

            // Get text dimensions
            FontMetrics metrics = g.getFontMetrics(font);
            int textWidth = metrics.stringWidth(displayText);
            int textHeight = metrics.getHeight();

            // Calculate starting position to center the text
            int x = (getWidth() - textWidth) / 2;
            int y = (getHeight() - textHeight) / 2 + metrics.getAscent();

            // Draw shadow first
            g.setColor(shadowColor);
            for (int i = 0; i < displayText.length(); i++) {
                int xPos = x + metrics.stringWidth(displayText.substring(0, i));
                int yPos = y + (int) (waveAmplitude * Math.sin((double) (animationIndex - i) / waveFrequency));
                String letter = Character.toString(displayText.charAt(i));
                g.drawString(letter, xPos + 2, yPos + 2); // Shifted by 2 pixels for shadow effect
            }

            // Draw main text
            g.setColor(textColor);
            for (int i = 0; i < displayText.length(); i++) {
                int xPos = x + metrics.stringWidth(displayText.substring(0, i));
                int yPos = y + (int) (waveAmplitude * Math.sin((double) (animationIndex - i) / waveFrequency));
                String letter = Character.toString(displayText.charAt(i));
                g.drawString(letter, xPos, yPos);
            }
        }
    }
}
