package app.Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        // this.setVerticalAlignment(SwingConstants.CENTER);
        setIconTextGap(iconTextGap);
        // this.setMargin(new Insets(5, 10, 5, 10));
        setBorder(BorderFactory.createLineBorder(Color.white));
        setIcon(icon);
        addMouseListener(mouseListener);
        // this.addActionListener(this);
    }

    public NavbarButton() {
    }

    public NavbarButton(CustomFont customFont, MouseListener_NavbarButton mouseListener, String description, Ikon ikon,
                        int iconSize, int buttonWidth, int buttonHeight, int fontStyle, int fontSize, int iconTextGap) {
        this.customFont = customFont;
        this.mouseListener = mouseListener;
        this.description = description;
        this.ikon = ikon;
        this.iconSize = iconSize;
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;
        this.fontStyle = fontStyle;
        this.fontSize = fontSize;
        this.iconTextGap = iconTextGap;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Ikon getIkon() {
        return this.ikon;
    }

    public int getButtonWidth() {
        return this.buttonWidth;
    }

    public void setButtonWidth(int buttonWidth) {
        this.buttonWidth = buttonWidth;
    }

    public int getButtonHeight() {
        return this.buttonHeight;
    }

    public void setButtonHeight(int buttonHeight) {
        this.buttonHeight = buttonHeight;
    }

    public int getFontStyle() {
        return this.fontStyle;
    }

    public void setFontStyle(int fontStyle) {
        this.fontStyle = fontStyle;
    }

    public void setIconSize(int iconSize) {
        this.iconSize = iconSize;
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
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
