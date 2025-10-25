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

import app.Collections.Collection_Member;
import app.Components.CustomTableCellRenderer;
import app.Components.CustomTableHeaderRenderer;
import app.InitFont.CustomFont;
import app.Object.*;

public class PromotionPage extends JPanel {
    private DefaultTableModel tableModel;
    private JTextField nameInput;
    private JTextField idInput;
    private JTextField dbInput;
    private JTextField phoneInput;
    private JLabel pointGained;
    private JLabel discount;
    private String id;
    private String name;
    private LocalDate dob;
    private String phoneNumber;
    private int points;
    Collection_Member memberList = new Collection_Member();
    CustomFont customFont = new CustomFont();

    public PromotionPage() {
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
        // panel.setBackground(new Color(225, 203, 177));

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

        JLabel idLabel = new JLabel("Id: ");
        idLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        idLabel.setForeground(Color.black);
        idLabel.setPreferredSize(new Dimension(120, 25));
        left.add(idLabel);

        idInput = new JTextField();
        idInput.setPreferredSize(new Dimension(140, 25));
        idInput.setBackground(new Color(241, 211, 178));
        idInput.setForeground(Color.black);
        idInput.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        idInput.setBorder(BorderFactory.createLineBorder(new Color(21, 24, 48)));
        left.add(idInput);

        JLabel nameLabel = new JLabel("Name: ");
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

        JLabel dbLabel = new JLabel("Birth Day: ");
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

        JLabel phoneLabel = new JLabel("Phone: ");
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

        JLabel pointLabel = new JLabel("Points: ");
        pointLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        pointLabel.setForeground(Color.black);
        pointLabel.setPreferredSize(new Dimension(120, 25));
        right.add(pointLabel);

        pointGained = new JLabel("0 pts");
        pointGained.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        pointGained.setForeground(Color.black);
        pointGained.setPreferredSize(new Dimension(140, 25));
        right.add(pointGained);

        tbPanel.add(right);

        tbiPanel.add(tbPanel);

        JPanel imgPanel = new JPanel();
        imgPanel.setLayout(new BorderLayout());
        imgPanel.setPreferredSize(new Dimension(250, 300));
        // Border lineBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, new
        // Color(255, 213, 146));
        // imgPanel.setBorder(lineBorder);
        imgPanel.setOpaque(false);

        String imagePath = "asset/user.png";
        Image scaledImage;

        try {
            // Read the image from the file
            Image image = ImageIO.read(new File(imagePath));

            // Scale the image
            int newWidth = (int) (200 * 0.8); // Desired width
            int newHeight = (int) (200 * 0.8); // Desired height
            scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

            // Create an ImageIcon from the scaled image
            ImageIcon imageIcon = new ImageIcon(scaledImage);

            // Create a JLabel and set the icon
            JLabel imgLabel = new JLabel(FontIcon.of(Feather.USER, (int) (200 * 0.8), Color.black));
            imgLabel.setPreferredSize(new Dimension(250, 300));
            imgLabel.setHorizontalAlignment(JLabel.CENTER);
            imgLabel.setVerticalAlignment(JLabel.NORTH);
            imgLabel.setBackground(Color.CYAN);
            imgPanel.add(imgLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        tbPanel.add(imgPanel);

        // tbiPanel.add(imgPanel, BorderLayout.SOUTH);
        panel.add(tbiPanel, BorderLayout.CENTER);

        add(panel, BorderLayout.NORTH);
    }

    private void createEmpTablePanel() {
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        tablePanel.setPreferredSize(new Dimension(1100, 200));
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

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setPreferredSize(new Dimension(800, 50));
        panel.setOpaque(false);

        JPanel left = new JPanel();
        left.setLayout(new FlowLayout(FlowLayout.LEFT));
        left.setPreferredSize(new Dimension(360, 35));
        Border lineBorder = BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black);
        left.setBorder(lineBorder);
        left.setOpaque(false);

        JLabel searchLabel = new JLabel("Search Customers:");
        searchLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        searchLabel.setForeground(Color.black);
        searchLabel.setPreferredSize(new Dimension(130, 25)); // Thay đổi kích thước cho phù hợp
        left.add(searchLabel);

        JTextField searchBar = new JTextField();
        searchBar.setForeground(Color.black);
        searchBar.setBackground(new Color(241, 211, 178));
        searchBar.setBorder(null);
        searchBar.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        searchBar.setPreferredSize(new Dimension(170, 25)); // Thay đổi kích thước cho phù hợp và vị trí
        left.add(searchBar);

        FontIcon lookingGlassIcon = FontIcon.of(Feather.SEARCH, 24, Color.BLACK);
        JButton findProduct = new JButton(lookingGlassIcon);
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

        JButton addButton = new JButton("Add");
        addButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        addButton.setPreferredSize(new Dimension(100, 25));
        addButton.setForeground(Color.black);
        addButton.setBackground(new Color(241, 211, 178));
        right.add(addButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        deleteButton.setPreferredSize(new Dimension(100, 25));
        deleteButton.setForeground(Color.black);
        deleteButton.setBackground(new Color(241, 211, 178));
        right.add(deleteButton);

        JButton cancelChangeButton = new JButton("Cancel changes");
        cancelChangeButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        cancelChangeButton.setPreferredSize(new Dimension(140, 25));
        cancelChangeButton.setForeground(Color.black);
        cancelChangeButton.setBackground(new Color(241, 211, 178));
        right.add(cancelChangeButton);

        JButton saveButton = new JButton("Save");
        saveButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        saveButton.setPreferredSize(new Dimension(100, 25));
        saveButton.setForeground(Color.black);
        saveButton.setBackground(new Color(241, 211, 178));
        right.add(saveButton);

        panel.add(right);
        tablePanel.add(panel, BorderLayout.NORTH);

        // panel.setBackground(new Color(225, 203, 177));

        this.tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make "Points" column (index 4) uneditable
                return column != 4;
            }
        };

        tableModel.addColumn("N0");
        tableModel.addColumn("Name");
        tableModel.addColumn("Day Of Birth");
        tableModel.addColumn("Phone Number");
        tableModel.addColumn("Points");

        JTable table = new JTable(tableModel);
        table.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        table.setForeground(Color.black);
        table.setBackground(Color.white);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setForeground(Color.black);
        tableHeader.setBackground(Color.white);
        tableHeader.setDefaultRenderer(new CustomTableHeaderRenderer());
        tableHeader.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));

        // Apply the custom renderer to each column
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
                phoneNumber = phoneInput.getText();
                points = 0;

                Customer mem = new Customer();
                memberList.addMember(mem);

                idInput.setText("");
                nameInput.setText("");
                dbInput.setText("");
                phoneInput.setText("");

                Vector<String> rowData = new Vector<>();
                rowData.add(id);
                rowData.add(name);
                rowData.add(dbInput.getText());
                rowData.add(phoneNumber);
                rowData.add(Integer.toString(points));

                tableModel.addRow(rowData);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    idInput.setText((String) tableModel.getValueAt(selectedRow, 0));
                    nameInput.setText((String) tableModel.getValueAt(selectedRow, 1));
                    dbInput.setText((String) tableModel.getValueAt(selectedRow, 2));
                    phoneInput.setText((String) tableModel.getValueAt(selectedRow, 3));
                    pointGained.setText((String) tableModel.getValueAt(selectedRow, 4) + " pts");

                    // Delete employee
                    memberList.deleteMember((String) tableModel.getValueAt(selectedRow, 0));

                    // Delete the selected row
                    tableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete", "Delete Error",
                            JOptionPane.ERROR_MESSAGE);
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
                        pointGained.setText((String) tableModel.getValueAt(selectedRow, 4) + " pts");
                        discount.setText((String) tableModel.getValueAt(selectedRow, 5) + "%");
                    }
                }
            }
        });

        add(tablePanel, BorderLayout.CENTER);
    }
}