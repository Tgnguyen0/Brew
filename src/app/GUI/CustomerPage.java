package app.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JTable;


import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.swing.FontIcon;


import app.InitFont.CustomFont;

import app.DAO.DAO_Customer;
import app.DAO.DAO_Bill;
import app.Object.Customer;
import app.Object.Bill;


class CustomTableCellRenderer extends javax.swing.table.DefaultTableCellRenderer {}
class CustomTableHeaderRenderer extends javax.swing.table.DefaultTableCellRenderer {}


public class CustomerPage extends JPanel {
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField searchBar;

    // Input Fields
    private JTextField idInput;
    private JTextField firstNameInput;
    private JTextField lastNameInput;
    private JTextField phoneInput;
    private JTextField emailInput;
    private JTextField sexInput;
    private JTextField createdDateInput;

    // DAO
    private DAO_Customer customerDAO = new DAO_Customer();
    private DAO_Bill billDAO = new DAO_Bill();


    private CustomFont customFont = new CustomFont();

    private final Font ROBOTO_REGULAR_12;
    private final Font ROBOTO_BOLD_12;

    // --- HẰNG SỐ MÀU SẮC ĐỒNG BỘ ---
    private static final Color INPUT_EDITABLE_BG = new Color(241, 211, 178);
    private static final Color INPUT_READONLY_BG = new Color(220, 220, 220);
    private static final Color PRIMARY_ACCENT_COLOR = new Color(21, 24, 48);

