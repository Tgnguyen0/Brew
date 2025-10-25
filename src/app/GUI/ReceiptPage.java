package app.GUI;

import app.Collections.Collection_Member;
import app.Components.CustomTableCellRenderer;
import app.Components.CustomTableHeaderRenderer;
import app.InitFont.CustomFont;
import app.Collections.Collection_BillDetails;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

public class ReceiptPage extends JPanel {
    private CustomFont customFont = new CustomFont();
    private DefaultTableModel productTableModel;
    private Collection_BillDetails bdl = new Collection_BillDetails();
    private Collection_Member memberList = new Collection_Member();
    private JTable productTable;
    private JLabel nameText;
    private JLabel idText;
    private JLabel dbText;
    private JLabel phoneText;
    private JLabel pointGained;
    private JLabel discountAmount;
    private JTextField phoneInput;

    public ReceiptPage() {
        setPreferredSize(new Dimension(1100, 500));
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Color.white);

        JPanel emptyL = new JPanel();
        emptyL.setPreferredSize(new Dimension(16, 500));
        emptyL.setOpaque(false);
        add(emptyL, BorderLayout.WEST);

        JPanel emptyR = new JPanel();
        emptyR.setPreferredSize(new Dimension(16, 500));
        emptyR.setOpaque(false);
        add(emptyR, BorderLayout.EAST);

        // add(createSearchPane(), BorderLayout.NORTH);
        add(createReceiptPanel(), BorderLayout.NORTH);
    }

    public JPanel createReceiptPanel() {
        JPanel ptPanel = new JPanel();
        ptPanel.setOpaque(false);
        ptPanel.setPreferredSize(new Dimension(800, 350));
        ptPanel.setLayout(new BorderLayout());

        JPanel esPanel = new JPanel();
        esPanel.setOpaque(false);
        esPanel.setLayout(new BorderLayout());
        esPanel.setPreferredSize(new Dimension(800, 300));

        JLabel introLabel = new JLabel("<html><div style='font-size:20px'><b>Invoices List</b></div></html>");
        introLabel.setForeground(Color.BLACK);
        ;
        introLabel.setBackground(Color.white);
        introLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 20));
        introLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        esPanel.add(introLabel, BorderLayout.NORTH);

        JPanel editPanel = new JPanel();
        editPanel.setOpaque(true);
        editPanel.setBackground(Color.white);
        editPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        editPanel.setPreferredSize(new Dimension(700, 30));

        JLabel findLabel = new JLabel("Tìm kiếm khách hàng theo sdt: ");
        findLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        findLabel.setForeground(Color.BLACK);
        ;
        findLabel.setPreferredSize(new Dimension(220, 25));
        editPanel.add(findLabel);

        phoneInput = new JTextField();
        phoneInput.setPreferredSize(new Dimension(140, 25));
        phoneInput.setBackground(new Color(241, 211, 178));
        phoneInput.setBorder(BorderFactory.createLineBorder(new Color(79, 92, 133)));
        phoneInput.setForeground(Color.BLACK);
        phoneInput.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        editPanel.add(phoneInput);

        JButton findButton = new JButton("Tìm kiếm");
        findButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        findButton.setPreferredSize(new Dimension(120, 25));
        findButton.setForeground(Color.BLACK);
        findButton.setBackground(new Color(241, 211, 178));

        JButton refreshButton = new JButton("Làm mới");
        refreshButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        refreshButton.setPreferredSize(new Dimension(120, 25));
        refreshButton.setForeground(Color.BLACK);
        refreshButton.setBackground(new Color(241, 211, 178));

        JButton invoicesButton = new JButton("Chi tiết hóa đơn");
        invoicesButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        invoicesButton.setPreferredSize(new Dimension(150, 25));
        invoicesButton.setForeground(Color.BLACK);
        invoicesButton.setBackground(new Color(241, 211, 178));

        JButton exportButton = new JButton("Xuất hóa đơn");
        exportButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        exportButton.setPreferredSize(new Dimension(120, 25));
        exportButton.setForeground(Color.BLACK);
        exportButton.setBackground(new Color(241, 211, 178));

        editPanel.add(findButton);
        editPanel.add(refreshButton);
        editPanel.add(invoicesButton);
        editPanel.add(exportButton);
        esPanel.add(editPanel, BorderLayout.CENTER);

        productTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make "Points" column (index 0,1,2) uneditable
                return column != 0 && column != 1 && column != 2 && column != 3;
            }
        };
        productTableModel.addColumn("N0");
        productTableModel.addColumn("Name");
        productTableModel.addColumn("Served");
        productTableModel.addColumn("Quantity");
        productTableModel.addColumn("Price");

        productTable = new JTable(productTableModel);
        productTable.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        productTable.setForeground(Color.BLACK);
        productTable.setBackground(new Color(241, 211, 178));

        JComboBox<String> servedComboBox = new JComboBox<>();
        servedComboBox.setForeground(Color.BLACK);
        ;
        servedComboBox.setBackground(new Color(241, 211, 178));
        servedComboBox.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        servedComboBox.addItem("Hot");
        servedComboBox.addItem("Cold");

        TableCellEditor comboBoxEditor = new DefaultCellEditor(servedComboBox);

        // Set the cell editor for the "Served" column (index 2)
        TableColumn servedColumn = productTable.getColumnModel().getColumn(2);
        servedColumn.setCellEditor(comboBoxEditor);

        JTableHeader tableHeader = productTable.getTableHeader();
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setBackground(new Color(241, 211, 178));
        tableHeader.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        tableHeader.setDefaultRenderer(new CustomTableHeaderRenderer());

        for (int i = 0; i < productTable.getColumnCount(); i++) {
            productTable.getColumnModel().getColumn(i).setCellRenderer(new CustomTableCellRenderer());
        }

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setPreferredSize(new Dimension(800, 235));
        scrollPane.getViewport().setBackground(new Color(241, 211, 178));
        scrollPane.setBackground(new Color(241, 211, 178));
        scrollPane.setForeground(Color.BLACK);
        esPanel.add(scrollPane, BorderLayout.SOUTH);

        ptPanel.add(esPanel, BorderLayout.CENTER);

        JPanel emptyW = new JPanel();
        emptyW.setPreferredSize(new Dimension(15, 100));
        emptyW.setOpaque(false);
        ptPanel.add(emptyW, BorderLayout.WEST);

        JPanel emptyE = new JPanel();
        emptyE.setPreferredSize(new Dimension(15, 100));
        emptyE.setOpaque(false);
        ptPanel.add(emptyE, BorderLayout.EAST);

        JPanel emptyS = new JPanel();
        emptyS.setOpaque(false);
        emptyS.setPreferredSize(new Dimension(400, 20));
        ptPanel.add(emptyS, BorderLayout.SOUTH);

        return ptPanel;
    }
}