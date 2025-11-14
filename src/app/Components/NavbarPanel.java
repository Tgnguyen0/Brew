package app.Components;

import java.awt.*;

import javax.swing.*;

import app.Object.Account;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.swing.FontIcon;

import app.InitFont.CustomFont;
import app.Listener.ActionListener_NavbarPanel;
import net.miginfocom.swing.MigLayout;

public class NavbarPanel extends JPanel {
    private CustomFont customFont = new CustomFont();
    private ActionListener_NavbarPanel action = new ActionListener_NavbarPanel();
    public JLabel logoNameLabel;
    public NavbarButton homeButton;
    public NavbarButton sellButton;
    public NavbarButton receiptButton;
    public NavbarButton productButton;

    // ĐÃ ĐỔI TÊN: promotionRateButton -> customerButton
    public NavbarButton customerButton;

    public NavbarButton statisticsButton;
    public NavbarButton employeeButton;
    // private final Animator animator;
    private boolean showMenu = true;
    private Account acc;

    public NavbarPanel(Account acc) {
        this.acc = acc;

        initNavbarButton();
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black));
        this.setLayout(new MigLayout("wrap, fillx, insets 0", "[fill]", "[fill, 60!]0[fill, 40!]"));
    }

    public void setShowMenu(boolean showMenu) {
        this.showMenu = showMenu;
        this.homeButton.setShowMenu(showMenu);
        this.sellButton.setShowMenu(showMenu);
        this.receiptButton.setShowMenu(showMenu);
        this.productButton.setShowMenu(showMenu);

        // ĐÃ CẬP NHẬT: promotionRateButton -> customerButton
        this.customerButton.setShowMenu(showMenu);

        this.statisticsButton.setShowMenu(showMenu);
        this.employeeButton.setShowMenu(showMenu);
    }

    public boolean isShowMenu() {
        return this.showMenu;
    }

    public void initNavbarButton() {
        FontIcon batteryIcon = FontIcon.of(Feather.BATTERY_CHARGING, 50, Color.WHITE);
        logoNameLabel = new JLabel("Brew", batteryIcon, SwingConstants.LEFT); // Tạo tên logo
        logoNameLabel.setPreferredSize(new Dimension(200, 50));
        logoNameLabel.setIconTextGap(10);
        logoNameLabel.setForeground(Color.WHITE);
        logoNameLabel.setBackground(new Color(164, 56, 32));
        logoNameLabel.setVerticalAlignment(SwingConstants.CENTER);
        logoNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoNameLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 30));
        logoNameLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.white));
        this.add(logoNameLabel);

        // Tạo Nút dẫn đến trang chủ
        homeButton = new NavbarButton("Trang Chủ", Feather.HOME,
                24, 200, 40, Font.PLAIN, 12, 10);
        homeButton.addActionListener(action);
        this.add(homeButton);

        // Tạo Nút dẫn đến trang bán hàng
        sellButton = new NavbarButton("Bán Hàng", Feather.SHOPPING_CART,
                24, 200, 35, Font.PLAIN, 12, 10);
        sellButton.addActionListener(action);
        this.add(sellButton);

        receiptButton = new NavbarButton("Hóa Đơn", Feather.CREDIT_CARD,
                24, 200, 35, Font.PLAIN, 12, 10);
        receiptButton.addActionListener(action);
        this.add(receiptButton);

        productButton = new NavbarButton("Sản Phẩm", Feather.BOX,
                24, 200, 35, Font.PLAIN, 12, 10);
        productButton.addActionListener(action);
        productButton.setEnabled(acc.getRole().equals("Admin") || acc.getRole().equals("QuanLy"));

        this.add(productButton);

        // ĐÃ ĐỔI: "Khuyến Mại" -> "Khách Hàng", Feather.DISC -> Feather.USERS, promotionRateButton -> customerButton
        customerButton = new NavbarButton("Khách Hàng", Feather.USERS,
                24, 200, 35, Font.PLAIN, 12, 10);
        customerButton.addActionListener(action);
        this.add(customerButton);

        statisticsButton = new NavbarButton("Thống Kê", Feather.BAR_CHART,
                24, 200, 35, Font.PLAIN, 12, 10);
        statisticsButton.addActionListener(action);
        statisticsButton.setEnabled(acc.getRole().equals("Admin") || acc.getRole().equals("QuanLy"));
        this.add(statisticsButton);

        employeeButton = new NavbarButton("Nhân Viên", Feather.USER,
                24, 200, 35, Font.PLAIN, 12, 10);
        employeeButton.addActionListener(action);
        employeeButton.setEnabled(acc.getRole().equals("Admin") || acc.getRole().equals("QuanLy"));
        this.add(employeeButton);
    }

    public void timingEventCloseButton(NavbarButton button, double iconWidth, double iconTextGap,
                                       double borderRightWidth, double fraction) {
        FontIcon icon = FontIcon.of(button.getIkon(), (int) (iconWidth * (fraction)),
                Color.WHITE);
        button.setIcon(icon);
        button.setIconSize((int) iconWidth);
        button.setIconTextGap((int) (iconTextGap * fraction));
        button.setBorder(BorderFactory.createEmptyBorder(0, (int) (borderRightWidth * (fraction)), 0, 0));
    }

    public void timingEventShowButton(NavbarButton button, double iconWidth, double iconTextGap,
                                      double borderRightWidth, double fraction) {
        double borderWidth = (0.5f - fraction >= 0) ? 0.5f - fraction : 0;
        FontIcon icon = FontIcon.of(button.getIkon(), (int) (24f * (fraction)),
                Color.WHITE);
        button.setIcon(icon);
        button.setIconSize((int) iconWidth);
        button.setIconTextGap((int) (10f * fraction));
        button.setBorder(BorderFactory.createEmptyBorder(0, (int) (10f * (borderWidth)), 0, 0));
    }
}