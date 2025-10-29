package app.GUI;

import app.Components.CustomTableCellRenderer;
import app.Components.CustomTableHeaderRenderer;
import app.Components.ImagePanelButton;
import app.DAO.DAO_MenuItem;
import app.InitFont.CustomFont;
import app.Listener.ActionListener_SellPage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import app.Object.MenuItem;
import app.Object.Table;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.swing.FontIcon;

public class SellPage extends JPanel {
    public CustomFont customFont = new CustomFont();
//    private Collection_MenuItem menu = new Collection_MenuItem();
//    private Collection_BillDetails bdl = new Collection_BillDetails();
    private ActionListener_SellPage action;
    public JRadioButton takeAwayRadioButton;
    public JButton seatingButton;
    private DefaultTableModel productTableModel;
    private JTable productTable;
    public List<Table> choosenTableList;
    public int currentOffset = 0;
//    private final int PAGE_SIZE = 18;
    public boolean isLoading = false;
    public JButton loadProductButton;
    public JPanel productPanel;
    public GridBagConstraints gbc;
    public JDialog loadingDialog;
    public JScrollBar sb;

    public SellPage() {
        setPreferredSize(new Dimension(1100, 500));
        setLayout(new BorderLayout());
        setBackground(Color.white);

        action = new ActionListener_SellPage(this);
        choosenTableList = new ArrayList<Table>();

        JPanel emptyL = new JPanel();
        emptyL.setPreferredSize(new Dimension(16, 500));
        emptyL.setOpaque(false);
        add(emptyL, BorderLayout.WEST);

        JPanel emptyR = new JPanel();
        emptyR.setPreferredSize(new Dimension(16, 500));
        emptyR.setOpaque(false);
        add(emptyR, BorderLayout.EAST);

        add(createSearchPane(), BorderLayout.NORTH);
        add(createProductPanel(), BorderLayout.CENTER);
    }

    public JPanel createSearchPane() {
        JPanel north = new JPanel();
        north.setPreferredSize(new Dimension(800, 55));
        north.setOpaque(false);
        north.setVisible(true);
        north.setLayout(new BorderLayout());

        JPanel northN = new JPanel();
        northN.setPreferredSize(new Dimension(800, 25));
        northN.setOpaque(false);
        northN.setVisible(true);
        northN.setLayout(new FlowLayout(FlowLayout.LEFT));
        north.add(northN, BorderLayout.CENTER);

        JPanel emptyN = new JPanel();
        emptyN.setPreferredSize(new Dimension(800, 20));
        emptyN.setOpaque(false);
        north.add(emptyN, BorderLayout.NORTH);

        JPanel emptyL = new JPanel();
        emptyL.setPreferredSize(new Dimension(12, 25));
        emptyL.setOpaque(false);
        north.add(emptyL, BorderLayout.WEST);

        JLabel searchLabel = new JLabel("Find Products:");
        searchLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        searchLabel.setForeground(Color.BLACK);
        searchLabel.setPreferredSize(new Dimension(105, 25)); // Thay đổi kích thước cho phù hợp
        northN.add(searchLabel);

        JTextField searchBar = new JTextField();
        searchBar.setForeground(Color.BLACK);
        searchBar.setBackground(new Color(241, 211, 178));
        searchBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        searchBar.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        searchBar.setPreferredSize(new Dimension(180, 25)); // Thay đổi kích thước cho phù hợp và vị trí
        northN.add(searchBar);

        JButton findProduct = new JButton("Search");
        findProduct.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        findProduct.setForeground(Color.BLACK);
        findProduct.setBackground(new Color(241, 211, 178));
        findProduct.setPreferredSize(new Dimension(80, 25));
        northN.add(findProduct);

        JLabel chooseLabel = new JLabel("Category:");
        chooseLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        chooseLabel.setForeground(Color.BLACK);
        chooseLabel.setPreferredSize(new Dimension(65, 25));
        northN.add(chooseLabel);

        JComboBox<String> productCategory = new JComboBox<>();
        productCategory.setForeground(Color.BLACK);
        productCategory.setBackground(new Color(241, 211, 178));
        productCategory.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        productCategory.addItem("All");
        productCategory.addItem("Coffee");
        productCategory.addItem("Soda");
        productCategory.addItem("Ice Cream");
        productCategory.setPreferredSize(new Dimension(90, 25));
        northN.add(productCategory);

        loadProductButton = new JButton("Load Product");
        loadProductButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        loadProductButton.setForeground(Color.BLACK);
        loadProductButton.setBackground(new Color(241, 211, 178));
        loadProductButton.setPreferredSize(new Dimension(120, 25));
        loadProductButton.addActionListener(action);
        northN.add(loadProductButton);

        return north;
    }

