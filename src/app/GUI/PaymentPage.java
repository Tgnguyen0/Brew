package app.GUI;

import app.Collections.Collection_BillDetails;
import app.Collections.Collection_Table;
import app.Components.CustomTableCellRenderer;
import app.Components.CustomTableHeaderRenderer;
import app.Components.CustomUpdateCellEditor;
import app.InitFont.CustomFont;
import app.Listener.ActionListener_PaymentPage;
import app.Object.BillDetail;
import app.Object.Employee;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Vector;

public class PaymentPage extends JFrame {
    public CustomFont customFont = new CustomFont();
    public ActionListener_PaymentPage action;
    public Collection_BillDetails collectionBillDetails;
    public Collection_Table collectionTable;
    public Employee employee;
    public DefaultTableModel productTableModel;
    public JTable productTable;
    public JTextField phoneInput;
    public JRadioButton isRegistedRadioButton;
    public JTextField custPaymentField;
    public JLabel changeValueLabel;
    public JButton confirmedButton;
    public JButton exitButton;
    public float customerPayment;
    public float totalBill;
    public float change;

    public PaymentPage(Collection_BillDetails collectionBillDetails, Collection_Table collectionTable, Employee employee) {
        this.collectionBillDetails = collectionBillDetails;
        this.collectionTable = collectionTable;
        this.employee = employee;
        action = new ActionListener_PaymentPage(this);

        setTitle("Thanh Toán");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(new Dimension(800, 620));
        setBackground(Color.white);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JPanel labelPanel = new JPanel();
        labelPanel.setBackground(Color.white);
        labelPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel("Thanh toán");
        label.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 20));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.black);
        labelPanel.add(label);

        this.add(labelPanel, BorderLayout.NORTH);

        initDetailsPanel();
        initPaymentPanel();
    }

    private void initDetailsPanel() {
        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(400, 400));
        right.setLayout(new BoxLayout(right, BoxLayout.X_AXIS));
        right.setBackground(Color.white);
        right.add(Box.createHorizontalStrut(10));

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

        for (BillDetail bd: collectionBillDetails.getList()) {
            Vector<String> row = new Vector<String>();
            row.add(bd.getItemName());
            row.add(String.valueOf(bd.getQuantity()));
            row.add(String.valueOf(bd.getTotal_price()));

            productTableModel.addRow(row);
        }

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

        this.add(right, BorderLayout.WEST);
    }

    private void initPaymentPanel() {
        JPanel left = new JPanel(new BorderLayout());
        left.setPreferredSize(new Dimension(380, 400));
        left.setBackground(Color.white);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.white);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        left.add(formPanel, BorderLayout.NORTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createLabel("Loại khách:"), gbc);

        gbc.gridx = 1;
        isRegistedRadioButton = new JRadioButton("Vãng lai");
        isRegistedRadioButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        isRegistedRadioButton.setBackground(Color.white);
        isRegistedRadioButton.setForeground(Color.black);
        isRegistedRadioButton.addActionListener(action);
        formPanel.add(isRegistedRadioButton, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(createLabel("Điện thoại:"), gbc);

        gbc.gridx = 1;
        phoneInput = createInputField(180);
        formPanel.add(phoneInput, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(createLabel("Tiền cần trả:"), gbc);

        gbc.gridx = 1;
        custPaymentField = createInputField(180);
        formPanel.add(custPaymentField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(createLabel("Tổng tiền:"), gbc);

        gbc.gridx = 1;
        totalBill = collectionBillDetails.total();
        JLabel totalBillValueLabel = createValueLabel(String.valueOf(totalBill));
        formPanel.add(totalBillValueLabel, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(createLabel("Tiền thối:"), gbc);

        gbc.gridx = 1;
        changeValueLabel = createValueLabel(String.valueOf(change));
        formPanel.add(changeValueLabel, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttonPanel.setBackground(Color.white);

        confirmedButton = createButton("Thanh Toán");
        confirmedButton.setPreferredSize(new Dimension(120, 35));
        exitButton = createButton("Thoát");
        buttonPanel.add(confirmedButton);
        buttonPanel.add(exitButton);

        formPanel.add(buttonPanel, gbc);

        this.add(left, BorderLayout.CENTER);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        label.setPreferredSize(new Dimension(100, 25));
        label.setForeground(Color.black);
        return label;
    }

    private JTextField createInputField(int width) {
        JTextField field = new JTextField();
        field.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        field.setPreferredSize(new Dimension(width, 25));
        field.setBackground(new Color(241, 211, 178));
        field.setBorder(BorderFactory.createLineBorder(new Color(21, 24, 48)));
        field.setForeground(Color.black);
        return field;
    }

    private JLabel createValueLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        label.setPreferredSize(new Dimension(180, 25));
        label.setOpaque(true);
        label.setBackground(new Color(241, 211, 178));
        label.setBorder(BorderFactory.createLineBorder(new Color(21, 24, 48)));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setForeground(Color.black);
        return label;
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        btn.setPreferredSize(new Dimension(100, 35));
        btn.setBackground(Color.white);
        btn.setForeground(Color.black);
        btn.addActionListener(action);
        return btn;
    }

    public void showPaySuccessfullyOptionPane() {
        JOptionPane.showMessageDialog(this, "Thanh toán thành công!", "Thanh toán thành công", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showPayUnsuccessfullyOptionPane() {
        JOptionPane.showMessageDialog(this, "Thanh toán thất bại!", "Thanh toán thất bại", JOptionPane.ERROR_MESSAGE);
    }

    public void showPhoneInputEmptyOptionPane() {
        JOptionPane.showMessageDialog(this, "Số điện thoại không được rỗng!", "Thanh toán thất bại", JOptionPane.ERROR_MESSAGE);
    }

    public void showCustPaymentEmptyOptionPane() {
        JOptionPane.showMessageDialog(this, "Tiền trả không được rỗng!", "Thanh toán thất bại", JOptionPane.ERROR_MESSAGE);
    }

    public void showCustPaymentLowerOptionPane() {
        JOptionPane.showMessageDialog(this, "Tiền trả phải lớn hơn 0 và lớn hơn hoặc bằng tổng hóa đơn!", "Thanh toán thất bại", JOptionPane.ERROR_MESSAGE);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            new PaymentPage(new Collection_BillDetails(), new Collection_Table(), new Employee()).setVisible(true);
//        });
//    }
}
