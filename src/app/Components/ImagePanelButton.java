package app.Components;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import app.InitFont.CustomFont;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanelButton extends JPanel {
    private CustomFont customFont = new CustomFont();
    private JLabel textLabel;
    private JLabel priceLabel;
    private JComboBox<String> serveTypeComboBox;
    private JButton addButton;
    private JLabel iconLabel;

    public ImagePanelButton(String labelText, String description, double price, String imagePath, int imageWidth, int imageHeight,
                            double scale) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(imageWidth, imageHeight));
        setBackground(new Color(164, 56, 32));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Tạo icon
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            int scaledW = (int) (imageWidth * scale);
            int scaledH = (int) (imageHeight * scale);
            Image scaledImage = img.getScaledInstance(scaledW, scaledH, Image.SCALE_SMOOTH);

            iconLabel = new JLabel(new ImageIcon(scaledImage));
            iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
            add(iconLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(true);
        contentPanel.setBackground(new Color(241, 211, 178));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(4, 8, 6, 8));
        contentPanel.setLayout(new BorderLayout());

        // Tạo text label
        textLabel = new JLabel("<html>" +
                "<div style='text-align:left;'>" +
                "<p style='font-size:13px'><b>" + labelText + "</b></p>" +
                "<p style='font-size:10px'><b>" + description + "</b></p>" +
                "</div></html>");
        textLabel.setPreferredSize(new Dimension(250, 25));
        textLabel.setHorizontalAlignment(SwingConstants.LEFT);
        textLabel.setFont(getFont().deriveFont(Font.PLAIN, 15));
        textLabel.setOpaque(false);
        textLabel.setForeground(Color.BLACK);
        textLabel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        contentPanel.add(textLabel, BorderLayout.NORTH);

        JPanel priceAndAddPanel = new JPanel();
        priceAndAddPanel.setOpaque(false);
        priceAndAddPanel.setLayout(new BorderLayout());

        priceLabel = new JLabel("<html><div style='text-align:left;'>$ " + String.valueOf(price) + "</div></html>");
        priceLabel.setHorizontalAlignment(SwingConstants.LEFT);
        priceLabel.setFont(getFont().deriveFont(Font.PLAIN, 15));
        priceLabel.setOpaque(false);
        priceLabel.setForeground(Color.BLACK);
        priceLabel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        priceAndAddPanel.add(priceLabel, BorderLayout.WEST);

        // serveTypeComboBox = new JComboBox<>();
        // serveTypeComboBox.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN,
        // 12));
        // serveTypeComboBox.setPreferredSize(new Dimension(100, 20));
        // serveTypeComboBox.addItem("Hot");
        // serveTypeComboBox.addItem("Cold");
        // serveTypeComboBox.addComponentListener(null);
        // serveTypeComboBox.setForeground(Color.WHITE); // chữ trắng để dễ nhìn trên
        // nền
        // serveTypeComboBox.setBackground(Color.RED); // mặc định Hot là đỏ

        // // Lắng nghe thay đổi lựa chọn
        // serveTypeComboBox.addActionListener(e -> {
        // String selected = (String) serveTypeComboBox.getSelectedItem();
        // if ("Hot".equals(selected)) {
        // serveTypeComboBox.setBackground(Color.RED);
        // serveTypeComboBox.setForeground(Color.WHITE);
        // } else if ("Cold".equals(selected)) {
        // serveTypeComboBox.setBackground(new Color(0, 120, 215)); // xanh nước biển
        // đẹp
        // serveTypeComboBox.setForeground(Color.WHITE);
        // }
        // });
        // priceAndAddPanel.add(serveTypeComboBox, BorderLayout.CENTER);

        addButton = new JButton("+");
        addButton.setBackground(new Color(70, 33, 26));
        addButton.setForeground(Color.WHITE);
        addButton.setMargin(new Insets(0, 10, 0, 10));
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addButton.setBackground(Color.white);
                addButton.setForeground(Color.BLACK);
                // setBorder(BorderFactory.createLineBorder(new Color(70, 33, 26), 5));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addButton.setBackground(new Color(70, 33, 26));
                addButton.setForeground(Color.WHITE);
            }
        });
        priceAndAddPanel.add(addButton, BorderLayout.EAST);
        contentPanel.add(priceAndAddPanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.SOUTH);

        // Tạo hiệu ứng click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(164, 56, 32));
                // setBorder(BorderFactory.createLineBorder(new Color(70, 33, 26), 5));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(180, 56, 32));
                // setBorder(BorderFactory.createLineBorder(Color.white, 5));
            }
        });
    }
}
