import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
public class showStock extends JPanel {
    private JTable stockTable;
    private JComboBox<String> comp;
    private NonEditableTableModel model;
    private final JButton btnPrint;
    private final JButton btnRefresh;
    private final JButton btnExportToExcel;
    private final Image backgroundImage;
    private final ArrayList<String[]> stockData; // Store stock data with expiration dates

    public showStock() {
        backgroundImage = new ImageIcon("C:\\Users\\ADMIN\\Downloads\\FINAL\\Cafe Encanto\\images\\show stock bg.jpg").getImage();
        setLayout(null);
        setBounds(100, 100, 840, 619);

        JLabel lblStock = new JLabel("AVAILABLE STOCKS");
        lblStock.setBounds(338, 26, 232, 21);
        lblStock.setFont(new Font("Tahoma", Font.BOLD, 17));
        add(lblStock);

        model = new NonEditableTableModel();
        stockTable = new JTable(model);
        Color lightBrown = new Color(210, 180, 140);
        stockTable.setBackground(lightBrown);
        stockTable.setForeground(Color.BLACK);
        stockTable.setGridColor(Color.BLACK);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setBackground(new Color(235, 221, 195));
        cellRenderer.setForeground(Color.WHITE);
        for (int i = 0; i < stockTable.getColumnCount(); i++) {
            stockTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        JTableHeader header = stockTable.getTableHeader();
        header.setDefaultRenderer(new TableCellRenderer() {
            private final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                comp.setBackground(new Color(139, 69, 19));
                comp.setForeground(Color.WHITE);
                renderer.setHorizontalAlignment(JLabel.CENTER);
                ((JLabel) comp).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
                return comp;
            }
        });

        stockTable.setBounds(98, 112, 645, 397);
        add(stockTable);
        model.addColumn("Product ID");
        model.addColumn("Product Detail");
        model.addColumn("Categories");
        model.addColumn("Quantity");
        model.addColumn("Expiration Date"); // Add new column for expiration date
        JScrollPane scroll = new JScrollPane(stockTable);
        scroll.setBounds(98, 112, 645, 397);
        scroll.getViewport().setBackground(new Color(235, 221, 195));
        add(scroll);

        comp = new JComboBox<>();
        comp.setBackground(new Color(139, 69, 19));
        comp.setForeground(Color.WHITE);
        comp.setBounds(583, 81, 160, 20);
        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        listRenderer.setForeground(Color.WHITE);
        comp.setRenderer(listRenderer);
        add(comp);
        comp.addItem("All");
        comp.addItem("Pastry");
        comp.addItem("Coffee");
        comp.addItem("Fruit Tea");
        comp.addItem("Espresso");
        comp.addItem("Desserts");
        comp.addItem("Snacks");
        comp.addItem("Rice Meal");
        comp.addItemListener((ItemEvent arg0) -> {
            updateTable();
        });

        JLabel lblCategories = new JLabel("Categories:");
        lblCategories.setBounds(582, 68, 161, 14);
        lblCategories.setForeground(Color.BLACK);
        add(lblCategories);
    
        btnExportToExcel = new JButton("Export to Excel");
        btnExportToExcel.addActionListener((ActionEvent arg0) -> {
            toExcel(stockTable, new File("availableStock.xls"));
            JOptionPane.showMessageDialog(null, "Export file created!");
        });
        btnExportToExcel.setBounds(605, 525, 138, 23);
        btnExportToExcel.setBackground(new Color(92, 64, 51)); 
        btnExportToExcel.setForeground(Color.WHITE); 
        add(btnExportToExcel);
    
        btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener((ActionEvent arg0) -> {
            updateTable();
        });
        btnRefresh.setBounds(457, 525, 138, 23);
        btnRefresh.setBackground(new Color(92, 64, 51)); 
        btnRefresh.setForeground(Color.WHITE); 
        add(btnRefresh);
    
        btnPrint = new JButton("Print");
        btnPrint.addActionListener((ActionEvent arg0) -> {
            showPrintPreview();
        });
        btnPrint.setBounds(309, 525, 138, 23);
        btnPrint.setBackground(new Color(92, 64, 51));
        btnPrint.setForeground(Color.WHITE); 
        add(btnPrint);
    
        stockData = new ArrayList<>(); // Initialize stock data
        updateTable();
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public void updateTable() {
        model.setRowCount(0); 
        stockData.clear(); // Clear previous stock data
        ArrayList<String> stock = DB.showStock(comp.getSelectedItem().toString());
        for (int x = 0; x < stock.size(); x += 5) { // Adjust increment to 5 for the new column
            stockData.add(new String[] {
                stock.get(x), // Product ID
                stock.get(x + 1), // Product Detail
                stock.get(x + 2), // Categories
                stock.get(x + 3), // Quantity
                stock.get(x + 4)  // Expiration Date
            });
        }
    
        // Update table model with stored data
        for (String[] row : stockData) {
            model.addRow(row);
        }
    }
    
    

    public void toExcel(JTable table, File file) {
        try {
            TableModel model2 = table.getModel();
            try (FileWriter excel = new FileWriter(file)) {
                // Write column names
                for (int i = 0; i < model2.getColumnCount(); i++) {
                    excel.write(model2.getColumnName(i) + "\t");
                }
                excel.write("\n");
    
                // Write data rows
                for (int i = 0; i < model2.getRowCount(); i++) {
                    for (int j = 0; j < model2.getColumnCount(); j++) {
                        excel.write(model2.getValueAt(i, j).toString() + "\t");
                    }
                    excel.write("\n");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    

    public void showPrintPreview() {
        btnPrint.setVisible(false);
        btnRefresh.setVisible(false);
        btnExportToExcel.setVisible(false);

        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        this.printAll(g2d);
        g2d.dispose();

        btnPrint.setVisible(true);
        btnRefresh.setVisible(true);
        btnExportToExcel.setVisible(true);

        JDialog previewDialog = new JDialog();
        previewDialog.setTitle("Print Preview");
        previewDialog.setSize(800, 600);
        previewDialog.setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, this);
            }
        };
        JScrollPane scrollPane = new JScrollPane(panel);
        previewDialog.add(scrollPane);

        JButton btnPrintNow = new JButton("Print");
        btnPrintNow.setBackground(new Color(92, 64, 51));
        btnPrintNow.setForeground(Color.WHITE);
        btnPrintNow.addActionListener((ActionEvent e) -> {
            printPanel(image);
            previewDialog.dispose();
        });
        previewDialog.add(btnPrintNow, "South");

        previewDialog.setVisible(true);
    }

    public void printPanel(BufferedImage image) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable((Graphics graphics, PageFormat pageFormat, int pageIndex) -> {
            if (pageIndex > 0) {
                return Printable.NO_SUCH_PAGE;
            }
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
            return Printable.PAGE_EXISTS;
        });
        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
            } catch (PrinterException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
}
