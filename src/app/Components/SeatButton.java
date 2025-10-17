package app.Components;

import javax.swing.*;
import java.awt.*;

public class SeatButton extends JButton {
    private int width;
    private int height;
    private Color backgroundColor;
    private Color foregroundColor;
    private Font font;

    public SeatButton(String text, int width, int height, Color backgroundColor, Color foregroundColor, Font font) {
        ImageIcon icon = new ImageIcon("asset/cat.jpg");

        setPreferredSize(new Dimension(width, height));
        setBackground(backgroundColor);
        setForeground(foregroundColor);
        setFont(font);
        setOpaque(true);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(true);
        setHorizontalAlignment(SwingConstants.LEFT);
        // this.setVerticalAlignment(SwingConstants.CENTER);
        // this.setMargin(new Insets(5, 10, 5, 10));
        setIcon(icon);
        setBorder(BorderFactory.createLineBorder(Color.white));
    }
}