    private static final DateTimeFormatter DATE_TIME_DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public CustomerPage() {
        // Khởi tạo Font từ CustomFont
        List<Font> robotoFonts = customFont.getRobotoFonts();
        ROBOTO_REGULAR_12 = robotoFonts.size() > 0 ? robotoFonts.get(0).deriveFont(Font.PLAIN, 12f) : new Font("SansSerif", Font.PLAIN, 12);
        ROBOTO_BOLD_12 = robotoFonts.size() > 1 ? robotoFonts.get(1).deriveFont(Font.BOLD, 12f) : new Font("SansSerif", Font.BOLD, 12);



        setPreferredSize(new Dimension(1100, 500));
        setLayout(new BorderLayout());
        setBackground(Color.white);
        setOpaque(true);

        // Tiêu đề
        JLabel introLabel = new JLabel("<html><div style='font-size:20px'><b>Danh Sách Khách Hàng</b></div></html>", SwingConstants.CENTER);
        introLabel.setForeground(Color.BLACK);
        introLabel.setBackground(Color.white);
        introLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 20));
        introLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(introLabel, BorderLayout.NORTH);

        // --- Tạo Panel chứa Input và Table ---
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BorderLayout());
        mainContentPanel.setOpaque(false);

        JPanel inputPanel = createEmpTextBox();
        mainContentPanel.add(inputPanel, BorderLayout.NORTH);

        JPanel tablePanel = createEmpTablePanel();
        mainContentPanel.add(tablePanel, BorderLayout.CENTER);

        add(mainContentPanel, BorderLayout.CENTER);

        refreshDataAndInputs();
        setupKeyBindings();
    }

    private void setupKeyBindings() {
        InputMap inputMap = this.getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "refreshAction");
        actionMap.put("refreshAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshDataAndInputs();
            }
        });

        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchCustomer();
                }
            }
        });
    }

    // --- LOGIC CHỨC NĂNG CƠ SỞ ---
    private void refreshDataAndInputs() {
        clearInputFields();
        loadCustomerDataToTable(customerDAO.getAllCustomers());
        if (searchBar != null) searchBar.setText("");
    }

    private void clearInputFields() {
        idInput.setText("");
        firstNameInput.setText("");
        lastNameInput.setText("");
        phoneInput.setText("");
        emailInput.setText("");
        sexInput.setText("");
        createdDateInput.setText("");
        if (table != null && table.getSelectedRow() != -1) {
            table.clearSelection();
        }
    }

    private void loadCustomerDataToTable(List<Customer> customers) {
        tableModel.setRowCount(0);
        int stt = 1;

        for (Customer customer : customers) {
            String sexText = customer.getSex() ? "Nam" : "Nữ";
            String createdDateStr = customer.getCreatedDate() != null ? customer.getCreatedDate().format(DATE_TIME_DISPLAY_FORMATTER) : "N/A";

            tableModel.addRow(new Object[]{
                    stt++,
                    customer.getFullName(),
                    customer.getPhoneNumber(),
                    customer.getEmail(),
                    sexText,
                    createdDateStr
            });
        }
    }

    private void displaySelectedRowData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {

            String phone = (String) tableModel.getValueAt(selectedRow, 2);

            Customer foundCustomer = customerDAO.searchCustomerByPhoneNumber(phone);

            if (foundCustomer != null) {

                idInput.setText(foundCustomer.getCustomerId());
                firstNameInput.setText(foundCustomer.getFirstName());
                lastNameInput.setText(foundCustomer.getLastName());
                phoneInput.setText(foundCustomer.getPhoneNumber());
                emailInput.setText(foundCustomer.getEmail());
                sexInput.setText(foundCustomer.getSex() ? "Nam" : "Nữ");
                createdDateInput.setText(foundCustomer.getCreatedDate() != null ? foundCustomer.getCreatedDate().format(DATE_TIME_DISPLAY_FORMATTER) : "N/A");
            } else {
                System.err.println("Lỗi: Không tìm thấy khách hàng khớp chính xác SĐT: " + phone);
            }
        }
    }

    /**
     * CHỨC NĂNG 1: THÊM KHÁCH HÀNG MỚI (CREATE)
     */
    private void addNewCustomer() {
        String firstName = firstNameInput.getText().trim();
        String lastName = lastNameInput.getText().trim();
        String phone = phoneInput.getText().trim();
        String email = emailInput.getText().trim();
        String sexStr = sexInput.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || sexStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ Họ, Tên, SĐT và Giới tính để thêm.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }


        Customer newCustomer = new Customer();
        newCustomer.setCustomerId("KH" + UUID.randomUUID().toString().substring(0, 5).toUpperCase());
        newCustomer.setFirstName(firstName);
        newCustomer.setLastName(lastName);
        newCustomer.setPhoneNumber(phone);
        newCustomer.setEmail(email);
        newCustomer.setSex(sexStr.equalsIgnoreCase("Nam"));
        newCustomer.setCreatedDate(LocalDateTime.now());

        if (customerDAO.addCustomer(newCustomer)) {
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            refreshDataAndInputs();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại. (Lỗi DAO)", "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * CHỨC NĂNG 2: XÓA KHÁCH HÀNG (DELETE)
     */
    private void deleteCustomer() {
        String customerId = idInput.getText().trim();

        if (customerId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa khách hàng " + firstNameInput.getText() + " " + lastNameInput.getText() + "?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (billDAO.getBillsByCustomerId(customerId).size() > 0) {
                JOptionPane.showMessageDialog(this, "Khách hàng này đã có hóa đơn. Không thể xóa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (customerDAO.deleteCustomer(customerId)) { // Giả sử DAO có hàm deleteCustomer
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                refreshDataAndInputs();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thất bại. (Lỗi DAO)", "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * CHỨC NĂNG 3: CẬP NHẬT THÔNG TIN KHÁCH HÀNG (UPDATE)
     */
    private void updateCustomerData() {
        String customerId = idInput.getText().trim();

        if (customerId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần cập nhật.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String firstName = firstNameInput.getText().trim();
        String lastName = lastNameInput.getText().trim();
        String phone = phoneInput.getText().trim();
        String email = emailInput.getText().trim();
        String sexStr = sexInput.getText().trim();


        if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || sexStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ, Tên, SĐT và Giới tính không được để trống khi cập nhật.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Customer updatedCustomer = new Customer();
        updatedCustomer.setCustomerId(customerId);
        updatedCustomer.setFirstName(firstName);
        updatedCustomer.setLastName(lastName);
        updatedCustomer.setPhoneNumber(phone);
        updatedCustomer.setEmail(email);
        updatedCustomer.setSex(sexStr.equalsIgnoreCase("Nam"));
        updatedCustomer.setCreatedDate(customerDAO.getCustomerById(customerId).getCreatedDate());


        if (customerDAO.updateCustomer(updatedCustomer)) { // Giả sử DAO có hàm updateCustomer
            JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            refreshDataAndInputs();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thất bại. (Lỗi DAO)", "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * CHỨC NĂNG 4: TÌM KIẾM KHÁCH HÀNG (SEARCH)
     */
    private void searchCustomer() {
        String keyword = searchBar.getText().trim();

        if (keyword.isEmpty()) {
            loadCustomerDataToTable(customerDAO.getAllCustomers());
            return;
        }

        List<Customer> allCustomers = customerDAO.getAllCustomers();
        List<Customer> results = allCustomers.stream()
                .filter(c -> c.getFullName().toLowerCase().contains(keyword.toLowerCase()) ||
                        c.getPhoneNumber().contains(keyword))
                .collect(Collectors.toList());

        loadCustomerDataToTable(results);

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng nào khớp với từ khóa: " + keyword, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // --- CÁC HÀM TẠO GIAO DIỆN ---
    private JPanel createEmpTextBox() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(1100, 200));
        panel.setOpaque(false);


        JPanel emptyW = new JPanel();
        emptyW.setPreferredSize(new Dimension(15, 300));
        emptyW.setOpaque(false);
        panel.add(emptyW, BorderLayout.WEST);


        JPanel emptyN = new JPanel();
        emptyN.setPreferredSize(new Dimension(1100, 5));
        emptyN.setOpaque(false);
        panel.add(emptyN, BorderLayout.NORTH);


        JPanel emptyE = new JPanel();
        emptyE.setPreferredSize(new Dimension(15, 300));
        emptyE.setOpaque(false);
        panel.add(emptyE, BorderLayout.EAST);

        JPanel emptyS = new JPanel();
        emptyS.setPreferredSize(new Dimension(1100, 15));
        emptyS.setOpaque(false);
        panel.add(emptyS, BorderLayout.SOUTH);

        JPanel tbiPanel = new JPanel();
        tbiPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
        tbiPanel.setPreferredSize(new Dimension(1100, 300));
        tbiPanel.setOpaque(false);

        JPanel tbPanel = new JPanel();
        tbPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        tbPanel.setPreferredSize(new Dimension(800, 250));
        tbPanel.setOpaque(false);

        // --- Cột 1 (Mã, Họ, Tên) ---
        JPanel column1 = createInputColumn();
        column1.setPreferredSize(new Dimension(250, 250));

        // Mã KH
        idInput = createInputField("Mã KH:", column1, false, 120, 100);
        // Họ và Tên (Editable)
        lastNameInput = createInputField("Họ:", column1, true, 120, 100);
        firstNameInput = createInputField("Tên:", column1, true, 120, 100);

        tbPanel.add(column1);

        // --- Cột 2 (SĐT, Email, Giới tính) ---
        JPanel column2 = createInputColumn();
        column2.setPreferredSize(new Dimension(250, 250));

        // SĐT, Email, Giới tính (Editable)
        phoneInput = createInputField("SĐT:", column2, true, 120, 100);
        emailInput = createInputField("Email:", column2, true, 120, 100);
        sexInput = createInputField("Giới tính:", column2, true, 120, 100);

        tbPanel.add(column2);

        // --- Cột 3 (Ngày KT, Lịch sử) ---
        JPanel column3 = createInputColumn();
        column3.setPreferredSize(new Dimension(250, 250));

        // Ngày tạo (Readonly)
        createdDateInput = createInputField("Ngày KT:", column3, false, 120, 100);

        // Thêm nút "Lịch sử mua hàng"
        JButton historyButton = new JButton("Lịch sử mua hàng");
        historyButton.setFont(ROBOTO_BOLD_12);
        historyButton.setPreferredSize(new Dimension(220, 30));
        historyButton.setForeground(Color.white);
        historyButton.setBackground(PRIMARY_ACCENT_COLOR);
        historyButton.addActionListener(e -> {
            String selectedId = idInput.getText().trim();

            // Lấy dữ liệu hiện tại từ input fields
            String fullName = firstNameInput.getText().trim() + " " + lastNameInput.getText().trim();
            String phoneNumber = phoneInput.getText().trim();

            if (selectedId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xem lịch sử.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            } else {
                // Mở cửa sổ lịch sử
                showHistoryDialog(selectedId, fullName, phoneNumber);
            }
        });

        column3.add(new JPanel() {{
            setOpaque(false);
            setPreferredSize(new Dimension(220, 10));
        }});
        column3.add(historyButton);

        tbPanel.add(column3);

        tbiPanel.add(tbPanel);

        // Panel ảnh
        JPanel imgPanel = new JPanel();
        imgPanel.setLayout(new BorderLayout());
        imgPanel.setPreferredSize(new Dimension(150, 150));
        imgPanel.setOpaque(false);
        imgPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        JLabel imgLabel = new JLabel(FontIcon.of(Feather.USER, 100, Color.black));
        imgLabel.setHorizontalAlignment(JLabel.CENTER);
        imgLabel.setVerticalAlignment(JLabel.CENTER);
        imgPanel.add(imgLabel, BorderLayout.CENTER);

        JPanel imgContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        imgContainer.setOpaque(false);
        imgContainer.add(imgPanel);
        imgContainer.setPreferredSize(new Dimension(200, 200));

        tbiPanel.add(imgContainer);

        panel.add(tbiPanel, BorderLayout.CENTER);

        return panel;
    }

    private void showHistoryDialog(String customerId, String fullName, String phoneNumber) {
        // Giả lập lấy danh sách Bills
        List<Bill> customerBills = billDAO.getBillsByCustomerId(customerId);

        app.GUI.HistoryDialog historyDialog = new app.GUI.HistoryDialog(
                SwingUtilities.getWindowAncestor(this),
                customerId,
                fullName,
                phoneNumber,
                customerBills
        );
        historyDialog.setVisible(true);

    }


    private JPanel createInputColumn() {
        JPanel column = new JPanel();
        column.setLayout(new FlowLayout(FlowLayout.LEFT));
        column.setOpaque(false);
        return column;
    }

    private JTextField createInputField(String labelText, JPanel parentPanel, boolean isEditable, int inputWidth, int labelWidth) {
        JLabel label = new JLabel(labelText);
        label.setFont(ROBOTO_REGULAR_12);
        label.setForeground(Color.black);
        label.setPreferredSize(new Dimension(labelWidth, 25));
        parentPanel.add(label);

        JTextField input = new JTextField();
        input.setPreferredSize(new Dimension(inputWidth, 25));
        input.setBorder(BorderFactory.createLineBorder(PRIMARY_ACCENT_COLOR));
        input.setForeground(Color.black);
        input.setFont(ROBOTO_REGULAR_12);

        if (!isEditable) {
            input.setEditable(false);
            input.setBackground(INPUT_READONLY_BG);
            input.setForeground(Color.DARK_GRAY);
            input.setFocusable(false);
        } else {
            input.setEditable(true);
            input.setBackground(INPUT_EDITABLE_BG);
        }

        parentPanel.add(input);
        return input;
    }

    private JPanel createEmpTablePanel() {
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setOpaque(false);

        JPanel emptyW = new JPanel();
        emptyW.setPreferredSize(new Dimension(20, 300));
        emptyW.setOpaque(false);
        tablePanel.add(emptyW, BorderLayout.WEST);

        JPanel emptyE = new JPanel();
        emptyE.setPreferredSize(new Dimension(20, 300));
        emptyE.setOpaque(false);
        tablePanel.add(emptyE, BorderLayout.EAST);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.setPreferredSize(new Dimension(900, 50));
        topPanel.setOpaque(false);

        JPanel left = new JPanel();
        left.setLayout(new FlowLayout(FlowLayout.LEFT));
        left.setPreferredSize(new Dimension(400, 35));
        Border lineBorder = BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black);
        left.setBorder(lineBorder);
        left.setOpaque(false);

        JLabel searchLabel = new JLabel("Tìm Kiếm Khách Hàng:");
        searchLabel.setFont(ROBOTO_REGULAR_12);
        searchLabel.setForeground(Color.black);
        left.add(searchLabel);

        searchBar = new JTextField();
        searchBar.setForeground(Color.black);
        searchBar.setBackground(INPUT_EDITABLE_BG); // Dùng màu nền input editable
        searchBar.setBorder(null);
        searchBar.setFont(ROBOTO_REGULAR_12);
        searchBar.setPreferredSize(new Dimension(170, 25));
        left.add(searchBar);

        FontIcon lookingGlassIcon = FontIcon.of(Feather.SEARCH, 24, Color.BLACK);
        JButton findProduct = new JButton(lookingGlassIcon);
        findProduct.setFont(ROBOTO_REGULAR_12);
        findProduct.setForeground(Color.black);
        findProduct.setBackground(INPUT_EDITABLE_BG);
        findProduct.setPreferredSize(new Dimension(30, 30));
        findProduct.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        findProduct.addActionListener(e -> searchCustomer());
        left.add(findProduct);

        topPanel.add(left);

        JPanel right = new JPanel();
        right.setLayout(new FlowLayout(FlowLayout.LEFT));
        right.setPreferredSize(new Dimension(450, 35));
        right.setOpaque(false);

        JButton addButton = new JButton("Thêm");
        addButton.setFont(ROBOTO_REGULAR_12);
        addButton.setPreferredSize(new Dimension(100, 25));
        addButton.setForeground(Color.black);
        addButton.setBackground(INPUT_EDITABLE_BG);
        right.add(addButton);

        JButton deleteButton = new JButton("Xóa");
        deleteButton.setFont(ROBOTO_REGULAR_12);
        deleteButton.setPreferredSize(new Dimension(100, 25));
        deleteButton.setForeground(Color.black);
        deleteButton.setBackground(INPUT_EDITABLE_BG);
        right.add(deleteButton);

        JButton updateButton = new JButton("Cập nhật");
        updateButton.setFont(ROBOTO_REGULAR_12);
        updateButton.setPreferredSize(new Dimension(100, 25));
        updateButton.setForeground(Color.black);
        updateButton.setBackground(INPUT_EDITABLE_BG);
        right.add(updateButton);

        topPanel.add(right);
        tablePanel.add(topPanel, BorderLayout.NORTH);

        this.tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel.addColumn("STT");
        tableModel.addColumn("Họ và Tên");
        tableModel.addColumn("SĐT");
        tableModel.addColumn("Email");
        tableModel.addColumn("Giới tính");
        tableModel.addColumn("Ngày KT");

        table = new JTable(tableModel);
        table.setFont(ROBOTO_REGULAR_12);
        table.setForeground(Color.black);
        table.setBackground(Color.white);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setForeground(Color.black);
        tableHeader.setBackground(Color.white);
        tableHeader.setDefaultRenderer(new CustomTableHeaderRenderer());
        tableHeader.setFont(ROBOTO_REGULAR_12);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new CustomTableCellRenderer());
        }

        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setForeground(Color.black);
        scrollPane.getViewport().setBackground(Color.white);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // --- Event Listeners ---
        addButton.addActionListener(e -> addNewCustomer());
        deleteButton.addActionListener(e -> deleteCustomer());
        updateButton.addActionListener(e -> updateCustomerData());

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    displaySelectedRowData();
                }
            }
        });

        return tablePanel;
    }
}