package app.Components;

import javax.swing.*;
import java.awt.*;

public class TableButton extends JButton {
    public TableButton(String text, int cordX, int cordY, int width, int height, Font font, Color backgroundColor, Color foregroundColor) {
        setText(text);
        setBounds(cordX, cordY, width, height);
        setFont(font);
        setBackground(backgroundColor);
        setForeground(foregroundColor);
        setBorderPainted(true);
        setFocusPainted(false);
    }
}
