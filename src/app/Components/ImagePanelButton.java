package app.Components;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import app.Collections.Collection_MenuItem;
import app.InitFont.CustomFont;
import app.Listener.ActionListener_ImagePanelButton;
import app.Object.MenuItem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanelButton extends JPanel {
    public Collection_MenuItem collectionMenuItem;
    public ActionListener_ImagePanelButton action;
    public MenuItem mi;
    private CustomFont customFont = new CustomFont();
    private JLabel textLabel;
    private JLabel priceLabel;
    private JComboBox<String> serveTypeComboBox;
    private JButton addButton;
    private JLabel iconLabel;

    public ImagePanelButton(MenuItem mi, Collection_MenuItem collectionMenuItem, String imagePath, int imageWidth, int imageHeight,
                            double scale) {
        this.mi = mi;
        this.collectionMenuItem = collectionMenuItem;
        this.action = new ActionListener_ImagePanelButton(this);

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
                "<p style='font-size:13px'><b>" + mi.getName() + "</b></p>" +
                "<p style='font-size:10px'><b>" + mi.getDescription() + "</b></p>" +
                "</div></html>");
        textLabel.setPreferredSize(new Dimension(250, 50));
        textLabel.setVerticalAlignment(SwingConstants.NORTH);
        textLabel.setHorizontalAlignment(SwingConstants.LEFT);
        textLabel.setFont(getFont().deriveFont(Font.PLAIN, 15));
        textLabel.setOpaque(false);
        textLabel.setForeground(Color.BLACK);
        textLabel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        contentPanel.add(textLabel, BorderLayout.NORTH);

        JPanel priceAndAddPanel = new JPanel();
        priceAndAddPanel.setOpaque(false);
        priceAndAddPanel.setLayout(new BorderLayout());

        priceLabel = new JLabel("<html><div style='text-align:left;'>" + String.valueOf(mi.getPrice()) + " VND</div></html>");
        priceLabel.setHorizontalAlignment(SwingConstants.LEFT);
        priceLabel.setFont(getFont().deriveFont(Font.PLAIN, 15));
        priceLabel.setOpaque(false);
        priceLabel.setForeground(Color.BLACK);
        priceLabel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        priceAndAddPanel.add(priceLabel, BorderLayout.WEST);

        addButton = new JButton("+");
        addButton.setBackground(new Color(70, 33, 26));
        addButton.setForeground(Color.WHITE);
        addButton.setMargin(new Insets(0, 10, 0, 10));
        addButton.addActionListener(action);
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
