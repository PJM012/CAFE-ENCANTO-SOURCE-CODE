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
    private final JMenuItem itmHomePage; 

    public AdminPanel() {
    
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\ADMIN\\Downloads\\FINAL\\Cafe Encanto\\images\\app logo.jpg"));
        setTitle("Café Encanto Product Inventory System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false); 
        setBounds(100, 100, 840, 619);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        menuBar.setBackground(new Color(139, 69, 19));

        Color panelBgColor = new Color(196, 164, 132);
        Color textFgColor = Color.WHITE;
        Color buttonBgColor = Color.WHITE; 
        Color buttonTextColor = new Color(139, 69, 19); 

        mnHome = new JMenu("Home");
        menuBar.add(mnHome);
        StyleUtil.styleMenu(mnHome, new Font("Times New Roman", Font.PLAIN, 14), textFgColor, panelBgColor);

        itmHomePage = new JMenuItem("Home Page");
        itmHomePage.addActionListener(extracted()); 
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

        getContentPane().add(panels.get(0)); 
        StyleUtil.stylePanel((JPanel) getContentPane(), panelBgColor, null);
        
        setMenuItemActive(itmHomePage, true);
    }

    private AdminPanel extracted() {
        return this;
    }

    @Override
    public void setExtendedState(int state) {
        super.setExtendedState(JFrame.NORMAL); 
    }

    public void displayHome() {
        JMenuBar menuBar = getJMenuBar();
        menuBar.add(mnHome, 0); 
        menuBar.add(mnProduct); 
        menuBar.add(mnStock); 
        menuBar.add(mnSearch);
        menuBar.add(mnAccount); 
        menuBar.add(mnExport); 
        
        menuBar.revalidate();
        menuBar.repaint();
        setTitle("Café Encanto Product | Inventory System");
        setVisible(true);
        setMenuItemActive(itmHomePage, true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Selected: " + e.getActionCommand());
        JMenuItem clickedMenuItem = (JMenuItem) e.getSource();
        resetMenuItems();
        setMenuItemActive(clickedMenuItem, true);
    
        String baseTitle = "Café Encanto | ";
    
        if (e.getActionCommand().equals("Home Page")) {
           
            remove(panels.get(cPanel));
            getContentPane().add(panels.get(0)); 
            cPanel = 0;
            setTitle(baseTitle + "Inventory System"); 
            revalidate();
            repaint();
            setVisible(true);
        } else {
            
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
                setTitle(baseTitle + e.getActionCommand()); 
                revalidate();
                repaint();
                setVisible(true);
            }
        }
    }
    
    private void resetMenuItems() {
     
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
            menuItem.setBackground(new Color(139, 69, 19)); 
            menuItem.setForeground(Color.WHITE); 
        } else {
            menuItem.setBackground(Color.WHITE);
            menuItem.setForeground(new Color(139, 69, 19)); 
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
        private String displayText = "Café Encanto Product Inventory System";
        private final Timer animationTimer;
        private int animationIndex = 0;
        private int waveAmplitude = 20; 
        private int waveFrequency = 30; 
        private Color textColor = Color.WHITE;
        private Color shadowColor = new Color(0, 0, 0, 100);

        public HomePanel() {
            try {
                backgroundImage = ImageIO.read(new File("C:\\Users\\ADMIN\\Downloads\\FINAL\\Cafe Encanto\\images\\cafe bg 3.jpg")); 
            } catch (IOException e) {              
            }
            setLayout(new BorderLayout());
            animationTimer = new Timer(50, (ActionEvent e) -> {
                animationIndex++;
                repaint();
            });
            animationTimer.start(); 
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);         
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            Font font = new Font("Times New Roman", Font.BOLD, 42);
            g.setFont(font);
            FontMetrics metrics = g.getFontMetrics(font);
            int textWidth = metrics.stringWidth(displayText);
            int textHeight = metrics.getHeight();
            int x = (getWidth() - textWidth) / 2;
            int y = (getHeight() - textHeight) / 2 + metrics.getAscent();
            g.setColor(shadowColor);
            for (int i = 0; i < displayText.length(); i++) {
                int xPos = x + metrics.stringWidth(displayText.substring(0, i));
                int yPos = y + (int) (waveAmplitude * Math.sin((double) (animationIndex - i) / waveFrequency));
                String letter = Character.toString(displayText.charAt(i));
                g.drawString(letter, xPos + 2, yPos + 2); 
            }

       
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
