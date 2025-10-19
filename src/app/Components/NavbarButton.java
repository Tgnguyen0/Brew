package app.Components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.swing.FontIcon;

import app.InitFont.CustomFont;
import app.Listener.MouseListener_NavbarButton;

public class NavbarButton extends JButton {
    private CustomFont customFont = new CustomFont();
    private MouseListener_NavbarButton mouseListener = new MouseListener_NavbarButton();
    private String description;
    private Ikon ikon;
    private int iconSize;
    private int buttonWidth;
    private int buttonHeight;
    private int fontStyle;
    private int fontSize;
    private int iconTextGap;
    private boolean showMenu = true;

    public NavbarButton(String description, Ikon ikon, int iconSize, int width, int height, int fontStyle, int fontSize,
                        int iconTextGap) {
        this.description = description;
        this.ikon = ikon;
        this.iconSize = iconSize;
        this.buttonWidth = width;
        this.buttonHeight = height;
        this.fontStyle = fontStyle;
        this.fontSize = fontSize;
        this.iconTextGap = iconTextGap;
        init(this.description, this.ikon, this.iconSize, this.buttonWidth, this.buttonHeight, this.fontStyle,
                this.fontSize, this.iconTextGap);
    }

    private void init(String description, Ikon ikon, int iconSize, int width, int height, int fontStyle, int fontSize,
                      int iconTextGap) {
        FontIcon icon = FontIcon.of(ikon, iconSize, Color.WHITE);
        setText(description);
        setPreferredSize(new Dimension(width, height));
        setFont(customFont.getRobotoFonts().get(0).deriveFont(fontStyle, fontSize));
        setForeground(Color.WHITE);
        setBackground(new Color(164, 56, 32));
        setOpaque(true);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(true);
        setHorizontalAlignment(SwingConstants.LEFT);
        setIconTextGap(iconTextGap);
        setBorder(BorderFactory.createLineBorder(Color.white));
        setIcon(icon);
        addMouseListener(mouseListener);
    }

    public Ikon getIkon() {
        return this.ikon;
    }

    public void setIconSize(int iconSize) {
        this.iconSize = iconSize;
    }

    public int getIconSize() {
        return this.iconSize;
    }

    public int getIconTextGap() {
        return this.iconTextGap;
    }

    public void setIconTextGap(int iconTextGap) {
        this.iconTextGap = iconTextGap;
    }

    public void setShowMenu(boolean showMenu) {
        this.showMenu = showMenu;
    }

    public boolean isShowMenu() {
        return this.showMenu;
    }
}
