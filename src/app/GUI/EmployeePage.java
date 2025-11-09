package app.GUI;

import app.DAO.DAO_Employee;
import app.InitFont.CustomFont;
import app.Object.Employee;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

public class EmployeePage extends JPanel {

    private JTextField firstNameInput;
    private JTextField lastNameInput;
    private JTextField phoneInput;
    private JTextField emailInput;
    private JComboBox<String> sexCombo;   // "Nam"=true, "Nữ"=false
    private JComboBox<String> roleCombo;  // ví dụ: Nhân viên / Quản lý / Quản trị viên
    private JTextField addressInput;

    
    private JTextField searchBar;
    private JTextField idInput; // 
    private JTextField nameInput; // 
    private JTextField dbInput; // 
    private JComboBox<String> responsibilityCategory; // 

    // Bảng
    private DefaultTableModel tableModel;
    private JTable table;

    // Giữ employeeId (ẩn trong bảng)
    private static final int COL_HIDDEN_ID = 0;

    private final Color ACCENT = new Color(241, 211, 178);
    private final Color TABLE_BG = new Color(250, 245, 237);
    private final Color HEADER_BG = new Color(233, 203, 168);
    private final Color HEADER_FG = Color.BLACK;
    private final Color BORDER_COLOR = new Color(79, 92, 133);

    CustomFont customFont = new CustomFont();

    public EmployeePage() {
        setPreferredSize(new Dimension(1100, 500));
        setLayout(new BorderLayout(12, 12));
        setBackground(Color.white);
        setOpaque(true);

        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);

