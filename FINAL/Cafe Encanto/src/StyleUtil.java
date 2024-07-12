import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class StyleUtil {

    private static final Color ACTIVE_BUTTON_COLOR = Color.YELLOW; // Example active color

    public static void styleLabel(JLabel label, Font font, Color fgColor, int alignment) {
        label.setFont(font);
        label.setForeground(fgColor);
        label.setHorizontalAlignment(alignment);
    }

    public static void styleButton(JButton button, Font font, Color bgColor, Color fgColor, Border border, boolean isOpaque) {
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setBorder(border);
        button.setOpaque(isOpaque);
        button.setFocusPainted(false); // Removes the focus border around the text
    }

    public static void styleMenu(JMenu menu, Font font, Color fgColor, Color bgColor) {
        menu.setFont(font);
        menu.setForeground(fgColor);
        menu.setBackground(bgColor);
    }

    public static void styleMenuItem(JMenuItem menuItem, Font font, Color fgColor, Color bgColor) {
        menuItem.setFont(font);
        menuItem.setForeground(fgColor);
        menuItem.setBackground(bgColor);
        menuItem.setOpaque(true); // Ensure the background color shows
    }

    public static void stylePanel(JPanel panel, Color bgColor, Border border) {
        panel.setBackground(bgColor);
        panel.setBorder(border);
    }

    // Method to set a menu item as active
    public static void setMenuItemActive(JMenuItem menuItem, boolean active) {
        if (active) {
            menuItem.setBackground(ACTIVE_BUTTON_COLOR);
        } else {
            // Reset to the default background color
            menuItem.setBackground(null);
        }
    }
}
