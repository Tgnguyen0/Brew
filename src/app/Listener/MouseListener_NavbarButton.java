package app.Listener;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;

import org.kordamp.ikonli.swing.FontIcon;
import app.Components.NavbarButton;

public class MouseListener_NavbarButton implements MouseListener {
    private String description;
    private int iconSize;
    private int buttonWidth;
    private int buttonHeight;
    private int fontStyle;

    public MouseListener_NavbarButton() {}

    @Override
    public void mouseClicked(MouseEvent e) {
        // Nếu bạn cần xử lý click, thêm vào đây
        NavbarButton clickedButton = (NavbarButton) e.getComponent();
        System.out.println("Clicked button: " + clickedButton.getText());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Hiệu ứng khi nhấn giữ chuột (tối màu nút)
        NavbarButton btn = (NavbarButton) e.getComponent();
        btn.setBackground(new Color(50, 20, 20));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Khi thả chuột, trả lại màu hover
        NavbarButton btn = (NavbarButton) e.getComponent();
        btn.setBackground(new Color(70, 33, 26));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        NavbarButton enteredButton = (NavbarButton) e.getComponent();
        FontIcon icon = FontIcon.of(enteredButton.getIkon(), enteredButton.getIconSize(), Color.WHITE);
        enteredButton.setIcon(icon);
        enteredButton.setForeground(Color.white);
        enteredButton.setBackground(new Color(70, 33, 26));
        enteredButton.setFocusPainted(false);
        enteredButton.setBorderPainted(false);

        if (enteredButton.isShowMenu()) {
            enteredButton.setBorder(BorderFactory.createLineBorder(new Color(70, 33, 26)));
            enteredButton.setIconTextGap(10);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        NavbarButton exitedButton = (NavbarButton) e.getComponent();
        FontIcon icon = FontIcon.of(exitedButton.getIkon(), exitedButton.getIconSize(), Color.WHITE);
        exitedButton.setIcon(icon);
        exitedButton.setForeground(Color.white);
        exitedButton.setBackground(new Color(164, 56, 32));
        exitedButton.setFocusPainted(false);
        exitedButton.setBorderPainted(false);

        if (exitedButton.isShowMenu()) {
            exitedButton.setBorder(BorderFactory.createLineBorder(new Color(164, 56, 32)));
            exitedButton.setIconTextGap(10);
        }
    }
}
