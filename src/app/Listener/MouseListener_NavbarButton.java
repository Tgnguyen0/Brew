package app.Listener;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.swing.FontIcon;

import app.Components.NavbarButton;

public class MouseListener_NavbarButton implements MouseListener {
    private String description;
    private Ikon ikon;
    private int iconSize;
    private int buttonWidth;
    private int buttonHeight;
    private int fontStyle;

    public MouseListener_NavbarButton() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        NavbarButton enteredButton = (NavbarButton) e.getComponent();
        // Thay đổi màu khi hover
        FontIcon icon = FontIcon.of(enteredButton.getIkon(), enteredButton.getIconSize(), Color.WHITE);
        enteredButton.setIcon(icon);
        enteredButton.setForeground(Color.white); // Thay đổi màu khi hover
        enteredButton.setBackground(new Color(70, 33, 26));
        enteredButton.setFocusPainted(false);
        enteredButton.setBorderPainted(false);
        if (enteredButton.isShowMenu()) {
            enteredButton.setBorder(BorderFactory.createLineBorder(new Color(70, 33, 26)));
            enteredButton.setIconTextGap(10);
        } else {
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        NavbarButton exitedButton = (NavbarButton) e.getComponent();
        // Thay đổi màu khi hover
        FontIcon icon = FontIcon.of(exitedButton.getIkon(), exitedButton.getIconSize(), Color.WHITE);
        exitedButton.setIcon(icon);
        exitedButton.setForeground(Color.white); // Thay đổi màu khi hover
        exitedButton.setBackground(new Color(164, 56, 32));
        exitedButton.setFocusPainted(false);
        exitedButton.setBorderPainted(false);
        if (exitedButton.isShowMenu()) {
            exitedButton.setBorder(BorderFactory.createLineBorder(new Color(164, 56, 32)));
            exitedButton.setIconTextGap(10);
        } else {
        }
    }

}
