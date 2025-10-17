package app.Components;

import app.InitFont.CustomFont;

import javax.swing.*;
import java.awt.*;

public class OneSeatedTablePanel extends JFrame  {
    CustomFont customFont = new CustomFont();

    public OneSeatedTablePanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER));

        SeatButton chair = new SeatButton("1", 40, 40, Color.BLACK, Color.white, customFont.getRobotoFonts().get(0).deriveFont(13));
        add(chair);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OneSeatedTablePanel frame = new OneSeatedTablePanel();
            frame.setVisible(true);
        });
    }
}
