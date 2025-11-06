package app.GUI;

import app.Collections.Collection_BillDetails;
import app.Collections.Collection_MenuItem;
import app.Components.CustomTableCellRenderer;
import app.Components.CustomTableHeaderRenderer;
import app.Components.CustomUpdateCellEditor;
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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import app.Object.MenuItem;
import app.Object.Table;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.swing.FontIcon;

public class SellPage extends JPanel {
    public CustomFont customFont = new CustomFont();
    public Collection_BillDetails collectionBillDetails = new Collection_BillDetails();
//    private Collection_BillDetails bdl = new Collection_BillDetails();
    private ActionListener_SellPage action;
    public JTextField searchBar;
    public JButton updateButton;
    public JButton loadProductButton;
    public JButton clearSearchButton;
    public JComboBox<String> productCategory;
    public int currentOffset = 0;
    public int previousOffset = 0;
    public List<ImagePanelButton> allProductButtons;
    public boolean isLoading = false;
    public GridBagConstraints gbc;
    public JPanel productPanel;
    public static DefaultTableModel productTableModel;
    public static JTable productTable;
    public JRadioButton takeAwayRadioButton;
    public JButton seatingButton;
    public List<Table> choosenTableList;
//    private final int PAGE_SIZE = 18;
    public JButton deleteButton;
    public JButton toInvoiceButton;
    public JDialog loadingDialog;
    public JButton findProduct;
    public JScrollBar sb;

    public SellPage() {
        setPreferredSize(new Dimension(1100, 500));
        setLayout(new BorderLayout());
        setBackground(Color.white);

        action = new ActionListener_SellPage(this);
        choosenTableList = new ArrayList<Table>();
        allProductButtons = new ArrayList<>();

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

        JLabel searchLabel = new JLabel("Tìm kiếm sản phẩm:");
        searchLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        searchLabel.setForeground(Color.BLACK);
        searchLabel.setPreferredSize(new Dimension(130, 25)); // Thay đổi kích thước cho phù hợp
        northN.add(searchLabel);

        searchBar = new JTextField();
        searchBar.setForeground(Color.BLACK);
        searchBar.setBackground(new Color(241, 211, 178));
        searchBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        searchBar.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        searchBar.setPreferredSize(new Dimension(175, 25)); // Thay đổi kích thước cho phù hợp và vị trí
        northN.add(searchBar);

        findProduct = new JButton("Tìm Kiếm");
        findProduct.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        findProduct.setForeground(Color.BLACK);
        findProduct.setBackground(new Color(241, 211, 178));
        findProduct.setPreferredSize(new Dimension(100, 25));
        findProduct.addActionListener(action);
        northN.add(findProduct);

        JLabel chooseLabel = new JLabel("Loại:");
        chooseLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        chooseLabel.setForeground(Color.BLACK);
        chooseLabel.setPreferredSize(new Dimension(65, 25));
        northN.add(chooseLabel);

        productCategory = new JComboBox<>();
        productCategory.setForeground(Color.BLACK);
        productCategory.setBackground(new Color(241, 211, 178));
        productCategory.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        productCategory.addItem("Tất cả");
        productCategory.addItem("Cà Phê");
        productCategory.addItem("Trà");
        productCategory.addItem("Bánh ngọt");
        productCategory.addItem("Khác");
        productCategory.setPreferredSize(new Dimension(90, 25));
        productCategory.addActionListener(action);
        northN.add(productCategory);

        loadProductButton = new JButton("Tải thêm");
        loadProductButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        loadProductButton.setForeground(Color.BLACK);
        loadProductButton.setBackground(new Color(241, 211, 178));
        loadProductButton.setPreferredSize(new Dimension(120, 25));
        loadProductButton.addActionListener(action);
        northN.add(loadProductButton);

        clearSearchButton = new JButton("Tải lại danh sách cũ");
        clearSearchButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        clearSearchButton.setForeground(Color.BLACK);
        clearSearchButton.setBackground(new Color(241, 211, 178));
        clearSearchButton.setPreferredSize(new Dimension(190, 25));
        clearSearchButton.addActionListener(action);
        northN.add(clearSearchButton);

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

        loadFirst18MenuItem(gbc);

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
                "<html><div style='text-align: left; font-size: 15px;'><b>Đơn của bạn:</b></div></html>",
                (Icon) cartIcon,
                JLabel.LEFT);
        chosenItemLabel.setIconTextGap(8);
        chosenItemLabel.setForeground(Color.BLACK);
        chosenItemLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        labelPanel.add(chosenItemLabel);

        productTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make "Points" column (index 0,2) uneditable
                return column != 0 && column != 2;
            }
        };
        productTableModel.addColumn("Tên");
        productTableModel.addColumn("Số lượng");
        productTableModel.addColumn("Giá");

        productTable = new JTable(productTableModel);
        productTable.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        productTable.setForeground(Color.BLACK);
        productTable.setBackground(Color.white);
        productTable.setRowHeight(30);

        TableColumnModel modelTable = productTable.getColumnModel();
        modelTable.getColumn(0).setPreferredWidth(80);
        modelTable.getColumn(1).setPreferredWidth(80);
        modelTable.getColumn(2).setPreferredWidth(80);

        JTableHeader tableHeader = productTable.getTableHeader();
        tableHeader.setForeground(Color.black);
        tableHeader.setBackground(Color.white);
        tableHeader.setReorderingAllowed(false);
        tableHeader.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));

        tableHeader.setDefaultRenderer(new CustomTableHeaderRenderer());
        for (int i = 0; i < productTable.getColumnCount(); i++) {
            productTable.getColumnModel().getColumn(i).setCellRenderer(new CustomTableCellRenderer());
            if (i == 1)
                productTable.getColumnModel().getColumn(i).setCellEditor(new CustomUpdateCellEditor(collectionBillDetails));
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
                "<html><div style='text-align: left; font-size: 13px;'><b>Mã giảm giá:</b></div></html>",
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

        JButton applyPromoButton = new JButton("Áp dụng");
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
                "<html><div style='text-align: left; font-size: 13px;'><b>Mang đi:</b></div></html>",
                (Icon) takeAwayIcon,
                JLabel.LEFT
        );
        takeAwayLabel.setBackground(Color.WHITE);
        takeAwayLabel.setForeground(Color.BLACK);
        takeAwayLabel.setIconTextGap(8);
        takeAwayLabel.setPreferredSize(new Dimension(160, 25));
        takeAwayPanel.add(takeAwayLabel);

        takeAwayRadioButton = new JRadioButton("Có");
        takeAwayRadioButton.setPreferredSize(new Dimension(100, 25));
        takeAwayRadioButton.setForeground(Color.BLACK);
        takeAwayRadioButton.setBackground(Color.white);
        takeAwayRadioButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        takeAwayRadioButton.addActionListener(action);
        takeAwayPanel.add(takeAwayRadioButton);
        takeAwayPanel.add(Box.createHorizontalStrut(10));

        seatingButton = new JButton("Sơ đồ");
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
        updateButton = new JButton("Cập nhật", editIcon);
        updateButton.setBackground(Color.white);
        updateButton.setForeground(Color.black);
        updateButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        updateButton.addActionListener(action);
        funcPanel.add(updateButton);
        funcPanel.add(Box.createHorizontalStrut(10));

        FontIcon trashIcon = FontIcon.of(Feather.TRASH, 24, Color.BLACK);
        deleteButton = new JButton("Xóa", trashIcon);
        deleteButton.setBackground(Color.white);
        deleteButton.setForeground(Color.black);
        deleteButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        deleteButton.addActionListener(action);
        funcPanel.add(deleteButton);
        funcPanel.add(Box.createHorizontalStrut(10));

        FontIcon invoiceIcon = FontIcon.of(Feather.FILE_TEXT, 24, Color.BLACK);
        toInvoiceButton = new JButton("Hóa đơn", invoiceIcon);
        toInvoiceButton.setBackground(Color.white);
        toInvoiceButton.setForeground(Color.black);
        toInvoiceButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        toInvoiceButton.addActionListener(action);
        funcPanel.add(toInvoiceButton);

        ptPanel.add(scrollPane);
        ptPanel.add(Box.createHorizontalStrut(10));
        ptPanel.add(right);
        panel.add(ptPanel);
        panel.add(Box.createVerticalStrut(30));
        return panel;
    }

    public void showLoadingDialog(JFrame parentFrame) {
        loadingDialog = new JDialog(parentFrame, "Đang tải...", false);
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

    public void loadFirst18MenuItem(GridBagConstraints gbc) {
        List<MenuItem> menu = DAO_MenuItem.get18MenuItems(0, 18);
        int columns = 3;
        for (int i = 0; i < 18; i++) {
            ImagePanelButton productButton = new ImagePanelButton(menu.get(i),
                    collectionBillDetails,
                    "asset/placeholder.png", 200,
                    200,
                    0.8);
            productButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
            productButton.setPreferredSize(new Dimension(250, 250)); // không bị co giãn
            productButton.setMaximumSize(new Dimension(250, 250));

            gbc.gridx = i % columns;
            gbc.gridy = i / columns;
            productPanel.add(productButton, gbc);
            allProductButtons.add(productButton);
        }

        currentOffset = 18;
    }

    public void showLoadingSuccessfullyOptionPane() {
        JOptionPane.showMessageDialog(this, "Tải thêm 18 sản phẩm thành công!", "Tải thành công", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showSearchingSuccessfullyOptionPane() {
        JOptionPane.showMessageDialog(this, "Tìm kiếm thành công!", "Tải thành công", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showCategorizingSuccessfullyOptionPane() {
        JOptionPane.showMessageDialog(this, "Lấy theo loại thành công!", "Tải thành công", JOptionPane.INFORMATION_MESSAGE);
    }
}