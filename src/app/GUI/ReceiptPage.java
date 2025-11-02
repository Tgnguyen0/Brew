package app.GUI;

import app.Components.CustomTableCellRenderer;
import app.Components.CustomTableHeaderRenderer;
import app.InitFont.CustomFont;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.RowSorter.SortKey;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableColumn;

public class ReceiptPage extends JPanel {
    private CustomFont customFont = new CustomFont();
    private DefaultTableModel productTableModel;
    private JTable productTable;
    private JTextField searchInput;
    private JTextField fromDateInput; // Trường nhập ngày bắt đầu
    private JTextField toDateInput;   // Trường nhập ngày kết thúc
    private TableRowSorter<DefaultTableModel> sorter;
    // Đã xóa: private DateSortControl dateSortControl; 

    // Dữ liệu mẫu ban đầu
    private final Object[][] initialData = {
        {1, "HD0001", "Nguyễn Văn A", "Trần Thị B", "0901234567", "25/10/2025 10:30", 3, "75,000"},
        {2, "HD0002", "Lê Thị C", "Khách lẻ", "N/A", "25/10/2025 11:00", 2, "60,000"},
        {3, "HD0003", "Nguyễn Văn A", "Phạm Văn D", "0909876543", "24/10/2025 15:45", 5, "120,000"},
        {4, "HD0004", "Trần Văn E", "Nguyễn Thị F", "0912345678", "24/10/2025 09:15", 4, "95,000"},
        {5, "HD0005", "Lê Thị C", "Khách đoàn", "N/A", "26/10/2025 14:00", 10, "250,000"},
        {6, "HD0006", "Phạm Thị G", "Khách lẻ", "N/A", "27/10/2025 08:00", 1, "35,000"}
    };

    // Index của cột Creation Date (cột 5)
    private static final int DATE_COLUMN_INDEX = 5; 

    public ReceiptPage() {
        setPreferredSize(new Dimension(1100, 500));
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Color.white);

