package app.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.swing.FontIcon;

import app.AppFunctions.Collection_MenuItem;
import app.Components.CustomTableCellRenderer;
import app.Components.CustomTableHeaderRenderer;
import app.InitFont.CustomFont;
import app.Object.*;
//import app.SaveToFile.ReadSaveFromFile;

public class ProductPage extends JPanel {
    private DefaultTableModel tableModel;
    private JTextField nameInput;
    private JTextField idInput;
    private JRadioButton servedHotRadio;
    private JTextField priceInput;
    private JComboBox<String> categoryComboBox;
    private JRadioButton statusRadioButton;
    private JTextField sizeInput;
    private String id;
    private String name;
    private Boolean servedHot;
    private double price;
    Collection_MenuItem menu = new Collection_MenuItem();
    CustomFont customFont = new CustomFont();

    public ProductPage() {
        setPreferredSize(new Dimension(1100, 500));
        setLayout(new BorderLayout());
        setBackground(Color.white);

        JPanel empty = new JPanel();
        empty.setPreferredSize(new Dimension(1100, 140));
        empty.setOpaque(false);
        add(empty, BorderLayout.NORTH);

        createEmpTextBox();
        createEmpTablePanel();
    }

    // @Override
    // protected void paintComponent(Graphics g) {
    // String imagePath = "dev_cafe/asset/background.png"; // Path to your GIF image
    // file
    // File imageFile = new File(imagePath);

    // // Chèn ảnh vào Option menu
    // try {
    // // Đọc ảnh từ file
    // Image image = ImageIO.read(imageFile);

    // // Tạo icon cho ảnh
    // int newWidth = getWidth(); // Get the width of the panel
    // int newHeight = getHeight(); // Get the height of the panel
    // Image scaledImage = image.getScaledInstance(newWidth, newHeight,
    // Image.SCALE_SMOOTH);
    // g.drawImage(scaledImage, 0, 0, null);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    private void createEmpTextBox() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(1100, 200));
        panel.setOpaque(false);
        // panel.setBackground(new Color(225, 203, 177));

        JPanel emptyN = new JPanel();
        emptyN.setPreferredSize(new Dimension(1100, 20));
        emptyN.setOpaque(false);
        panel.add(emptyN, BorderLayout.NORTH);

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
        idInput.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        idInput.setForeground(Color.black);
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
        nameInput.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        nameInput.setForeground(Color.black);
        nameInput.setBorder(BorderFactory.createLineBorder(new Color(21, 24, 48)));
        left.add(nameInput);

        JLabel sizeLabel = new JLabel("Size: ");
        sizeLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        sizeLabel.setForeground(Color.black);
        sizeLabel.setPreferredSize(new Dimension(120, 25));
        left.add(sizeLabel);

        sizeInput = new JTextField();
        sizeInput.setPreferredSize(new Dimension(140, 25));
        sizeInput.setBackground(new Color(241, 211, 178));
        sizeInput.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        sizeInput.setForeground(Color.black);
        sizeInput.setBorder(BorderFactory.createLineBorder(new Color(21, 24, 48)));
        left.add(sizeInput);

        tbPanel.add(left);

        JPanel right = new JPanel();
        right.setLayout(new FlowLayout(FlowLayout.LEFT));
        right.setOpaque(false);
        right.setPreferredSize(new Dimension(330, 300));

        JLabel priceLabel = new JLabel("Price: ");
        priceLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        priceLabel.setForeground(Color.black);
        priceLabel.setPreferredSize(new Dimension(120, 25));
        right.add(priceLabel);

        priceInput = new JTextField();
        priceInput.setPreferredSize(new Dimension(140, 25));
        priceInput.setBackground(new Color(241, 211, 178));
        priceInput.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        priceInput.setForeground(Color.black);
        priceInput.setBorder(BorderFactory.createLineBorder(new Color(21, 24, 48)));
        right.add(priceInput);

        JLabel categoryLabel = new JLabel("Category: ");
        categoryLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        categoryLabel.setForeground(Color.black);
        categoryLabel.setPreferredSize(new Dimension(120, 25));
        right.add(categoryLabel);

        categoryComboBox = new JComboBox<String>();
        categoryComboBox.setPreferredSize(new Dimension(140, 25));
        categoryComboBox.setBackground(new Color(241, 211, 178));
        categoryComboBox.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        categoryComboBox.setForeground(Color.black);
        categoryComboBox.setBorder(BorderFactory.createLineBorder(new Color(21, 24, 48)));
        categoryComboBox.addItem("Coffee");
        categoryComboBox.addItem("Juice");
        categoryComboBox.addItem("Cocoa");
        categoryComboBox.addItem("Tea");
        right.add(categoryComboBox);

        JLabel statusLabel = new JLabel("Status: ");
        statusLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        statusLabel.setForeground(Color.black);
        statusLabel.setPreferredSize(new Dimension(120, 25));
        right.add(statusLabel);