    public JPanel createProductPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(800, 400));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel ptPanel = new JPanel();
        ptPanel.setOpaque(false);
        ptPanel.setPreferredSize(new Dimension(800, 400));
        ptPanel.setLayout(new BoxLayout(ptPanel, BoxLayout.X_AXIS));

        productPanel = new JPanel(new GridBagLayout());
        productPanel.setBackground(Color.white);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // khoảng cách giữa các nút
        gbc.anchor = GridBagConstraints.CENTER;

        List<MenuItem> menu = DAO_MenuItem.get18MenuItems(0, 18);
        int columns = 3;
        for (int i = 0; i < 18; i++) {
            ImagePanelButton productButton = new ImagePanelButton(menu.get(i).getName(), "", menu.get(i).getPrice(),
                    "asset/placeholder.png", 200,
                    200,
                    0.8);
            productButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
            productButton.setPreferredSize(new Dimension(250, 250)); // không bị co giãn
            productButton.setMaximumSize(new Dimension(250, 250));

            gbc.gridx = i % columns;
            gbc.gridy = i / columns;
            productPanel.add(productButton, gbc);
        }
        currentOffset = 18;

        JScrollPane scrollPane = new JScrollPane(productPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(1000, 400));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setWheelScrollingEnabled(true);
        sb = scrollPane.getVerticalScrollBar();
