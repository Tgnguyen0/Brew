package app.GUI;

import app.Components.CustomTableCellRenderer;
import app.Components.CustomTableHeaderRenderer;
import app.InitFont.CustomFont;
import app.DAO.DAO_Bill;
import app.Object.Bill;
import app.Utils.PDF_Exporter;
import java.io.IOException;

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
    private JTextField fromDateInput;
    private JTextField toDateInput;
    private TableRowSorter<DefaultTableModel> sorter;
    private DAO_Bill billDAO = new DAO_Bill();

    private static final int DATE_COLUMN_INDEX = 1;
    private static final int DATE_TIME_COLUMN_INDEX = 1;
    private static final int SOTIEN_COLUMN_INDEX = 6;

    public ReceiptPage() {
        setPreferredSize(new Dimension(1100, 500));
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Color.white);

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

        JPanel emptyL = new JPanel();
        emptyL.setPreferredSize(new Dimension(16, 500));
        emptyL.setOpaque(false);
        add(emptyL, BorderLayout.WEST);

        JPanel emptyR = new JPanel();
        emptyR.setPreferredSize(new Dimension(16, 500));
        emptyR.setOpaque(false);
        add(emptyR, BorderLayout.EAST);

        add(createReceiptPanel(), BorderLayout.CENTER);

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

        productTableModel = createTableModel();
        loadInitialData();
        productTable = new JTable(productTableModel);

        sorter = new TableRowSorter<>(productTableModel);
        productTable.setRowSorter(sorter);

        setupDateSorter();

        customizeTable(productTable);

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.getViewport().setBackground(new Color(241, 211, 178));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(79, 92, 133)));

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setupDefaultSort();

        JPanel topControlPanel = createTopControlPanel();
        mainPanel.add(topControlPanel, BorderLayout.NORTH);

        return mainPanel;
    }

    private void setupDateSorter() {
        sorter.setComparator(DATE_TIME_COLUMN_INDEX, new Comparator<String>() {
            private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            @Override
            public int compare(String dateStr1, String dateStr2) {
                try {
                    Date date1 = formatter.parse(dateStr1);
                    Date date2 = formatter.parse(dateStr2);
                    return date1.compareTo(date2);
                } catch (ParseException e) {
                    return dateStr1.compareTo(dateStr2);
                }
            }
        });

        sorter.setComparator(SOTIEN_COLUMN_INDEX, new Comparator<String>() {
            @Override
            public int compare(String moneyStr1, String moneyStr2) {
                try {
                    double val1 = Double.parseDouble(moneyStr1.replaceAll("[^\\d.]", ""));
                    double val2 = Double.parseDouble(moneyStr2.replaceAll("[^\\d.]", ""));
                    return Double.compare(val1, val2);
                } catch (NumberFormatException e) {
                    return moneyStr1.compareTo(moneyStr2);
                }
            }
        });
    }

    private void setupDefaultSort() {
        sorter.setSortKeys(List.of(new SortKey(DATE_TIME_COLUMN_INDEX, SortOrder.DESCENDING)));
    }


    private DefaultTableModel createTableModel() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == SOTIEN_COLUMN_INDEX) {
                    return String.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        model.addColumn("ID");
        model.addColumn("Ngày khởi tạo");
        model.addColumn("Giờ vào");
        model.addColumn("Giờ out");
        model.addColumn("Tên khách hàng");
        model.addColumn("SĐT");
        model.addColumn("Tổng cộng");
        model.addColumn("Trạng thái");
        model.addColumn("Nhân viên phục vụ");
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

        TableColumn colID = table.getColumnModel().getColumn(0);
        colID.setPreferredWidth(80);
        colID.setMinWidth(80);

        TableColumn colNgayLap = table.getColumnModel().getColumn(1);
        colNgayLap.setPreferredWidth(100);
        colNgayLap.setMinWidth(100);

        TableColumn colSDT = table.getColumnModel().getColumn(5);
        colSDT.setPreferredWidth(100);
        colSDT.setMinWidth(100);

        TableColumn colTongCong = table.getColumnModel().getColumn(6);
        colTongCong.setPreferredWidth(90);
        colTongCong.setMinWidth(90);

        TableColumn colTrangThai = table.getColumnModel().getColumn(7);
        colTrangThai.setPreferredWidth(90);
        colTrangThai.setMinWidth(90);
    }

    private JTextField createDateInputField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setPreferredSize(new Dimension(125, 25));
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

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftPanel.setOpaque(false);


        JLabel findLabel = new JLabel("Tìm Kiếm:");
        findLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        findLabel.setForeground(Color.BLACK);
        findLabel.setPreferredSize(new Dimension(110, 25));
        leftPanel.add(findLabel);

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

        JButton findButton = new JButton("Tìm");
        findButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        findButton.setPreferredSize(new Dimension(80, 25));
        findButton.setForeground(Color.BLACK);
        findButton.setBackground(new Color(241, 211, 178));
        findButton.addActionListener(e -> applyFilter());
        leftPanel.add(findButton);

        controlPanel.add(leftPanel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        centerPanel.setOpaque(false);

        JLabel dateFilterLabel = new JLabel("Lọc Theo Ngày:");
        dateFilterLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        dateFilterLabel.setForeground(Color.BLACK);
        centerPanel.add(dateFilterLabel);

        fromDateInput = createDateInputField("Từ Ngày (dd/MM/yyyy)");
        centerPanel.add(fromDateInput);

        JLabel toLabel = new JLabel("đến");
        toLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        centerPanel.add(toLabel);

        toDateInput = createDateInputField("Đến Ngày (dd/MM/yyyy)");
        centerPanel.add(toDateInput);

        JButton filterDateButton = new JButton("Lọc");
        filterDateButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        filterDateButton.setPreferredSize(new Dimension(70, 25));
        filterDateButton.setForeground(Color.BLACK);
        filterDateButton.setBackground(new Color(241, 211, 178));
        filterDateButton.addActionListener(e -> applyFilter());
        centerPanel.add(filterDateButton);

        controlPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);

        JButton detailButton = new JButton("Chi Tiết");
        detailButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        detailButton.setForeground(Color.BLACK);
        detailButton.setBackground(new Color(241, 211, 178));
        rightPanel.add(detailButton);
        detailButton.addActionListener(e -> showBillDetail());
        rightPanel.add(detailButton);

        JButton exportButton = new JButton("Xuất File");
        exportButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        exportButton.setForeground(Color.BLACK);
        exportButton.setBackground(new Color(241, 211, 178));
        exportButton.addActionListener(e -> exportFileAction());
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

    private void loadInitialData() {
        productTableModel.setRowCount(0);
        List<Bill> billList = billDAO.selectAll();

        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        for (Bill bill : billList) {
            String dateStr = bill.getDateCreated() != null ? dateFormatter.format(java.sql.Date.valueOf(bill.getDateCreated())) : "";
            String hourInStr = bill.getHourIn() != null ? timeFormatter.format(bill.getHourIn()) : "";
            String hourOutStr = bill.getHourOut() != null ? timeFormatter.format(bill.getHourOut()) : "";

            String totalStr = String.format("%,.0f", bill.getTotal());
            String name;
            if (bill.getEmployee().getFirstName() == null && bill.getEmployee().getLastName() == null) {
                name = bill.getEmployee().getId();
            } else {
                name = bill.getEmployee().getFirstName() + " " + bill.getEmployee().getLastName();
            }


            Object[] row = new Object[] {
                    bill.getBillId(),
                    dateStr,
                    hourInStr,
                    hourOutStr,
                    bill.getCustomer().getFullName() != null ? bill.getCustomer().getFullName() : "Khách lẻ",
                    bill.getPhoneNumber(),
                    totalStr,
                    bill.getStatus(),
                    name
            };
            productTableModel.addRow(row);
        }
    }

    private void applyFilter() {
        String keyword = searchInput.getText().trim();
        String fromDateStr = fromDateInput.getText().trim();
        String toDateStr = toDateInput.getText().trim();

        final String keywordPlaceholder = "Mã HĐ hoặc SĐT Khách";

        final boolean isKeywordActive = !(keyword.equals(keywordPlaceholder) || keyword.isEmpty());
        final boolean isFromDateActive = !(fromDateStr.contains("Từ Ngày") || fromDateStr.isEmpty());
        final boolean isToDateActive = !(toDateStr.contains("Đến Ngày") || toDateStr.isEmpty());
        final boolean isDateFilterActive = isFromDateActive && isToDateActive;

        if (!isKeywordActive && !isDateFilterActive) {
            sorter.setRowFilter(null);
            return;
        }

        if ((isFromDateActive && !isToDateActive) || (!isFromDateActive && isToDateActive)) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập cả Ngày Bắt Đầu và Ngày Kết Thúc để lọc.", "Lỗi Lọc Dữ Liệu", JOptionPane.ERROR_MESSAGE);
            return;
        }

        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        Date filterFromDate = null;
        Date filterToDate = null;

        if (isDateFilterActive) {
            try {
                Date startDay = dateFormatter.parse(fromDateStr);
                filterFromDate = startDay;

                Date endDay = dateFormatter.parse(toDateStr);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(endDay);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                filterToDate = calendar.getTime();

                if (filterFromDate.after(endDay)) {
                    JOptionPane.showMessageDialog(this, "Ngày Bắt Đầu không được sau Ngày Kết Thúc.", "Lỗi Lọc Dữ Liệu", JOptionPane.ERROR_MESSAGE);
                    sorter.setRowFilter(null);
                    return;
                }

            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Lỗi định dạng ngày. Vui lòng sử dụng dd/MM/yyyy (ví dụ: 25/10/2025).", "Lỗi Lọc Dữ Liệu", JOptionPane.ERROR_MESSAGE);
                sorter.setRowFilter(null);
                return;
            }
        }

        final Date finalFilterFromDate = filterFromDate;
        final Date finalFilterToDate = filterToDate;

        RowFilter<Object, Object> combinedFilter = new RowFilter<Object, Object>() {
            public boolean include(Entry<?, ?> entry) {

                if (isKeywordActive) {
                    String maHD = entry.getStringValue(0).toLowerCase();
                    String sdt = entry.getStringValue(5).toLowerCase();
                    String customerName = entry.getStringValue(4).toLowerCase();
                    String employeeName = entry.getStringValue(8).toLowerCase();
                    String k = keyword.toLowerCase();
                    if (!(maHD.contains(k) || sdt.contains(k) || customerName.contains(k) || employeeName.contains(k))) {
                        return false;
                    }
                }

                if (isDateFilterActive) {
                    String dateStr = entry.getStringValue(DATE_TIME_COLUMN_INDEX);
                    try {
                        Date entryDate = dateFormatter.parse(dateStr);

                        boolean isAfterOrEqualFrom = !entryDate.before(finalFilterFromDate);

                        boolean isBeforeTo = entryDate.before(finalFilterToDate);

                        if (!(isAfterOrEqualFrom && isBeforeTo)) {
                            return false;
                        }

                    } catch (ParseException e) {
                        System.err.println("Error parsing date in table row: " + dateStr);
                        return false;
                    }
                }

                return true;
            }
        };

        sorter.setRowFilter(combinedFilter);
        requestFocusInWindow();
    }

    private void refreshData() {
        sorter.setRowFilter(null);

        sorter.setSortKeys(null);
        setupDefaultSort();

        searchInput.setText("Mã HĐ hoặc SĐT Khách");
        searchInput.setForeground(Color.GRAY);

        fromDateInput.setText("Từ Ngày (dd/MM/yyyy)");
        fromDateInput.setForeground(Color.GRAY);
        toDateInput.setText("Đến Ngày (dd/MM/yyyy)");
        toDateInput.setForeground(Color.GRAY);

        productTable.clearSelection();

        loadInitialData();

        requestFocusInWindow();

        JOptionPane.showMessageDialog(this, "Dữ liệu đã được làm mới thành công. (Phím tắt F5)", "Làm Mới Thành Công", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showBillDetail() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để xem chi tiết.", "Chưa Chọn Hóa Đơn", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = productTable.convertRowIndexToModel(selectedRow);
        String billId = productTableModel.getValueAt(modelRow, 0).toString();

        // Gọi phương thức DAO mới
        Bill selectedBill = billDAO.getBillById(billId);

        if (selectedBill != null) {
            BillDetailPage detailPage = new BillDetailPage(selectedBill);
            detailPage.showDetailWindow();
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy chi tiết hóa đơn ID: " + billId, "Lỗi Dữ Liệu", JOptionPane.ERROR_MESSAGE);
        }
    }


    //Hàm xử lí Export PDF
    private void exportFileAction() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để xuất file.", "Chưa Chọn Hóa Đơn", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Chuyển đổi index từ view sang model (quan trọng nếu bảng đang được sắp xếp/lọc)
        int modelRow = productTable.convertRowIndexToModel(selectedRow);
        String selectedBillId = productTableModel.getValueAt(modelRow, 0).toString();

        // Hộp thoại xác nhận
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xuất hóa đơn " + selectedBillId + " thành file PDF?",
                "Xác nhận Xuất File",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return; // Hủy bỏ
        }

        try {
            // Lấy đối tượng Bill hoàn chỉnh từ CSDL (đã được cập nhật trong DAO_Bill ở trên)
            Bill billToExport = billDAO.getBillById(selectedBillId);

            if (billToExport == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu hóa đơn ID: " + selectedBillId, "Lỗi Dữ Liệu", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PDF_Exporter exporter = new PDF_Exporter();
            String filePath = exporter.exportBillToPDF(billToExport);

            // Thông báo thành công
            JOptionPane.showMessageDialog(this,
                    "Xuất file PDF thành công! Hóa đơn " + selectedBillId + " đã được lưu tại:\n" + filePath,
                    "Xuất Hóa Đơn Thành Công",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi xuất file PDF. Vui lòng kiểm tra đã thêm đủ các file JAR iText và font RobotoMono trong thư mục 'font' hay chưa.",
                    "Lỗi Xuất File (Font/JAR)",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi hệ thống: " + e.getMessage(), "Lỗi Hệ Thống", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    //Hiển thị information của trang
    private void showInfoMessage() {
        String info = "<html>"
                + "<h3>Tổng Quan: Quản Lý Hóa Đơn</h3>"
                + "<p>Trang này hiển thị <b>25 Hóa Đơn Gần Nhất</b> để tối ưu hiệu suất.</p>"
                + "<hr>"
                + "<h4>Các Tính Năng Chính:</h4>"
                + "<ul>"
                + "<li><b>Tìm Kiếm:</b> Tra cứu theo Mã Hóa Đơn, Tên Khách Hàng, SĐT Khách Hàng hoặc Tên Nhân Viên.</li>"
                + "<li><b>Lọc Theo Ngày:</b> Nhập khoảng ngày (dd/MM/yyyy) để thu hẹp kết quả, sau đó nhấn 'Lọc'.</li>"
                + "<li><b>Sắp Xếp Mặc Định:</b> Tự động sắp xếp theo Ngày Khởi Tạo (Mới nhất trước).</li>"
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