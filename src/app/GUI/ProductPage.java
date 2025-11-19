package app.GUI;

import app.DAO.DAO_MenuItem;
import app.InitFont.CustomFont;
import app.Object.MenuItem;
import app.Components.CustomTableCellRenderer;
import app.Components.CustomTableHeaderRenderer;

import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.swing.FontIcon;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class ProductPage extends JPanel {


    private JTextField idInput;               // read-only 
    private JTextField nameInput;             // item_name
    private JTextField priceInput;            // price
    private JComboBox<String> categoryComboBox; // category
    private JTextArea descriptionArea;        // description

    // ====== Tìm kiếm ======
    private JTextField searchBar;

    // ====== Bảng ======
    private DefaultTableModel tableModel;
    private JTable table;
    private static final int COL_HIDDEN_ID = 0;

    private final CustomFont customFont = new CustomFont();
    private final DecimalFormat money = new DecimalFormat("#,##0");

    public ProductPage() {
        setPreferredSize(new Dimension(1100, 500));
        setLayout(new BorderLayout());
        setBackground(Color.white);
        setOpaque(true);

        add(buildCompactHeader(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);

        loadTable();
    }

    // ---------------------------------------------------------------------
    // Header compact (GridBagLayout) 
    // ---------------------------------------------------------------------
    private JPanel buildCompactHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setPreferredSize(new Dimension(1100, 160)); // gọn, thấp

        // Lưới 2 cột
        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 10, 4, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // ===== Cột trái =====
        int row = 0;

        // Id (read-only)
        gbc.gridx = 0; gbc.gridy = row;
        grid.add(makeLabel("Id:"), gbc);
        gbc.gridx = 1;
        idInput = makeTextField(180);
        idInput.setEditable(false);
        idInput.setFocusable(false);
        idInput.setToolTipText("ID được hệ thống sinh tự động");
        idInput.setBackground(new Color(235, 225, 210)); // gợi ý read-only
        grid.add(idInput, gbc);

        // Name
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        grid.add(makeLabel("Tên:"), gbc);
        gbc.gridx = 1;
        nameInput = makeTextField(180);
        grid.add(nameInput, gbc);

        // Price
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        grid.add(makeLabel("Giá:"), gbc);
        gbc.gridx = 1;
        priceInput = makeTextField(180);
        grid.add(priceInput, gbc);

        // ===== Cột phải =====
        row = 0;
        gbc.gridx = 2; gbc.gridy = row;
        grid.add(makeLabel("Loại:"), gbc);
        gbc.gridx = 3;
        categoryComboBox = new JComboBox<>(new String[]{"Cà phê", "Trà", "Nước ép", "Bánh ngọt", "Khác"});
        categoryComboBox.setPreferredSize(new Dimension(180, 25));
        categoryComboBox.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        categoryComboBox.setForeground(Color.BLACK);
        categoryComboBox.setBackground(new Color(241, 211, 178));
        categoryComboBox.setBorder(BorderFactory.createLineBorder(new Color(21, 24, 48)));
        grid.add(categoryComboBox, gbc);

        // Description 
        row++;
        gbc.gridx = 2; gbc.gridy = row;
        grid.add(makeLabel("Mô tả:"), gbc);
        gbc.gridx = 3;
        descriptionArea = new JTextArea(3, 22);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setForeground(Color.BLACK);
        descriptionArea.setBackground(new Color(241, 211, 178));
        descriptionArea.setBorder(BorderFactory.createLineBorder(new Color(21, 24, 48)));
        descriptionArea.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        JScrollPane descScroll = new JScrollPane(descriptionArea);
        descScroll.setPreferredSize(new Dimension(220, 60));
        grid.add(descScroll, gbc);

        header.add(grid, BorderLayout.CENTER);

        // ===== Ảnh  =====
        JPanel imgPanel = new JPanel(new BorderLayout());
        imgPanel.setPreferredSize(new Dimension(230, 150)); 
        imgPanel.setOpaque(false);

        String imagePath = "asset/placeholder.png";
        try {
            Image image = ImageIO.read(new File(imagePath));
            int newWidth = (int) (200 * 0.8f);
            int newHeight = (int) (200 * 0.8f);
            Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(scaledImage);
            JLabel imgLabel = new JLabel(imageIcon);
            imgLabel.setHorizontalAlignment(JLabel.CENTER);
            imgLabel.setVerticalAlignment(JLabel.CENTER);
            imgPanel.add(imgLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            // Hiển thị icon mặc định nếu không load được ảnh
            JLabel imgLabel = new JLabel(FontIcon.of(Feather.IMAGE, 100, Color.BLACK));
            imgLabel.setHorizontalAlignment(JLabel.CENTER);
            imgLabel.setVerticalAlignment(JLabel.CENTER);
            imgPanel.add(imgLabel, BorderLayout.CENTER);
        }

        header.add(imgPanel, BorderLayout.EAST);
        return header;
    }

    // ---------------------------------------------------------------------
    // Bảng + Toolbar
    // ---------------------------------------------------------------------
    private JPanel buildTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);

        // Toolbar
        JPanel tool = new JPanel(new FlowLayout(FlowLayout.CENTER));
        tool.setOpaque(false);

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        left.setPreferredSize(new Dimension(420, 35));
        left.setOpaque(false);
        Border lineBorder = BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(255, 213, 146));
        left.setBorder(lineBorder);

        JLabel searchLabel = makeLabel("Tìm sản phẩm:");
        searchLabel.setPreferredSize(new Dimension(120, 25));
        searchBar = makeTextField(200);
        JButton searchBtn = new JButton(FontIcon.of(Feather.SEARCH, 20, Color.BLACK));
        searchBtn.setBackground(new Color(241, 211, 178));
        searchBtn.addActionListener(e -> search());

        // === Nút REFRESH (Load lại trang) ===
        JButton refreshBtn = new JButton(FontIcon.of(Feather.ROTATE_CW, 20, Color.BLACK));
        refreshBtn.setBackground(new Color(241, 211, 178));
        refreshBtn.setToolTipText("Làm mới danh sách");
        refreshBtn.setPreferredSize(new Dimension(36, 28));
        refreshBtn.addActionListener(e -> {
            clearForm();
            loadTable();
        });

        left.add(searchLabel);
        left.add(searchBar);
        left.add(searchBtn);
        left.add(refreshBtn); // <-- nút refresh được thêm vào đây

        JPanel right = new JPanel(new FlowLayout(FlowLayout.LEFT));
        right.setOpaque(false);
        right.setPreferredSize(new Dimension(600, 35));

        JButton addBtn = makeButton("Thêm");
        JButton saveBtn = makeButton("Lưu");
        JButton deleteBtn = makeButton("Xóa");
        JButton clearBtn = makeButton("Hủy");

        addBtn.addActionListener(e -> onAdd());
        saveBtn.addActionListener(e -> onUpdate());
        deleteBtn.addActionListener(e -> onDelete());       
        clearBtn.addActionListener(e -> clearForm());

        right.add(addBtn);
        right.add(deleteBtn);
        right.add(clearBtn);
        right.add(saveBtn);

        tool.add(left);
        tool.add(right);
        tablePanel.add(tool, BorderLayout.NORTH);

        // Table model
        tableModel = new DefaultTableModel(
                new Object[]{"_ID", "Tên", "Giá", "Loại", "Mô tả"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        table.setForeground(Color.BLACK);
        table.setBackground(new Color(252, 244, 233)); 

        // Ẩn cột ID
        table.getColumnModel().getColumn(COL_HIDDEN_ID).setMinWidth(0);
        table.getColumnModel().getColumn(COL_HIDDEN_ID).setMaxWidth(0);
        table.getColumnModel().getColumn(COL_HIDDEN_ID).setPreferredWidth(0);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setBackground(new Color(241, 211, 178));
        tableHeader.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        tableHeader.setDefaultRenderer(new CustomTableHeaderRenderer());

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new CustomTableCellRenderer());
        }

        // Độ rộng cột
        TableColumn nameCol = table.getColumnModel().getColumn(1);
        nameCol.setPreferredWidth(160);

        TableColumn priceCol = table.getColumnModel().getColumn(2);
        priceCol.setPreferredWidth(80);

        TableColumn cateCol = table.getColumnModel().getColumn(3);
        cateCol.setPreferredWidth(90);

        TableColumn descCol = table.getColumnModel().getColumn(4);
        descCol.setPreferredWidth(350);

        table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) fillFormFromSelectedRow();
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(241, 211, 178));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    // ---------------------------------------------------------------------
    // Actions
    // ---------------------------------------------------------------------
    private void loadTable() {
        tableModel.setRowCount(0);
        List<MenuItem> list = DAO_MenuItem.getAllMenuItem();
        for (MenuItem mi : list) {
            tableModel.addRow(new Object[]{
                    mi.getItemId(),
                    nz(mi.getName()),
                    money.format(mi.getPrice()),
                    nz(mi.getCategory()),
                    nz(mi.getDescription())
            });
        }
        table.clearSelection();
    }

    private void search() {
        String key = searchBar.getText().trim();
        tableModel.setRowCount(0);
        List<MenuItem> list = key.isEmpty()
                ? DAO_MenuItem.getAllMenuItem()
                : DAO_MenuItem.findMultipleMenuItem(key);

        for (MenuItem mi : list) {
            tableModel.addRow(new Object[]{
                    mi.getItemId(),
                    nz(mi.getName()),
                    money.format(mi.getPrice()),
                    nz(mi.getCategory()),
                    nz(mi.getDescription())
            });
        }
        table.clearSelection();
    }

    private void onAdd() {
        try {
            // Nếu idInput có giá trị, có nghĩa là form đang hiển thị dữ liệu cũ (chưa được Hủy)
            // và người dùng đang cố gắng Thêm sản phẩm mới.
            if (!idInput.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không thể thêm sản phẩm sẵn có. Vui lòng bấm 'Hủy' để xoá trắng form và thêm mới.", "Lỗi Thêm", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // ID bỏ qua 
            String name = nameInput.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập Name!");
                return;
            }

            float price = parsePrice(priceInput.getText().trim());
            String cate = String.valueOf(categoryComboBox.getSelectedItem());
            String desc = descriptionArea.getText().trim();

            MenuItem mi = new MenuItem(name, price, cate, desc);
            DAO_MenuItem.insert(mi);

            JOptionPane.showMessageDialog(this, "Đã thêm sản phẩm!");
            clearForm();
            loadTable();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi thêm: " + ex.getMessage());
        }
    }

    private void onUpdate() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Hãy chọn 1 dòng để lưu/cập nhật!"); return; }

        try {
            String id = String.valueOf(tableModel.getValueAt(row, COL_HIDDEN_ID));
            String name = nameInput.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập Name!");
                return;
            }

            float price = parsePrice(priceInput.getText().trim());
            String cate = String.valueOf(categoryComboBox.getSelectedItem());
            String desc = descriptionArea.getText().trim();

            MenuItem mi = new MenuItem(id, name, price, cate, desc);
            DAO_MenuItem.update(mi);

            JOptionPane.showMessageDialog(this, "Đã cập nhật sản phẩm!");
            loadTable();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi cập nhật: " + ex.getMessage());
        }
    }

    private void onDelete() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 dòng để xoá!"); return; }

        String id = String.valueOf(tableModel.getValueAt(row, COL_HIDDEN_ID));
        int confirm = JOptionPane.showConfirmDialog(
                this, "Xoá sản phẩm " + id + "?", "Xác nhận", JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            DAO_MenuItem.delete(id);
            JOptionPane.showMessageDialog(this, "Đã xoá!");
            clearForm();
            loadTable();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi xoá: " + ex.getMessage());
        }
    }

    private void fillFormFromSelectedRow() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        idInput.setText(nz(tableModel.getValueAt(row, 0)));
        nameInput.setText(nz(tableModel.getValueAt(row, 1)));
        priceInput.setText(stripMoney(nz(tableModel.getValueAt(row, 2))));
        categoryComboBox.setSelectedItem(nz(tableModel.getValueAt(row, 3)));
        descriptionArea.setText(nz(tableModel.getValueAt(row, 4)));
    }

    private void clearForm() {
        idInput.setText("");
        nameInput.setText("");
        priceInput.setText("");
        categoryComboBox.setSelectedIndex(0);
        descriptionArea.setText("");
        table.clearSelection();
    }

    // ---------------------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------------------
    private JLabel makeLabel(String text) {
        JLabel lb = new JLabel(text);
        lb.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        lb.setForeground(Color.black);
        return lb;
    }

    private JTextField makeTextField(int w) {
        JTextField tf = new JTextField();
        tf.setPreferredSize(new Dimension(w, 25));
        tf.setBackground(new Color(241, 211, 178));
        tf.setBorder(BorderFactory.createLineBorder(new Color(21, 24, 48)));
        tf.setForeground(Color.black);
        tf.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        return tf;
    }

    private JButton makeButton(String text) {
        JButton b = new JButton(text);
        b.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        b.setPreferredSize(new Dimension(120, 25));
        b.setForeground(Color.black);
        b.setBackground(new Color(241, 211, 178));
        return b;
    }

    private String nz(Object o) { return (o == null) ? "" : String.valueOf(o); }

    private float parsePrice(String s) {
        if (s == null || s.isEmpty()) return 0f;
        s = s.replace(",", "").trim(); 
        return Float.parseFloat(s);
    }

    private String stripMoney(String s) {
        return s == null ? "" : s.replace(",", "");
    }
}