//        scrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
//            JScrollBar sb = (JScrollBar) e.getSource();
//
//            int extent = sb.getModel().getExtent();
//            int maximum = sb.getMaximum();
//            int value = sb.getValue();
//
//            if (!isLoading && value + extent >= maximum - 100) {
//                loadMoreMenuItems(gbc);
//            }
//        });

        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(400, 400));
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBackground(Color.white);

        JPanel labelPanel = new JPanel();
        labelPanel.setPreferredSize(new Dimension(400, 40));
        labelPanel.setBackground(Color.white);
        labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        right.add(labelPanel);
        right.add(Box.createVerticalStrut(10));

        FontIcon cartIcon = FontIcon.of(Feather.SHOPPING_CART, 24, Color.BLACK);
        JLabel chosenItemLabel = new JLabel(
                "<html><div style='text-align: left; font-size: 15px;'><b>Your Item:</b></div></html>",
                (Icon) cartIcon,
                JLabel.LEFT);
        chosenItemLabel.setIconTextGap(8);
        chosenItemLabel.setForeground(Color.BLACK);
        chosenItemLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        labelPanel.add(chosenItemLabel);

        productTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make "Points" column (index 0,1,2) uneditable
                return column != 0 && column != 1 && column != 3;
            }
        };
        productTableModel.addColumn("Name");
        productTableModel.addColumn("Serve Type");
        productTableModel.addColumn("Amount");
        productTableModel.addColumn("Price");

        Object[] row = { "Cappuccino", "Hot", 1, 4.50 };
        productTableModel.addRow(row);

        Object[] row1 = { "Americano", "Hot", 1, 4.50 };
        productTableModel.addRow(row1);

        productTable = new JTable(productTableModel);
        productTable.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        productTable.setForeground(Color.BLACK);
        productTable.setBackground(Color.white);

        TableColumnModel modelTable = productTable.getColumnModel();
        modelTable.getColumn(0).setPreferredWidth(80);
        modelTable.getColumn(1).setPreferredWidth(40);
        modelTable.getColumn(2).setPreferredWidth(20);
        modelTable.getColumn(3).setPreferredWidth(80);

        JComboBox<String> servedComboBox = new JComboBox<>();
        servedComboBox.setForeground(Color.black);
        servedComboBox.setBackground(Color.white);
        servedComboBox.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        servedComboBox.addItem("Hot");
        servedComboBox.addItem("Cold");

        TableCellEditor comboBoxEditor = new DefaultCellEditor(servedComboBox);

        // Set the cell editor for the "Served" column (index 2)
        TableColumn servedColumn = productTable.getColumnModel().getColumn(1);
        servedColumn.setCellEditor(comboBoxEditor);

        JTableHeader tableHeader = productTable.getTableHeader();
        tableHeader.setForeground(Color.black);
        tableHeader.setBackground(Color.white);
        tableHeader.setReorderingAllowed(false);
        tableHeader.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));

        tableHeader.setDefaultRenderer(new CustomTableHeaderRenderer());
        for (int i = 0; i < productTable.getColumnCount(); i++) {
            productTable.getColumnModel().getColumn(i).setCellRenderer(new CustomTableCellRenderer());
        }

        JScrollPane tableScrollPane = new JScrollPane(productTable);
        tableScrollPane.setPreferredSize(new Dimension(400, 600));
        tableScrollPane.getViewport().setBackground(new Color(255, 213, 146));
        tableScrollPane.setBackground(Color.white);
        tableScrollPane.setForeground(Color.black);
        right.add(tableScrollPane);
        right.add(Box.createHorizontalStrut(10));

        JPanel editPanel = new JPanel();
        editPanel.setPreferredSize(new Dimension(400, 120));
        editPanel.setBackground(Color.white);
        editPanel.setForeground(Color.black);
        editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));
        right.add(editPanel);
        right.add(Box.createHorizontalStrut(10));

        JPanel promoPanel = new JPanel();
        promoPanel.setPreferredSize(new Dimension(400, 25));
        promoPanel.setBackground(Color.white);
        promoPanel.setForeground(Color.black);
        promoPanel.setLayout(new BoxLayout(promoPanel, BoxLayout.X_AXIS));
        promoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        editPanel.add(promoPanel);

        FontIcon percentIcon = FontIcon.of(Feather.PERCENT, 24, Color.BLACK);
        JLabel promotionLabel = new JLabel(
                "<html><div style='text-align: left; font-size: 13px;'><b>Promotion Code:</b></div></html>",
                (Icon) percentIcon,
                JLabel.LEFT);
        promotionLabel.setIconTextGap(8);
        promotionLabel.setPreferredSize(new Dimension(160, 25));
        promotionLabel.setForeground(Color.BLACK);
        promoPanel.add(promotionLabel);

        JTextField promoBar = new JTextField();
        promoBar.setForeground(Color.BLACK);
        promoBar.setBackground(new Color(241, 211, 178));
        promoBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        promoBar.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        promoBar.setPreferredSize(new Dimension(100, 25));
        promoPanel.add(promoBar);
        promoPanel.add(Box.createHorizontalStrut(10));

        JButton applyPromoButton = new JButton("Apply");
        applyPromoButton.setBackground(Color.white);
        applyPromoButton.setForeground(Color.black);
        applyPromoButton.setPreferredSize(new Dimension(90, 25));
        applyPromoButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        promoPanel.add(applyPromoButton);

        JPanel takeAwayPanel = new JPanel();
        takeAwayPanel.setPreferredSize(new Dimension(400, 25));
        takeAwayPanel.setBackground(Color.white);
        takeAwayPanel.setForeground(Color.black);
        takeAwayPanel.setLayout(new BoxLayout(takeAwayPanel, BoxLayout.X_AXIS));
        editPanel.add(takeAwayPanel);
        editPanel.add(Box.createVerticalStrut(10));

        FontIcon takeAwayIcon = FontIcon.of(Feather.SHOPPING_BAG, 24, Color.BLACK);
        JLabel takeAwayLabel = new JLabel(
                "<html><div style='text-align: left; font-size: 13px;'><b>Take Away:</b></div></html>",
                (Icon) takeAwayIcon,
                JLabel.LEFT
        );
        takeAwayLabel.setBackground(Color.WHITE);
        takeAwayLabel.setForeground(Color.BLACK);
        takeAwayLabel.setIconTextGap(8);
        takeAwayLabel.setPreferredSize(new Dimension(160, 25));
        takeAwayPanel.add(takeAwayLabel);

        takeAwayRadioButton = new JRadioButton("Yes");
        takeAwayRadioButton.setPreferredSize(new Dimension(100, 25));
        takeAwayRadioButton.setForeground(Color.BLACK);
        takeAwayRadioButton.setBackground(Color.white);
        takeAwayRadioButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        takeAwayRadioButton.addActionListener(action);
        takeAwayPanel.add(takeAwayRadioButton);
        takeAwayPanel.add(Box.createHorizontalStrut(10));

        seatingButton = new JButton("Seating");
        seatingButton.setBackground(Color.white);
        seatingButton.setForeground(Color.black);
        seatingButton.setPreferredSize(new Dimension(90, 25));
        seatingButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        seatingButton.addActionListener(action);
        if (!takeAwayRadioButton.isSelected()) seatingButton.setEnabled(true);
        takeAwayPanel.add(seatingButton);

        JPanel funcPanel = new JPanel();
        funcPanel.setPreferredSize(new Dimension(400, 25));
        funcPanel.setBackground(Color.white);
        funcPanel.setForeground(Color.black);
        funcPanel.setLayout(new BoxLayout(funcPanel, BoxLayout.X_AXIS));
        editPanel.add(funcPanel);
        editPanel.add(Box.createVerticalStrut(10));

        FontIcon editIcon = FontIcon.of(Feather.EDIT, 24, Color.BLACK);
        JButton updateButton = new JButton("Update", editIcon);
        updateButton.setBackground(Color.white);
        updateButton.setForeground(Color.black);
        updateButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        funcPanel.add(updateButton);
        funcPanel.add(Box.createHorizontalStrut(10));

        FontIcon trashIcon = FontIcon.of(Feather.TRASH, 24, Color.BLACK);
        JButton deleteButton = new JButton("Delete", trashIcon);
        deleteButton.setBackground(Color.white);
        deleteButton.setForeground(Color.black);
        deleteButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        funcPanel.add(deleteButton);
        funcPanel.add(Box.createHorizontalStrut(10));

        FontIcon invoiceIcon = FontIcon.of(Feather.FILE_TEXT, 24, Color.BLACK);
        JButton toInvoiceButton = new JButton("Invoice", invoiceIcon);
        toInvoiceButton.setBackground(Color.white);
        toInvoiceButton.setForeground(Color.black);
        toInvoiceButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        funcPanel.add(toInvoiceButton);

        ptPanel.add(scrollPane);
        ptPanel.add(Box.createHorizontalStrut(10));
        ptPanel.add(right);
        panel.add(ptPanel);
        panel.add(Box.createVerticalStrut(30));
        return panel;
    }

    public void showLoadingDialog(JFrame parentFrame) {
        loadingDialog = new JDialog(parentFrame, "Loading...", false);
        loadingDialog.setUndecorated(true);
        loadingDialog.setAlwaysOnTop(true);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(200, 200));

        JLabel gifLabel = new JLabel(new ImageIcon("asset/loading.gif"));
        gifLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(gifLabel);
        panel.add(Box.createVerticalStrut(5));

        loadingDialog.getContentPane().add(panel);
        loadingDialog.pack();
        loadingDialog.setLocationRelativeTo(parentFrame);
        loadingDialog.setVisible(true);
    }

    public void showLoadingSuccessfullyOptionPane() {
        JOptionPane.showMessageDialog(this, "Load 18 products successfully!", "Loading Successfully", JOptionPane.INFORMATION_MESSAGE);
    }
}