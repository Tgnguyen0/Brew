package app.GUI;

import app.Object.Bill;
import app.InitFont.CustomFont;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistoryDialog extends JDialog {

    private final Color PRIMARY_COLOR = new Color(178, 74, 55);
    private final Color SECONDARY_COLOR = new Color(241, 211, 178);
    private final Color TEXT_COLOR = new Color(21, 24, 48);
    private final Color SELECTION_COLOR = new Color(255, 170, 100); // Màu cam nhạt hơn, dễ nhìn hơn khi chọn

    private final Font ROBOTO_REGULAR_12;
    private final Font ROBOTO_BOLD_12;
    private CustomFont customFont = new CustomFont();

    private static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("#,### VNĐ");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public HistoryDialog(Window owner, String customerId, String fullName, String phoneNumber, List<Bill> bills) {
        super(owner, "Lịch sử mua hàng - " + fullName, ModalityType.APPLICATION_MODAL);

        List<Font> robotoFonts = customFont.getRobotoFonts();
        ROBOTO_REGULAR_12 = robotoFonts.size() > 0 ? robotoFonts.get(0).deriveFont(Font.PLAIN, 12f) : new Font("SansSerif", Font.PLAIN, 12);
        ROBOTO_BOLD_12 = robotoFonts.size() > 1 ? robotoFonts.get(1).deriveFont(Font.BOLD, 12f) : new Font("SansSerif", Font.BOLD, 12);

        setSize(650, 550);
        setLayout(new BorderLayout());
        setLocationRelativeTo(owner);
        setBackground(SECONDARY_COLOR);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        headerPanel.setBackground(PRIMARY_COLOR);

        JLabel titleLabel = new JLabel("LỊCH SỬ MUA HÀNG");
        titleLabel.setFont(ROBOTO_BOLD_12.deriveFont(Font.BOLD, 20f));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel infoLabel = new JLabel("<html><b>Khách hàng:</b> " + fullName + " | <b>SĐT:</b> " + phoneNumber + "</html>");
        infoLabel.setFont(ROBOTO_REGULAR_12.deriveFont(Font.PLAIN, 14f));
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(infoLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Bill List (Content)
        JList<Bill> billList = new JList<>(bills.toArray(new Bill[0]));
        billList.setFont(ROBOTO_REGULAR_12.deriveFont(Font.PLAIN, 14f));
        billList.setBackground(SECONDARY_COLOR);

        // Sử dụng Renderer đã điều chỉnh
        billList.setCellRenderer(new BillListRenderer());
        // Tăng chiều cao của mỗi item
        billList.setFixedCellHeight(70); 

        JScrollPane scrollPane = new JScrollPane(billList);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1));
        scrollPane.getViewport().setBackground(SECONDARY_COLOR);

        JPanel listContainer = new JPanel(new BorderLayout());
        listContainer.setBorder(new EmptyBorder(10, 20, 10, 20));
        listContainer.setBackground(SECONDARY_COLOR);
        listContainer.add(scrollPane, BorderLayout.CENTER);

        add(listContainer, BorderLayout.CENTER);

        if (bills.isEmpty()) {
             JOptionPane.showMessageDialog(this, "Khách hàng " + fullName + " chưa có hóa đơn nào.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    class BillListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof Bill) {
                Bill bill = (Bill) value;
                
                String billDateTime = (bill.getDateCreated() != null && bill.getHourIn() != null) 
                                      ? bill.getHourIn().toLocalDateTime().format(DATE_TIME_FORMATTER) 
                                      : "N/A";
                
                String totalFormatted = CURRENCY_FORMAT.format(bill.getTotal());
                
                // --- Bổ sung STT (index + 1) ---
                int stt = index + 1;

                // Sử dụng HTML và margin/padding để tạo khoảng cách giữa các dòng
                String text = String.format("<html>" +
                    "<div style='padding: 5px 0;'>" + // Thêm padding trên dưới
                    "<b>%d. Mã HĐ: %s</b> | Thời gian: %s<br>" + // STT và dòng 1
                    "&nbsp;&nbsp;&nbsp;&nbsp;Tổng tiền: <font color='#%06x'>%s</font> | SL Mặt hàng: %d | Trạng thái: %s" + // Dòng 2 (có thụt lề)
                    "</div></html>",
                    stt,
                    bill.getBillId(),
                    billDateTime,
                    PRIMARY_COLOR.getRGB() & 0xFFFFFF,
                    totalFormatted,
                    bill.getQuantityOfItems(),
                    bill.getStatus()
                );
                
                label.setText(text);
                label.setFont(ROBOTO_REGULAR_12);
                
                // Màu nền khi chọn
                label.setBackground(isSelected ? SELECTION_COLOR : SECONDARY_COLOR);
                label.setForeground(TEXT_COLOR);
                if(isSelected) {
                    label.setForeground(TEXT_COLOR.darker());
                }

                // Thiết lập border dưới để phân tách các hóa đơn
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(170, 170, 170)));
            }
            return label;
        }
    }
}