        // --- Cải tiến Focus cho F5 ---
        setFocusTraversalKeysEnabled(false); 
        setFocusable(true);
        requestFocusInWindow();
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F5) {
                    refreshData();
                }
            }
        });
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();
            }
        });
        // -----------------------------

        // Vùng đệm 2 bên
        JPanel emptyL = new JPanel();
        emptyL.setPreferredSize(new Dimension(16, 500));
        emptyL.setOpaque(false);
        add(emptyL, BorderLayout.WEST);

        JPanel emptyR = new JPanel();
        emptyR.setPreferredSize(new Dimension(16, 500));
        emptyR.setOpaque(false);
        add(emptyR, BorderLayout.EAST);

        add(createReceiptPanel(), BorderLayout.CENTER);
        
        // Thêm tiêu đề vào NORTH
        // Đã đổi: "Invoices List" -> "Danh Sách Hóa Đơn"
        JLabel introLabel = new JLabel("<html><div style='font-size:20px'><b>Danh Sách Hóa Đơn</b></div></html>", SwingConstants.CENTER);
        introLabel.setForeground(Color.BLACK);
        introLabel.setBackground(Color.white);
        introLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 20));
        introLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(introLabel, BorderLayout.NORTH);
    }

    public JPanel createReceiptPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BorderLayout(0, 10)); 

        // --- Table Setup (Đặt trước để có productTable và sorter) ---
        productTableModel = createTableModel();
        loadInitialData();
        productTable = new JTable(productTableModel);
        
        sorter = new TableRowSorter<>(productTableModel);
        productTable.setRowSorter(sorter);
        
        // Cài đặt Comparator cho cột Creation Date để sắp xếp đúng ngày tháng
        setupDateSorter(); 
        
        customizeTable(productTable);

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.getViewport().setBackground(new Color(241, 211, 178));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(79, 92, 133)));
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Sắp xếp mặc định: Creation Date, Mới nhất trước (DESCENDING)
        setupDefaultSort();
        
        // --- Panel Chức năng TOP (Tìm kiếm & Lọc ngày) ---
        JPanel topControlPanel = createTopControlPanel();
        mainPanel.add(topControlPanel, BorderLayout.NORTH);

        return mainPanel;
    }
    
    /**
     * Cài đặt Comparator tùy chỉnh để sắp xếp cột ngày tháng (dd/MM/yyyy HH:mm)
     */
    private void setupDateSorter() {
        sorter.setComparator(DATE_COLUMN_INDEX, new Comparator<String>() {
            private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            
            @Override
            public int compare(String dateStr1, String dateStr2) {
                try {
                    Date date1 = formatter.parse(dateStr1);
                    Date date2 = formatter.parse(dateStr2);
                    // Sắp xếp ngày
                    return date1.compareTo(date2); 
                } catch (ParseException e) {
                    // Nếu lỗi, sắp xếp theo chuỗi
                    return dateStr1.compareTo(dateStr2); 
                }
            }
        });
    }
    
    /**
     * Sắp xếp mặc định: Creation Date, Mới nhất trước (DESCENDING)
     */
    private void setupDefaultSort() {
        // Áp dụng sắp xếp mặc định (Mới nhất trước)
        sorter.setSortKeys(List.of(new SortKey(DATE_COLUMN_INDEX, SortOrder.DESCENDING)));
    }


    private DefaultTableModel createTableModel() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        // Tên cột đã được Việt hóa
        model.addColumn("STT");
        model.addColumn("Mã HĐ");
        model.addColumn("Nhân Viên Lập");
        model.addColumn("Tên Khách Hàng");
        model.addColumn("SĐT Khách");
        model.addColumn("Ngày Lập"); // Creation Date
        model.addColumn("SL Mặt Hàng");
        model.addColumn("Tổng Tiền");
        return model;
    }
    
    private void customizeTable(JTable table) {
        table.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        table.setForeground(Color.BLACK);
        table.setBackground(new Color(241, 211, 178));

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setBackground(new Color(241, 211, 178));
        tableHeader.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        tableHeader.setDefaultRenderer(new CustomTableHeaderRenderer());

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new CustomTableCellRenderer());
        }
        
        TableColumn colSTT = table.getColumnModel().getColumn(0);
        colSTT.setPreferredWidth(30);
        colSTT.setMinWidth(30);

        TableColumn colMaHD = table.getColumnModel().getColumn(1);
        colMaHD.setPreferredWidth(80);
        colMaHD.setMinWidth(80);

        TableColumn colNgayLap = table.getColumnModel().getColumn(5);
        colNgayLap.setPreferredWidth(120);
        colNgayLap.setMinWidth(120);
    }
    
    /**
     * Helper method to create styled JTextFields for date input with placeholder logic.
     */
    private JTextField createDateInputField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setPreferredSize(new Dimension(125, 25)); // Đã tăng kích thước
        field.setBackground(new Color(241, 211, 178));
        field.setBorder(BorderFactory.createLineBorder(new Color(79, 92, 133)));
        field.setForeground(Color.GRAY);
        field.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals(placeholder)) { 
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder); 
                    field.setForeground(Color.GRAY);
                }
            }
        });
        
        // Dùng ENTER để kích hoạt lọc
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    applyFilter(); 
                }
            }
        });
        return field;
    }

    private JPanel createTopControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setOpaque(true);
        controlPanel.setBackground(Color.white);
        controlPanel.setLayout(new BorderLayout());
        controlPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); 

        // --- WEST PANEL: Keyword Search ---
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftPanel.setOpaque(false);
        

        JLabel findLabel = new JLabel("Tìm Kiếm:"); 
        findLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        findLabel.setForeground(Color.BLACK);
        findLabel.setPreferredSize(new Dimension(110, 25)); 
        leftPanel.add(findLabel);

        // Đã đổi: "Invoice ID or Customer Phone No." -> "Mã HĐ hoặc SĐT Khách"
        searchInput = new JTextField("Mã HĐ hoặc SĐT Khách"); 
        searchInput.setPreferredSize(new Dimension(180, 25)); 
        searchInput.setBackground(new Color(241, 211, 178));
        searchInput.setBorder(BorderFactory.createLineBorder(new Color(79, 92, 133)));
        searchInput.setForeground(Color.GRAY);
        searchInput.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        
        searchInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchInput.getText().equals("Mã HĐ hoặc SĐT Khách")) { 
                    searchInput.setText("");
                    searchInput.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchInput.getText().isEmpty()) {
                    searchInput.setText("Mã HĐ hoặc SĐT Khách"); 
                    searchInput.setForeground(Color.GRAY);
                }
                requestFocusInWindow();
            }
        });
        
        searchInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    applyFilter(); 
                }
            }
        });
        leftPanel.add(searchInput);

        // Đã đổi: "Search" -> "Tìm"
        JButton findButton = new JButton("Tìm");
        findButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        findButton.setPreferredSize(new Dimension(80, 25));
        findButton.setForeground(Color.BLACK);
        findButton.setBackground(new Color(241, 211, 178));
        findButton.addActionListener(e -> applyFilter()); 
        leftPanel.add(findButton);
        
        controlPanel.add(leftPanel, BorderLayout.WEST);

        // --- CENTER PANEL: Date Filtering ---
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        centerPanel.setOpaque(false);

        // Đã đổi: "Sort by Date:" -> "Lọc Theo Ngày:"
        JLabel dateFilterLabel = new JLabel("Lọc Theo Ngày:");
        dateFilterLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        dateFilterLabel.setForeground(Color.BLACK);
        centerPanel.add(dateFilterLabel);

        // Đã đổi: "From Date (dd/MM/yyyy)" -> "Từ Ngày (dd/MM/yyyy)"
        fromDateInput = createDateInputField("Từ Ngày (dd/MM/yyyy)");
        // Kích thước đã được chỉnh trong createDateInputField
        centerPanel.add(fromDateInput);

        // Đã đổi: "to" -> "đến"
        JLabel toLabel = new JLabel("đến");
        toLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        centerPanel.add(toLabel);

        // Đã đổi: "To Date (dd/MM/yyyy)" -> "Đến Ngày (dd/MM/yyyy)"
        toDateInput = createDateInputField("Đến Ngày (dd/MM/yyyy)");
        // Kích thước đã được chỉnh trong createDateInputField
        centerPanel.add(toDateInput);

        // Đã đổi: "Sort" -> "Lọc"
        JButton filterDateButton = new JButton("Lọc");
        filterDateButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        filterDateButton.setPreferredSize(new Dimension(70, 25)); // Đã tăng kích thước
        filterDateButton.setForeground(Color.BLACK);
        filterDateButton.setBackground(new Color(241, 211, 178));
        filterDateButton.addActionListener(e -> applyFilter()); 
        centerPanel.add(filterDateButton);

        controlPanel.add(centerPanel, BorderLayout.CENTER);
        
        // --- EAST PANEL: Chức năng ---
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0)); 
        rightPanel.setOpaque(false);
        
        // --- Nút chức năng ---
        // Đã đổi: "Details" -> "Chi Tiết"
        JButton invoicesButton = new JButton("Chi Tiết");
        invoicesButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        invoicesButton.setForeground(Color.BLACK);
        invoicesButton.setBackground(new Color(241, 211, 178));
        rightPanel.add(invoicesButton);

        // Đã đổi: "Export" -> "Xuất File"
        JButton exportButton = new JButton("Xuất File");
        exportButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        exportButton.setForeground(Color.BLACK);
        exportButton.setBackground(new Color(241, 211, 178));
        rightPanel.add(exportButton);
        
        JButton infoButton = new JButton("i");
        infoButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.BOLD, 14));
        infoButton.setPreferredSize(new Dimension(30, 25));
        infoButton.setForeground(Color.BLACK);
        infoButton.setBackground(new Color(241, 211, 178));
        infoButton.setBorder(BorderFactory.createLineBorder(new Color(79, 92, 133)));
        infoButton.addActionListener(e -> showInfoMessage());
        rightPanel.add(infoButton);

        controlPanel.add(rightPanel, BorderLayout.EAST);
        
        return controlPanel;
    }
    
    // --- Các phương thức xử lý sự kiện ---

    private void loadInitialData() {
        productTableModel.setRowCount(0);
        for (Object[] row : initialData) {
            productTableModel.addRow(row);
        }
    }
    
    /**
     * Áp dụng bộ lọc kết hợp giữa từ khóa (Invoice ID/Phone No.) và khoảng ngày.
     */
    private void applyFilter() { 
        String keyword = searchInput.getText().trim();
        String fromDateStr = fromDateInput.getText().trim();
        String toDateStr = toDateInput.getText().trim();

        // Đã cập nhật giá trị Placeholder tiếng Việt
        final String keywordPlaceholder = "Mã HĐ hoặc SĐT Khách"; 
        final String datePlaceholder = "dd/MM/yyyy";
        
        // Kiểm tra trạng thái kích hoạt của từng bộ lọc
        final boolean isKeywordActive = !(keyword.equals(keywordPlaceholder) || keyword.isEmpty());
        final boolean isFromDateActive = !(fromDateStr.contains("Từ Ngày") || fromDateStr.isEmpty());
        final boolean isToDateActive = !(toDateStr.contains("Đến Ngày") || toDateStr.isEmpty());
        // Lọc ngày CHỈ được kích hoạt khi CẢ 2 ô ngày đều được nhập
        final boolean isDateFilterActive = isFromDateActive && isToDateActive; 

        if (!isKeywordActive && !isDateFilterActive) {
            sorter.setRowFilter(null);
            return;
        }
        
        if ((isFromDateActive && !isToDateActive) || (!isFromDateActive && isToDateActive)) {
              // Đã đổi thông báo lỗi sang tiếng Việt
              JOptionPane.showMessageDialog(this, "Vui lòng nhập cả Ngày Bắt Đầu và Ngày Kết Thúc để lọc.", "Lỗi Lọc Dữ Liệu", JOptionPane.ERROR_MESSAGE);
              return;
        }


        // Khai báo formatter cho cột (có giờ) và cho input (chỉ ngày)
        final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        
        Date filterFromDate = null;
        Date filterToDate = null;

        if (isDateFilterActive) {
            try {
                // Parse From Date: Lấy ngày bắt đầu, thời gian 00:00:00
                Date startDay = dateFormatter.parse(fromDateStr);
                filterFromDate = startDay; 

                // Parse To Date: Lấy ngày kết thúc, sau đó cộng thêm 1 ngày
                Date endDay = dateFormatter.parse(toDateStr);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(endDay);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                filterToDate = calendar.getTime(); 
                
                // Kiểm tra logic ngày: From Date không được sau To Date
                if (filterFromDate.after(endDay)) {
                      // Đã đổi thông báo lỗi sang tiếng Việt
                      JOptionPane.showMessageDialog(this, "Ngày Bắt Đầu không được sau Ngày Kết Thúc.", "Lỗi Lọc Dữ Liệu", JOptionPane.ERROR_MESSAGE);
                      sorter.setRowFilter(null);
                      return;
                }

            } catch (ParseException e) {
                 // Đã đổi thông báo lỗi sang tiếng Việt
                 JOptionPane.showMessageDialog(this, "Lỗi định dạng ngày. Vui lòng sử dụng dd/MM/yyyy (ví dụ: 25/10/2025).", "Lỗi Lọc Dữ Liệu", JOptionPane.ERROR_MESSAGE);
                 sorter.setRowFilter(null);
                 return;
            }
        }
        
        final Date finalFilterFromDate = filterFromDate;
        final Date finalFilterToDate = filterToDate;

        RowFilter<Object, Object> combinedFilter = new RowFilter<Object, Object>() {
            public boolean include(Entry<?, ?> entry) {
                
                // --- 1. Keyword Filter Logic (Áp dụng AND) ---
                if (isKeywordActive) {
                    String maHD = entry.getStringValue(1).toLowerCase(); 
                    String sdt = entry.getStringValue(4).toLowerCase(); 
                    String k = keyword.toLowerCase();
                    if (!(maHD.contains(k) || sdt.contains(k))) {
                        return false; // Không khớp từ khóa -> Bỏ qua hàng
                    }
                }
                
                // --- 2. Date Filter Logic (Áp dụng AND) ---
                if (isDateFilterActive) {
                    String dateStr = entry.getStringValue(DATE_COLUMN_INDEX);
                    try {
                        // Phân tích cú pháp ngày giờ đầy đủ từ cột bảng
                        Date entryDate = dateTimeFormatter.parse(dateStr); 
                        
                        // Kiểm tra entryDate >= filterFromDate
                        boolean isAfterOrEqualFrom = !entryDate.before(finalFilterFromDate); 
                                                                    
                        // Kiểm tra entryDate < finalFilterToDate
                        boolean isBeforeTo = entryDate.before(finalFilterToDate); 

                        if (!(isAfterOrEqualFrom && isBeforeTo)) {
                            return false; // Không nằm trong khoảng ngày -> Bỏ qua hàng
                        }

                    } catch (ParseException e) {
                        // Bỏ qua nếu ngày trong bảng không đúng định dạng
                        System.err.println("Error parsing date in table row: " + dateStr);
                        return false; 
                    }
                }

                return true; // Khớp tất cả các bộ lọc đã kích hoạt
            }
        };
        
        sorter.setRowFilter(combinedFilter);
        requestFocusInWindow(); 
    }
    
    /**
     * Xử lý chức năng làm mới dữ liệu (Refresh) - Gán cho F5
     */
    private void refreshData() {
        // 1. Reset bộ lọc tìm kiếm
        sorter.setRowFilter(null);
        
        // 2. Xóa tất cả sắp xếp và áp dụng sắp xếp mặc định (Mới nhất trước)
        sorter.setSortKeys(null);
        setupDefaultSort(); 

        // 3. Reset ô tìm kiếm
        // Đã đổi: "Invoice ID or Customer Phone No." -> "Mã HĐ hoặc SĐT Khách"
        searchInput.setText("Mã HĐ hoặc SĐT Khách"); 
        searchInput.setForeground(Color.GRAY);
        
        // 4. Reset ô lọc ngày
        // Đã đổi: "From Date (dd/MM/yyyy)" -> "Từ Ngày (dd/MM/yyyy)"
        fromDateInput.setText("Từ Ngày (dd/MM/yyyy)");
        fromDateInput.setForeground(Color.GRAY);
        // Đã đổi: "To Date (dd/MM/yyyy)" -> "Đến Ngày (dd/MM/yyyy)"
        toDateInput.setText("Đến Ngày (dd/MM/yyyy)");
        toDateInput.setForeground(Color.GRAY);
        
        // 5. Bỏ chọn hàng trong JTable 
        productTable.clearSelection(); 
        
        // 6. Load lại dữ liệu ban đầu
        loadInitialData();
        
        // 7. Trả lại focus cho JPanel
        requestFocusInWindow();
        
        // 8. Hiển thị thông báo thành công
        // Đã đổi thông báo sang tiếng Việt
        JOptionPane.showMessageDialog(this, "Dữ liệu đã được làm mới thành công. (Phím tắt F5)", "Làm Mới Thành Công", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showInfoMessage() {
        // Đã Việt hóa toàn bộ nội dung trong showInfoMessage
        String info = "<html>"
                      + "<h3>Tổng Quan: Quản Lý Hóa Đơn</h3>" 
                      + "<p>Trang này cho phép bạn quản lý, tìm kiếm và xuất các hóa đơn đã tạo.</p>" 
                      + "<hr>"
                      + "<h4>Các Tính Năng Chính:</h4>" 
                      + "<ul>"
                      + "<li><b>Tìm Kiếm:</b> Tra cứu theo Mã Hóa Đơn hoặc Số Điện Thoại Khách Hàng.</li>" 
                      + "<li><b>Lọc Theo Ngày:</b> Nhập khoảng ngày (dd/MM/yyyy) để thu hẹp kết quả, sau đó nhấn 'Lọc'.</li>" 
                      + "<li><b>Sắp Xếp Mặc Định:</b> Tự động sắp xếp theo Ngày Lập (Mới nhất trước).</li>" 
                      + "<li><b>Chi Tiết/Xuất:</b> Sử dụng nút 'Chi Tiết' và 'Xuất File' cho các hóa đơn đã chọn.</li>" 
                      + "</ul>"
                      + "<h4>Phím Tắt:</h4>" 
                      + "<ul>"
                      + "<li>Nhấn <b>ENTER</b> trong bất kỳ ô tìm kiếm/lọc nào để áp dụng bộ lọc.</li>" 
                      + "<li>Nhấn <b>F5</b> để làm mới tất cả dữ liệu và xóa bộ lọc.</li>" 
                      + "</ul>"
                      + "</html>";
        
        JOptionPane.showMessageDialog(this, info, "Hướng Dẫn và Phím Tắt", JOptionPane.INFORMATION_MESSAGE);
        
        requestFocusInWindow();
    }
}