        loadTable();
    }

    // ====== UI Builders =======================================================

    private JPanel buildFormPanel() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(true);
        wrapper.setBackground(Color.white);
        wrapper.setBorder(BorderFactory.createEmptyBorder(12, 12, 0, 12));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 6, 6, 6);
        gc.fill = GridBagConstraints.HORIZONTAL;

        int colLabelW = 110;
        int colFieldW = 220;

        // Họ
        gc.gridx = 0; gc.gridy = 0; gc.weightx = 0;
        wrapper.add(makeRightLabel("Họ:", colLabelW), gc);
        gc.gridx = 1; gc.gridy = 0; gc.weightx = 1;
        firstNameInput = makeTextField(colFieldW);
        wrapper.add(firstNameInput, gc);

        // Tên
        gc.gridx = 2; gc.gridy = 0; gc.weightx = 0;
        wrapper.add(makeRightLabel("Tên:", colLabelW), gc);
        gc.gridx = 3; gc.gridy = 0; gc.weightx = 1;
        lastNameInput = makeTextField(colFieldW);
        wrapper.add(lastNameInput, gc);

        // SĐT
        gc.gridx = 4; gc.gridy = 0; gc.weightx = 0;
        wrapper.add(makeRightLabel("Số điện thoại:", colLabelW), gc);
        gc.gridx = 5; gc.gridy = 0; gc.weightx = 1;
        phoneInput = makeTextField(colFieldW);
        wrapper.add(phoneInput, gc);

        // Email
        gc.gridx = 0; gc.gridy = 1; gc.weightx = 0;
        wrapper.add(makeRightLabel("Email:", colLabelW), gc);
        gc.gridx = 1; gc.gridy = 1; gc.gridwidth = 3; gc.weightx = 1;
        emailInput = makeTextField(colFieldW * 2 + 20);
        wrapper.add(emailInput, gc);
        gc.gridwidth = 1;

        // Giới tính
        gc.gridx = 4; gc.gridy = 1; gc.weightx = 0;
        wrapper.add(makeRightLabel("Giới tính:", colLabelW), gc);
        gc.gridx = 5; gc.gridy = 1; gc.weightx = 1;
        sexCombo = new JComboBox<>(new String[]{"Nữ", "Nam"}); // Nữ=false, Nam=true
        styleCombo(sexCombo);
        wrapper.add(sexCombo, gc);

        // Chức vụ
        gc.gridx = 0; gc.gridy = 2; gc.weightx = 0;
        wrapper.add(makeRightLabel("Chức vụ:", colLabelW), gc);
        gc.gridx = 1; gc.gridy = 2; gc.weightx = 1;
        roleCombo = new JComboBox<>(new String[]{"Nhân viên", "Quản lý", "Quản trị viên"});
        styleCombo(roleCombo);
        wrapper.add(roleCombo, gc);

        // Địa chỉ
        gc.gridx = 2; gc.gridy = 2; gc.weightx = 0;
        wrapper.add(makeRightLabel("Địa chỉ:", colLabelW), gc);
        gc.gridx = 3; gc.gridy = 2; gc.gridwidth = 3; gc.weightx = 1;
        addressInput = makeTextField(colFieldW * 3 + 60);
        wrapper.add(addressInput, gc);
        gc.gridwidth = 1;

        // Thanh công cụ (tìm kiếm + nút)
        JPanel toolbar = buildToolbar();
        gc.gridx = 0; gc.gridy = 3; gc.gridwidth = 6; gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        wrapper.add(toolbar, gc);
        gc.gridwidth = 1;

        return wrapper;
    }

    private JPanel buildToolbar() {
        JPanel tool = new JPanel(new BorderLayout());
        tool.setOpaque(false);
        tool.setBorder(BorderFactory.createEmptyBorder(10, 0, 6, 0));

        // Left: search 
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);

        JLabel searchLabel = makeLabel("Tìm nhân viên:");
        searchLabel.setPreferredSize(new Dimension(110, 25));
        searchBar = makeTextField(240);
        JButton searchBtn = iconButton(Feather.SEARCH);
        searchBtn.addActionListener(e -> search());
        
     
        left.add(searchLabel);
        left.add(searchBar);
        left.add(searchBtn);

        // Right: actions
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);
        
        JButton addBtn   = makeButton("Thêm");
        JButton saveBtn  = makeButton("Lưu");
        JButton deleteBtn= makeButton("Xóa");
        JButton clearBtn = makeButton("Hủy thay đổi");

        addBtn.addActionListener(e -> onAdd());
        saveBtn.addActionListener(e -> onUpdate());
        deleteBtn.addActionListener(e -> onDelete());
        clearBtn.addActionListener(e -> clearForm());

        right.add(addBtn); right.add(saveBtn); right.add(deleteBtn); right.add(clearBtn);

        tool.add(left, BorderLayout.WEST);
        tool.add(right, BorderLayout.EAST);
        return tool;
    }

    private JPanel buildTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(true);
        tablePanel.setBackground(Color.white);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));

        tableModel = new DefaultTableModel(
                new Object[]{"_ID", "Tên", "Họ", "Số điện thoại", "Email", "Giới tính", "Chức vụ", "Địa chỉ"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(tableModel);
        table.setRowHeight(26);
        table.setShowGrid(false);
        table.setFillsViewportHeight(true);
        table.setBackground(TABLE_BG);
        table.setSelectionBackground(new Color(214, 224, 235));
        table.setSelectionForeground(Color.BLACK);
        table.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));

        // Ẩn cột ID
        TableColumnModel cm = table.getColumnModel();
        cm.getColumn(COL_HIDDEN_ID).setMinWidth(0);
        cm.getColumn(COL_HIDDEN_ID).setMaxWidth(0);
        cm.getColumn(COL_HIDDEN_ID).setPreferredWidth(0);

        // Header
        JTableHeader th = table.getTableHeader();
        th.setOpaque(true);
        th.setBackground(HEADER_BG);
        th.setForeground(HEADER_FG);
        th.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        th.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        // Zebra rows
        table.setDefaultRenderer(Object.class, new ZebraRenderer());

        JScrollPane sp = new JScrollPane(table);
        sp.getViewport().setBackground(TABLE_BG);
        sp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)
        ));

        tablePanel.add(sp, BorderLayout.CENTER);
        return tablePanel;
    }

    // ====== Actions=========================

    private void loadTable() {
        tableModel.setRowCount(0);
        List<Employee> list = DAO_Employee.findAll();
        for (Employee emp : list) {
            tableModel.addRow(new Object[]{
                    emp.getEmployeeId(),                                 // hidden
                    emp.getLastName(),                                    // Họ
                    emp.getFirstName(),                                   // Tên
                    nz(emp.getPhoneNumber()),
                    nz(emp.getEmail()),
                    emp.isSex() ? "Nam" : "Nữ",
                    nz(emp.getRole()),
                    nz(emp.getAddress())
            });
        }
        table.clearSelection();

        // chọn dòng -> fill form
        table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) fillFormFromSelectedRow();
        });
    }

    private void search() {
        tableModel.setRowCount(0);
        List<Employee> list = DAO_Employee.search(searchBar.getText());
        for (Employee emp : list) {
            tableModel.addRow(new Object[]{
                    emp.getEmployeeId(),                                 // hidden
                    emp.getLastName(),
                    emp.getFirstName(),
                    nz(emp.getPhoneNumber()),
                    nz(emp.getEmail()),
                    emp.isSex() ? "Nam" : "Nữ",
                    nz(emp.getRole()),
                    nz(emp.getAddress())
            });
        }
        table.clearSelection();
    }

    private void onAdd() {
        try {
        	String firstName = firstNameInput.getText().trim();
        	String lastName  = lastNameInput.getText().trim();
        	String phone     = phoneInput.getText().trim();
        	String email     = emailInput.getText().trim();

        	// ===== Regex =====
        	if (!firstName.matches("^[\\p{L} ]{1,50}$")) {
        	    JOptionPane.showMessageDialog(this, "Họ không hợp lệ (chỉ chứa chữ và khoảng trắng).");
        	    return;
        	}
        	if (!lastName.matches("^[\\p{L} ]{1,50}$")) {
        	    JOptionPane.showMessageDialog(this, "Tên không hợp lệ (chỉ chứa chữ và khoảng trắng).");
        	    return;
        	}
        	if (!phone.matches("^\\d{8,11}$")) {
        	    JOptionPane.showMessageDialog(this, "Số điện thoại phải gồm 8–11 chữ số.");
        	    return;
        	}
        	if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
        	    JOptionPane.showMessageDialog(this, "Email không hợp lệ.");
        	    return;
        	}

            Employee e = new Employee(
                    null,
                    firstName,                         // firstName
                    lastName,                          // lastName
                    "Nam".equals(sexCombo.getSelectedItem()),
                    phoneInput.getText().trim(),
                    emailInput.getText().trim(),
                    String.valueOf(roleCombo.getSelectedItem()),
                    addressInput.getText().trim()
            );

            DAO_Employee.insert(e);
            JOptionPane.showMessageDialog(this, "Đã thêm nhân viên!");
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

        String id = String.valueOf(tableModel.getValueAt(row, COL_HIDDEN_ID));
        try {
            Employee e = new Employee(
                    id,
                    lastNameInput.getText().trim(),    
                    firstNameInput.getText().trim(),   
                    "Nam".equals(sexCombo.getSelectedItem()),
                    phoneInput.getText().trim(),
                    emailInput.getText().trim(),
                    String.valueOf(roleCombo.getSelectedItem()),
                    addressInput.getText().trim()
            );
            DAO_Employee.update(e);
            JOptionPane.showMessageDialog(this, "Đã cập nhật!");
            loadTable();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi cập nhật: " + ex.getMessage());
        }
    }

    private void onDelete() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 dòng để xóa!"); return; }

        String id = String.valueOf(tableModel.getValueAt(row, COL_HIDDEN_ID));
        int confirm = JOptionPane.showConfirmDialog(this,
                "Xóa nhân viên " + id + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            DAO_Employee.delete(id);
            JOptionPane.showMessageDialog(this, "Đã xóa!");
            clearForm();
            loadTable();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi xóa: " + ex.getMessage());
        }
    }

    private void fillFormFromSelectedRow() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        String id = String.valueOf(tableModel.getValueAt(row, COL_HIDDEN_ID));
        Employee e = DAO_Employee.getEmployeeById(id);
        if (e == null) return;

        firstNameInput.setText(nz(e.getLastName()));     // Họ
        lastNameInput.setText(nz(e.getFirstName()));     // Tên
        phoneInput.setText(nz(e.getPhoneNumber()));
        emailInput.setText(nz(e.getEmail()));
        sexCombo.setSelectedItem(e.isSex() ? "Nam" : "Nữ");
        roleCombo.setSelectedItem(mapRoleToCombo(e.getRole()));
        addressInput.setText(nz(e.getAddress()));
    }

    private void clearForm() {
        firstNameInput.setText("");
        lastNameInput.setText("");
        phoneInput.setText("");
        emailInput.setText("");
        sexCombo.setSelectedIndex(0);
        roleCombo.setSelectedIndex(0);
        addressInput.setText("");
        table.clearSelection();
    }

    // ====== Helpers ===========================================================

    private JLabel makeLabel(String text) {
        JLabel lb = new JLabel(text);
        lb.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        lb.setForeground(Color.black);
        return lb;
    }

    private JLabel makeRightLabel(String text, int width) {
        JLabel lb = makeLabel(text);
        lb.setHorizontalAlignment(SwingConstants.RIGHT);
        lb.setPreferredSize(new Dimension(width, 25));
        return lb;
    }

    private JTextField makeTextField(int w) {
        JTextField tf = new JTextField();
        tf.setPreferredSize(new Dimension(w, 28));
        tf.setBackground(ACCENT);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(21, 24, 48)),
                BorderFactory.createEmptyBorder(2, 6, 2, 6)
        ));
        tf.setForeground(Color.black);
        tf.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        return tf;
    }

    private void styleCombo(JComboBox<?> cb) {
        cb.setPreferredSize(new Dimension(160, 28));
        cb.setBackground(ACCENT);
        cb.setForeground(Color.BLACK);
        cb.setBorder(BorderFactory.createLineBorder(new Color(21, 24, 48)));
        cb.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
    }

    private JButton makeButton(String text) {
        JButton b = new JButton(text);
        b.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        b.setPreferredSize(new Dimension(120, 28));
        b.setForeground(Color.black);
        b.setBackground(ACCENT);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                BorderFactory.createEmptyBorder(2, 10, 2, 10)
        ));
        return b;
    }

    private JButton iconButton(Feather icon) {
        JButton b = new JButton(FontIcon.of(icon, 18, Color.BLACK));
        b.setPreferredSize(new Dimension(36, 28));
        b.setBackground(ACCENT);
        b.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        b.setFocusPainted(false);
        return b;
    }

    private String nz(String s) { return s == null ? "" : s; }

    private String mapRoleToCombo(String role) {
        if (role == null) return "Nhân viên";
        String r = role.toLowerCase();
        if (r.contains("trị")) return "Quản trị viên";
        if (r.contains("lý"))  return "Quản lý";
        return "Nhân viên";
    }

    /** Zebra row renderer (nền sáng hơn, chọn vẫn rõ) */
    private static class ZebraRenderer extends DefaultTableCellRenderer {
        private final Color even = new Color(255, 252, 247);
        private final Color odd  = new Color(248, 243, 236);

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (!isSelected) {
                c.setBackground((row % 2 == 0) ? even : odd);
                c.setForeground(Color.BLACK);
            }
            setBorder(noFocusBorder);
            return c;
        }
    }
}