package app.GUI;

import app.InitFont.CustomFont;
import app.Object.Bill;
import app.Object.BillDetail;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class BillDetailPage extends JDialog {
    private CustomFont customFont = new CustomFont();
    private Bill bill;
    
    // Màu đỏ đất mà bạn đã cung cấp
    private final Color PRIMARY_COLOR = new Color(178, 74, 55); 
    private final Color SECONDARY_COLOR = new Color(241, 211, 178); 

    // Định dạng ngày giờ
    private final SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    public BillDetailPage(Bill bill) {
        this.bill = bill;
        
        setTitle("Chi Tiết Hóa Đơn ID: " + bill.getBillId());
        setSize(850, 800); 
        setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); 
        
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createDetailScrollPane(), BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout()); 
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Tiêu đề chính
        JLabel titleLabel = new JLabel("CHI TIẾT HÓA ĐƠN", SwingConstants.CENTER);
        titleLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

 
        JPanel topRightPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        topRightPanel.setOpaque(false); 
        topRightPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); 
        
        Font infoFont = customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12);
        
        // Ngày khởi tạo
        String dateStr = bill.getDateCreated() != null ? dateFormatter.format(java.sql.Date.valueOf(bill.getDateCreated())) : "N/A";
        JLabel dateLabel = new JLabel("Ngày: " + dateStr, SwingConstants.RIGHT);
        dateLabel.setFont(infoFont);
        dateLabel.setForeground(Color.WHITE);
        topRightPanel.add(dateLabel);

        // Nhân viên phụ trách
        String employeeName = bill.getEmployeeName() != null ? bill.getEmployeeName() : "NV Lẻ (ID: " + bill.getEmployeeId() + ")";
        JLabel employeeLabel = new JLabel("NV: " + employeeName, SwingConstants.RIGHT);
        employeeLabel.setFont(infoFont);
        employeeLabel.setForeground(Color.WHITE);
        topRightPanel.add(employeeLabel);
        
        headerPanel.add(topRightPanel, BorderLayout.EAST);

        return headerPanel;
    }
    
    private JPanel createSummaryPanel() {
        JPanel summaryPanel = new JPanel(new GridBagLayout());
        summaryPanel.setBackground(SECONDARY_COLOR);
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15); 
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = customFont.getRobotoFonts().get(0).deriveFont(Font.BOLD, 15);
        Font valueFont = customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 15); 

        int row = 0;
        
        
        addRow(summaryPanel, gbc, row++, "Mã Hóa Đơn:", bill.getBillId(), labelFont, valueFont);
        
       
        String hourInStr = bill.getHourIn() != null ? timeFormatter.format(bill.getHourIn()) : "N/A";
        String hourOutStr = bill.getHourOut() != null ? timeFormatter.format(bill.getHourOut()) : "N/A";
        addRow(summaryPanel, gbc, row++, "Thời Gian:", hourInStr + " - " + hourOutStr, labelFont, valueFont);
        
        
        String customerName = bill.getCustomerName() != null ? bill.getCustomerName() : "Khách lẻ (ID: " + bill.getCustomerId() + ")";
        addRow(summaryPanel, gbc, row++, "Tên Khách Hàng:", customerName, labelFont, valueFont);
        
        
        addRow(summaryPanel, gbc, row++, "Số Điện Thoại:", bill.getPhoneNumber() != null ? bill.getPhoneNumber() : "N/A", labelFont, valueFont);
        
        String totalStr = String.format("%,.0f VNĐ", bill.getTotal()); 
        addRow(summaryPanel, gbc, row++, "TỔNG CỘNG:", totalStr, labelFont.deriveFont(Font.BOLD, 17), valueFont.deriveFont(Font.BOLD, 17));
        addRow(summaryPanel, gbc, row++, "Trạng Thái:", bill.getStatus(), labelFont, valueFont);

        String paymentStr = String.format("%,.0f VNĐ", bill.getCustPayment()); 
        addRow(summaryPanel, gbc, row++, "Số Tiền Khách Trả:", paymentStr, labelFont, valueFont);
        
        return summaryPanel;
    }
    
    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, String valueText, Font labelFont, Font valueFont) {
        gbc.gridy = row;
        
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setForeground(PRIMARY_COLOR.darker()); // Màu đậm hơn một chút
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        panel.add(label, gbc);
        
        JLabel value = new JLabel("<html><b>" + valueText + "</b></html>"); // Giữ in đậm cho giá trị
        value.setFont(valueFont);
        value.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(value, gbc);
    }
    
    private JPanel createProductListPanel() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);
        container.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JLabel productHeader = new JLabel("DANH SÁCH SẢN PHẨM");
        productHeader.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.BOLD, 18)); // Cỡ chữ lớn hơn
        productHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        productHeader.setForeground(PRIMARY_COLOR.darker());
        container.add(productHeader);
        container.add(Box.createVerticalStrut(15));
        
        JPanel productListContent = new JPanel();
        productListContent.setLayout(new BoxLayout(productListContent, BoxLayout.Y_AXIS));
        productListContent.setBackground(Color.WHITE);

        // Thêm tiêu đề cột (Header)
        JPanel titleRow = createProductRow("Tên Sản Phẩm", "Số Lượng", "Phân Loại", "Đơn Giá", "Tổng Cộng", true);
        productListContent.add(titleRow);
        productListContent.add(new JSeparator(SwingConstants.HORIZONTAL));
        
        // Thêm chi tiết từng sản phẩm
        List<BillDetail> details = bill.getDetails(); 
        if (details != null && !details.isEmpty()) {
            for (BillDetail detail : details) {
                String itemName = detail.getItemName() != null ? detail.getItemName() : "Món đã xóa";
                String quantity = String.valueOf(detail.getQuantity());
                String category = detail.getCategory() != null ? detail.getCategory() : "N/A";
                
                String amount = String.format("%,.0f", detail.getAmount());
                String totalPrice = String.format("%,.0f", detail.getTotalPrice());
                
                JPanel detailRow = createProductRow(itemName, quantity, category, amount, totalPrice, false);
                productListContent.add(detailRow);
                productListContent.add(new JSeparator(SwingConstants.HORIZONTAL));
            }
        } else {
             JLabel noProductLabel = new JLabel("Không có sản phẩm nào trong hóa đơn này.");
             noProductLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.ITALIC, 15));
             noProductLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
             noProductLabel.setForeground(Color.GRAY.darker());
             productListContent.add(noProductLabel);
             productListContent.add(Box.createVerticalStrut(20));
        }
        
        JScrollPane scrollPane = new JScrollPane(productListContent);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)); // Border mỏng
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        container.add(scrollPane);
        return container;
    }
    
    private JPanel createProductRow(String name, String quantity, String category, String amount, String totalPrice, boolean isHeader) {
        JPanel rowPanel = new JPanel(new GridLayout(1, 5, 8, 0)); // Tăng khoảng cách giữa các cột
        rowPanel.setBackground(isHeader ? SECONDARY_COLOR.darker().darker() : Color.WHITE); // Header đậm hơn
        rowPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8)); // Tăng padding
        
        Font font = isHeader ? customFont.getRobotoFonts().get(0).deriveFont(Font.BOLD, 13) : customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 13); // Cỡ chữ lớn hơn
        Color textColor = isHeader ? Color.WHITE : Color.BLACK; // Chữ trắng trên header đậm, chữ đen trên nền trắng
        
        JLabel nameLabel = new JLabel(name, SwingConstants.LEFT);
        nameLabel.setFont(font);
        nameLabel.setForeground(textColor);
        
        JLabel quantityLabel = new JLabel(quantity, SwingConstants.CENTER);
        quantityLabel.setFont(font);
        quantityLabel.setForeground(textColor);
        
        JLabel categoryLabel = new JLabel(category, SwingConstants.LEFT);
        categoryLabel.setFont(font);
        categoryLabel.setForeground(textColor);
        
        JLabel amountLabel = new JLabel(amount + (isHeader ? "" : " VNĐ"), SwingConstants.RIGHT);
        amountLabel.setFont(font);
        amountLabel.setForeground(textColor);
        
        JLabel totalLabel = new JLabel(totalPrice + (isHeader ? "" : " VNĐ"), SwingConstants.RIGHT);
        totalLabel.setFont(font.deriveFont(isHeader ? Font.BOLD : Font.BOLD)); // Tổng cộng luôn in đậm
        totalLabel.setForeground(textColor);

        rowPanel.add(nameLabel);
        rowPanel.add(quantityLabel);
        rowPanel.add(categoryLabel);
        rowPanel.add(amountLabel);
        rowPanel.add(totalLabel);

        return rowPanel;
    }

    private JScrollPane createDetailScrollPane() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);
        
        container.add(createSummaryPanel());
        container.add(Box.createVerticalStrut(15)); // Tăng khoảng cách
        container.add(new JSeparator(SwingConstants.HORIZONTAL));
        container.add(Box.createVerticalStrut(15)); // Tăng khoảng cách
        
        container.add(createProductListPanel());
        
        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBorder(null); 
        scrollPane.getVerticalScrollBar().setUnitIncrement(18); // Cuộn mượt hơn
        return scrollPane;
    }
    
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout(10, 0));
        footerPanel.setBackground(PRIMARY_COLOR); 
        footerPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25)); // Tăng padding

        JLabel totalLabel = new JLabel("<html><b style='color: white;'>TỔNG CỘNG (VNĐ): " + String.format("%,.0f</b></html>", bill.getTotal()));
        totalLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.BOLD, 20)); // Cỡ chữ lớn hơn
        totalLabel.setForeground(Color.WHITE);

        JButton closeButton = new JButton("ĐÓNG");
        closeButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.BOLD, 15)); // Cỡ chữ lớn hơn
        closeButton.setBackground(SECONDARY_COLOR); // Nút màu kem nhạt
        closeButton.setForeground(PRIMARY_COLOR.darker()); // Chữ màu đậm hơn
        closeButton.setPreferredSize(new Dimension(150, 40)); // Kích thước nút lớn hơn
        closeButton.addActionListener(e -> dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(closeButton);
        
        footerPanel.add(totalLabel, BorderLayout.WEST); 
        footerPanel.add(buttonPanel, BorderLayout.EAST);

        return footerPanel;
    }

    public void showDetailWindow() {
        this.setVisible(true);
    }
}