package app.Components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import app.InitFont.CustomFont;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {
    private CustomFont customFont = new CustomFont();

    public CustomTableCellRenderer() {
        setHorizontalAlignment(SwingConstants.CENTER);
        setForeground(Color.black);
        setBackground(Color.white);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        // Call the superclass method to get the default renderer
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                column);

        cell.setBackground((row % 2 == 0 ? new Color(224, 224, 224) : Color.white));
        ((JLabel) cell).setHorizontalAlignment((column == 0) ? SwingConstants.CENTER : SwingConstants.CENTER);
        cell.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));

        return cell;
    }
}