        statusRadioButton = new JRadioButton("In stock");
        statusRadioButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        statusRadioButton.setForeground(Color.black);
        right.add(statusRadioButton);

        tbPanel.add(right);

        tbiPanel.add(tbPanel);

        JPanel imgPanel = new JPanel();
        imgPanel.setLayout(new BorderLayout());
        imgPanel.setPreferredSize(new Dimension(250, 300));
        // Border lineBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, new
        // Color(241, 211, 178));
        // imgPanel.setBorder(lineBorder);
        imgPanel.setOpaque(false);

        String imagePath = "asset/placeholder.png";
        Image scaledImage;

        try {
            // Read the image from the file
            Image image = ImageIO.read(new File(imagePath));

            // Scale the image
            int newWidth = (int) (200 * 0.8f); // Desired width
            int newHeight = (int) (200 * 0.8f); // Desired height
            scaledImage = image.getScaledInstance(newWidth, newHeight,
                    Image.SCALE_SMOOTH);

            // Create an ImageIcon from the scaled image
            ImageIcon imageIcon = new ImageIcon(scaledImage);

            // Create a JLabel and set the icon
            JLabel imgLabel = new JLabel(imageIcon);
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
        Border lineBorder = BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black);
        left.setBorder(lineBorder);
        left.setOpaque(false);

        JLabel searchLabel = new JLabel("Search Products:");
        searchLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        searchLabel.setForeground(Color.black);
        searchLabel.setPreferredSize(new Dimension(120, 25)); // Thay đổi kích thước cho phù hợp
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

        JButton cancelChangeButton = new JButton("Cancel Change");
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

        this.tableModel = new DefaultTableModel();
        tableModel.addColumn("N0");
        tableModel.addColumn("Name");
        tableModel.addColumn("Size");
        tableModel.addColumn("Price");
        tableModel.addColumn("Category");
        tableModel.addColumn("Status");

//        ReadSaveFromFile s = new ReadSaveFromFile();
//
//        try {
//            Object o = s.ReadFile("dev_cafe/data/menu_items_data.txt");
//
//            if (o instanceof ArrayList<?>) {
//                @SuppressWarnings("unchecked")
//                ArrayList<MenuItem> listOfItem = (ArrayList<MenuItem>) o;
//                menu.addAllItem(listOfItem);
//
//                for (MenuItem item : menu.getListOfItem()) {
//                    Vector<String> rowData = new Vector<>();
//                    rowData.add(item.getId());
//                    rowData.add(item.getName());
//                    rowData.add(String.valueOf(item.getPrice()));
//                    tableModel.addRow(rowData);
//                }
//            }
//
//            System.out.println("Import successfully!");
//        } catch (Exception ee) {
//            ee.printStackTrace();
//        }

        JTable table = new JTable(tableModel);
        table.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        table.setForeground(Color.black);
        table.setBackground(Color.white);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setForeground(Color.black);
        tableHeader.setBackground(new Color(241, 211, 178));
        tableHeader.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        tableHeader.setDefaultRenderer(new CustomTableHeaderRenderer());

        // Apply the custom renderer to each column
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new CustomTableCellRenderer());
        }

        JScrollPane scrollPane = new JScrollPane(table);
        // scrollPane.setPreferredSize(new Dimension(800, 300));
        scrollPane.setForeground(Color.black);
        scrollPane.getViewport().setBackground(new Color(241, 211, 178));

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id = idInput.getText();
                name = nameInput.getText();
                servedHot = servedHotRadio.isSelected();
                price = Double.parseDouble(priceInput.getText());

                MenuItem item = new MenuItem(id, name, servedHot, price);
                menu.addItem(item);

                idInput.setText("");
                nameInput.setText("");
                servedHotRadio.setSelected(false);
                priceInput.setText("");

                Vector<String> rowData = new Vector<>();
                rowData.add(id);
                rowData.add(name);
                rowData.add(String.valueOf(price));
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
                    priceInput.setText((String) tableModel.getValueAt(selectedRow, 2));

                    // Delete employee
                    menu.deleteItem((String) tableModel.getValueAt(selectedRow, 0));

                    // Delete the selected row
                    tableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete", "Delete Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

//        saveButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ReadSaveFromFile s = new ReadSaveFromFile();
//
//                try {
//                    s.SaveFile(menu.getListOfItem(), "data/menu_items_data.txt");
//                    System.out.println("Save successfully!");
//                } catch (Exception ee) {
//                    ee.printStackTrace();
//                }
//            }
//        });

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        idInput.setText((String) tableModel.getValueAt(selectedRow, 0));
                        nameInput.setText((String) tableModel.getValueAt(selectedRow, 1));
                        priceInput.setText((String) tableModel.getValueAt(selectedRow, 2));
                    }
                }
            }
        });

        add(tablePanel, BorderLayout.CENTER);
    }
}
