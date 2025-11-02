package app.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.swing.FontIcon;

import app.Collections.Collection_Employee;
import app.Components.CustomTableCellRenderer;
import app.Components.CustomTableHeaderRenderer;
import app.InitFont.CustomFont;
import app.Object.*;

public class EmployeePage extends JPanel {
    private DefaultTableModel tableModel;
    private JTextField nameInput;
    private JTextField idInput;
    private JTextField dbInput;
    private JTextField phoneInput;
    private JComboBox<String> responsibilityCategory;
    private String id;
    private String name;
    private LocalDate dob;
    private String phone;
    private String responsibility;
    Collection_Employee employeeList = new Collection_Employee();
    CustomFont customFont = new CustomFont();

    public EmployeePage() {
        setPreferredSize(new Dimension(1100, 500));
        setLayout(new BorderLayout());
        setBackground(Color.white);
        setOpaque(true);

        JPanel empty = new JPanel();
        empty.setPreferredSize(new Dimension(1100, 140));
        empty.setOpaque(false);
        add(empty, BorderLayout.NORTH);

        createEmpTextBox();
        createEmpTablePanel();
    }

    private void createEmpTextBox() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(1100, 200));
        panel.setOpaque(false);

        JPanel emptyW = new JPanel();
        emptyW.setPreferredSize(new Dimension(15, 300));
        emptyW.setOpaque(false);
        panel.add(emptyW, BorderLayout.WEST);

        JPanel emptyN = new JPanel();
        emptyN.setPreferredSize(new Dimension(1100, 20));
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
        tbiPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        tbiPanel.setPreferredSize(new Dimension(1100, 300));
        tbiPanel.setOpaque(false);

        JPanel tbPanel = new JPanel();
        tbPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        tbPanel.setPreferredSize(new Dimension(1100, 300));
        tbPanel.setOpaque(false);

        JPanel left = new JPanel();
        left.setLayout(new FlowLayout(FlowLayout.LEFT));
        left.setOpaque(false);
        left.setPreferredSize(new Dimension(330, 300));

        JLabel idLabel = new JLabel("Mã nhân viên:");
        idLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        idLabel.setForeground(Color.black);
        idLabel.setPreferredSize(new Dimension(120, 25));
        left.add(idLabel);

        idInput = new JTextField();
        idInput.setPreferredSize(new Dimension(140, 25));
        idInput.setBackground(new Color(241, 211, 178));
        idInput.setBorder(BorderFactory.createLineBorder(new Color(21, 24, 48)));
        idInput.setForeground(Color.black);
        idInput.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        left.add(idInput);

        JLabel nameLabel = new JLabel("Họ và tên:");
        nameLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        nameLabel.setForeground(Color.black);
        nameLabel.setPreferredSize(new Dimension(120, 25));
        left.add(nameLabel);

        nameInput = new JTextField();
        nameInput.setPreferredSize(new Dimension(140, 25));
        nameInput.setBackground(new Color(241, 211, 178));
        nameInput.setBorder(BorderFactory.createLineBorder(new Color(21, 24, 48)));
        nameInput.setForeground(Color.black);
        nameInput.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        left.add(nameInput);

        JLabel dbLabel = new JLabel("Ngày sinh :");
        dbLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        dbLabel.setForeground(Color.black);
        dbLabel.setPreferredSize(new Dimension(120, 25));
        left.add(dbLabel);

        dbInput = new JTextField();
        dbInput.setPreferredSize(new Dimension(140, 25));
        dbInput.setBackground(new Color(241, 211, 178));
        dbInput.setBorder(BorderFactory.createLineBorder(new Color(21, 24, 48)));
        dbInput.setForeground(Color.black);
        dbInput.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        left.add(dbInput);

        tbPanel.add(left);

        JPanel right = new JPanel();
        right.setLayout(new FlowLayout(FlowLayout.LEFT));
        right.setOpaque(false);
        right.setPreferredSize(new Dimension(330, 300));

        JLabel phoneLabel = new JLabel("Số điện thoại:");
        phoneLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        phoneLabel.setForeground(Color.black);
        phoneLabel.setPreferredSize(new Dimension(120, 25));
        right.add(phoneLabel);

        phoneInput = new JTextField();
        phoneInput.setPreferredSize(new Dimension(140, 25));
        phoneInput.setBackground(new Color(241, 211, 178));
        phoneInput.setBorder(BorderFactory.createLineBorder(new Color(21, 24, 48)));
        phoneInput.setForeground(Color.black);
        phoneInput.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        right.add(phoneInput);

        JLabel responsibilityLabel = new JLabel("Chức vụ:");
        responsibilityLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        responsibilityLabel.setForeground(Color.black);
        responsibilityLabel.setPreferredSize(new Dimension(120, 25));
        right.add(responsibilityLabel);

        responsibilityCategory = new JComboBox<>();
        responsibilityCategory.setPreferredSize(new Dimension(140, 25));
        responsibilityCategory.setForeground(Color.black);
        responsibilityCategory.setBackground(new Color(241, 211, 178));
        responsibilityCategory.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        responsibilityCategory.addItem("Quản trị");
        responsibilityCategory.addItem("Thu ngân");
        responsibilityCategory.addItem("Pha chế");
        responsibilityCategory.addItem("Phục vụ");
        right.add(responsibilityCategory);

        tbPanel.add(right);

        tbiPanel.add(tbPanel);

        JPanel imgPanel = new JPanel();
        imgPanel.setLayout(new BorderLayout());
        imgPanel.setPreferredSize(new Dimension(250, 300));
        imgPanel.setOpaque(false);

        String imagePath = "asset/user.png";
        Image scaledImage;

        try {
            Image image = ImageIO.read(new File(imagePath));
            int newWidth = (int) (200 * 0.8);
            int newHeight = (int) (200 * 0.8);
            scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(scaledImage);
            JLabel imgLabel = new JLabel(FontIcon.of(Feather.USER, (int) (200 * 0.8), Color.black));
            imgLabel.setPreferredSize(new Dimension(250, 300));
            imgLabel.setHorizontalAlignment(JLabel.CENTER);
            imgLabel.setVerticalAlignment(JLabel.NORTH);
            imgPanel.add(imgLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        tbPanel.add(imgPanel);
        panel.add(tbiPanel, BorderLayout.CENTER);

        add(panel, BorderLayout.NORTH);
    }

    private void createEmpTablePanel() {
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        tablePanel.setPreferredSize(new Dimension(1100, 500));
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setOpaque(false);

        JPanel emptyW = new JPanel();
        emptyW.setPreferredSize(new Dimension(20, 500));
        emptyW.setOpaque(false);
        tablePanel.add(emptyW, BorderLayout.WEST);

        JPanel emptyE = new JPanel();
        emptyE.setPreferredSize(new Dimension(20, 500));
        emptyE.setOpaque(false);
        tablePanel.add(emptyE, BorderLayout.EAST);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setPreferredSize(new Dimension(800, 50));
        panel.setOpaque(false);

        JPanel left = new JPanel();
        left.setLayout(new FlowLayout(FlowLayout.LEFT));
        left.setPreferredSize(new Dimension(360, 35));
        Border lineBorder = BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(255, 213, 146));
        left.setBorder(lineBorder);
        left.setOpaque(false);

        JLabel searchLabel = new JLabel("Tìm nhân viên:");
        searchLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        searchLabel.setForeground(Color.black);
        searchLabel.setPreferredSize(new Dimension(130, 25));
        left.add(searchLabel);

        JTextField searchBar = new JTextField();
        searchBar.setForeground(Color.black);
        searchBar.setBackground(new Color(241, 211, 178));
        searchBar.setBorder(null);
        searchBar.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        searchBar.setPreferredSize(new Dimension(170, 25));
        left.add(searchBar);

        JButton findProduct = new JButton(FontIcon.of(Feather.SEARCH, 24, Color.BLACK));
        findProduct.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        findProduct.setForeground(Color.black);
        findProduct.setBackground(new Color(241, 211, 178));
        findProduct.setPreferredSize(new Dimension(30, 30));
        findProduct.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        left.add(findProduct);

        panel.add(left);

        JPanel right = new JPanel();
        right.setLayout(new FlowLayout(FlowLayout.LEFT));
        right.setPreferredSize(new Dimension(490, 35));
        right.setOpaque(false);

        JButton addButton = new JButton("Thêm");
        addButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        addButton.setPreferredSize(new Dimension(100, 25));
        addButton.setForeground(Color.black);
        addButton.setBackground(new Color(241, 211, 178));
        right.add(addButton);

        JButton deleteButton = new JButton("Xóa");
        deleteButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        deleteButton.setPreferredSize(new Dimension(100, 25));
        deleteButton.setForeground(Color.black);
        deleteButton.setBackground(new Color(241, 211, 178));
        right.add(deleteButton);

        JButton cancelChangeButton = new JButton("Hủy thay đổi");
        cancelChangeButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        cancelChangeButton.setPreferredSize(new Dimension(140, 25));
        cancelChangeButton.setForeground(Color.black);
        cancelChangeButton.setBackground(new Color(241, 211, 178));
        right.add(cancelChangeButton);

        JButton saveButton = new JButton("Lưu");
        saveButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        saveButton.setPreferredSize(new Dimension(100, 25));
        saveButton.setForeground(Color.black);
        saveButton.setBackground(new Color(241, 211, 178));
        right.add(saveButton);

        panel.add(right);
        tablePanel.add(panel, BorderLayout.NORTH);

        // ===== Bảng dữ liệu =====
        this.tableModel = new DefaultTableModel();
        tableModel.addColumn("Mã");
        tableModel.addColumn("Họ và tên");
        tableModel.addColumn("Ngày sinh");
        tableModel.addColumn("Số điện thoại");
        tableModel.addColumn("Chức vụ");

        JTable table = new JTable(tableModel);
        table.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        table.setForeground(Color.black);
        table.setBackground(Color.white);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setForeground(Color.black);
        tableHeader.setBackground(Color.white);
        tableHeader.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        tableHeader.setDefaultRenderer(new CustomTableHeaderRenderer());

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new CustomTableCellRenderer());
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 500));
        scrollPane.setForeground(Color.black);
        scrollPane.getViewport().setBackground(Color.white);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id = idInput.getText();
                name = nameInput.getText();
                dob = LocalDate.parse(dbInput.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                phone = phoneInput.getText();
                responsibility = (String) responsibilityCategory.getSelectedItem();

                Employee employee = new Employee();
                employeeList.addEmployee(employee);

                idInput.setText("");
                nameInput.setText("");
                dbInput.setText("");
                phoneInput.setText("");
                responsibilityCategory.setSelectedItem("Quản trị");

                Vector<String> rowData = new Vector<>();
                rowData.add(id);
                rowData.add(name);
                rowData.add(dob.toString());
                rowData.add(phone);
                rowData.add(responsibility);
                tableModel.addRow(rowData);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    employeeList.deleteEmployee((String) tableModel.getValueAt(selectedRow, 0));
                    tableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(
                        null,
                        "Vui lòng chọn một dòng để xóa",
                        "Lỗi xóa",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        idInput.setText((String) tableModel.getValueAt(selectedRow, 0));
                        nameInput.setText((String) tableModel.getValueAt(selectedRow, 1));
                        dbInput.setText((String) tableModel.getValueAt(selectedRow, 2));
                        phoneInput.setText((String) tableModel.getValueAt(selectedRow, 3));
                        responsibilityCategory.setSelectedItem((String) tableModel.getValueAt(selectedRow, 4));
                    }
                }
            }
        });

        add(tablePanel, BorderLayout.CENTER);
    }
}
