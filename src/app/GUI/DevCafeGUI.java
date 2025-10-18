package app.GUI;

import app.Components.NavbarPanel;
import app.InitFont.CustomFont;
import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import com.formdev.flatlaf.FlatDarkLaf;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

// @important
// setBackground(new Color(70, 33, 26));
// setBackground(new Color(164, 56, 32));
// setBackground(new Color(241, 211, 178));

public class DevCafeGUI extends JFrame implements MouseListener {
    private MigLayout layout;
    private JPanel right;
    public static JPanel pageContainer;
    private HomePage homePage;
    private SellPage sellPage;
    private ReceiptPage receiptPage;
    private ProductPage productPage;
    private PromotionPage promotionPage;
    private StatisticPage statisticPage;
    private EmployeePage employeePage;
    private CustomFont customFont = new CustomFont();
    private JButton navbarButton;
    private JPanel slidePanel;
    private NavbarPanel optionBar;
    private boolean isOptionBarVisible = false;
    private JPanel infoBar;
    private Animator animator;

    // Function tạo GUI chính
    public DevCafeGUI() {
        ImageIcon icon = new ImageIcon("asset/icon.png");
        setTitle("Dev Cafe");
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setIconImage(icon.getImage());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        layout = new MigLayout("fill", "0[]0[100%, fill]0", "0[fill, top]0");
        setLayout(layout);

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        navbarInit();
        guiUserBarInit();

        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                if (!optionBar.isShowMenu()) {}
            }

            @Override
            public void timingEvent(float fraction) {
                double width;
                System.out.println(String.valueOf(fraction));
                if (optionBar.isShowMenu()) {
                    width = 60 + (140f * (1f - fraction)); // 30
                    optionBar.logoNameLabel
                            .setBorder(BorderFactory.createMatteBorder(0, (int) (5f * (fraction)), 0,
                                    (int) (5f * (fraction)), new Color(164, 56, 32)));

                    optionBar.timingEventCloseButton(optionBar.homeButton, 30f, 22f, 13f, fraction);
                    optionBar.timingEventCloseButton(optionBar.sellButton, 30f, 22f, 13f, fraction);
                    optionBar.timingEventCloseButton(optionBar.receiptButton, 30f, 22f, 13f, fraction);
                    optionBar.timingEventCloseButton(optionBar.productButton, 30f, 22f, 13f, fraction);
                    optionBar.timingEventCloseButton(optionBar.promotionRateButton, 30f, 22f, 13f, fraction);
                    optionBar.timingEventCloseButton(optionBar.statisticsButton, 30f, 22f, 13f, fraction);
                    optionBar.timingEventCloseButton(optionBar.employeeButton, 30f, 22f, 13f, fraction);
                } else {
                    width = 60 + (140f * fraction); // 30
                    optionBar.logoNameLabel
                            .setBorder(BorderFactory.createMatteBorder(0, (int) (5f * (1f - fraction)), 0,
                                    (int) (5f * (1f - fraction)), new Color(164, 56, 32)));

                    optionBar.timingEventShowButton(optionBar.homeButton, 24f, 10f, 10f, fraction);
                    optionBar.timingEventShowButton(optionBar.sellButton, 24f, 10f, 10f, fraction);
                    optionBar.timingEventShowButton(optionBar.receiptButton, 24f, 10f, 10f, fraction);
                    optionBar.timingEventShowButton(optionBar.productButton, 24f, 10f, 10f, fraction);
                    optionBar.timingEventShowButton(optionBar.promotionRateButton, 24f, 10f, 10f, fraction);
                    optionBar.timingEventShowButton(optionBar.statisticsButton, 24f, 10f, 10f, fraction);
                    optionBar.timingEventShowButton(optionBar.employeeButton, 24f, 10f, 10f, fraction);
                }
                layout.setComponentConstraints(optionBar, "w " + width + "!, spany2");
                optionBar.revalidate();
            }

            @Override
            public void end() {
                if (optionBar.isShowMenu()) {}

                optionBar.setShowMenu(!optionBar.isShowMenu());
            }
        };
        animator = new Animator(500, target);
        animator.setResolution(0);
        animator.setDeceleration(0.5f);
        animator.setAcceleration(0.5f);
        navbarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!animator.isRunning()) {
                    animator.start();
                }
                optionBar.setEnableMenu(false);
                if (optionBar.isShowMenu()) {
                }
            }
        });

        // add(layeredPane);
        // updateTime(); // Cập nhật tg
        // startTimer(); // Khởi động bộ đếm thời gian để cập nhật liên tục
    }

    // Tạo down menu GUI cho dev cafe
    public void navbarInit() {
        slidePanel = new JPanel();
        slidePanel.setBounds(-220, 0, 265,
                Toolkit.getDefaultToolkit().getScreenSize().height);
        slidePanel.setLocation(-220, 0);
        slidePanel.setBackground(new Color(164, 56, 32));

        optionBar = new NavbarPanel();
        optionBar.setBackground(new Color(164, 56, 32));
        optionBar.setBounds(0, 0, slidePanel.getWidth(), slidePanel.getHeight());
        optionBar.setBorder(BorderFactory.createLineBorder(new Color(164, 56, 32)));
        this.add(optionBar, "w 200!, spany 2");

        JPanel empty = new JPanel();
        empty.setPreferredSize(new Dimension(110, 30));
        empty.setOpaque(false);
        optionBar.add(empty);
    }

    public void guiUserBarInit() {
        this.right = new JPanel();
        this.right.setPreferredSize(new Dimension(660, 400));
        this.right.setLayout(new BorderLayout());

        infoBar = new JPanel();
        infoBar.setPreferredSize(new Dimension(670, 35));
        infoBar.setBounds(10, 10, 50, 30);
        infoBar.setBackground(new Color(164, 56, 32));
        infoBar.setLayout(new BorderLayout());

        navbarButton = new JButton(">"); // ☰
        navbarButton.setBounds(5, 3, 45, 30);
        navbarButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        navbarButton.setForeground(Color.BLACK);
        navbarButton.setBackground(Color.WHITE);
        navbarButton.setFocusPainted(false);
        navbarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isOptionBarVisible) {
                    navbarButton.setText("<");
                } else {
                    navbarButton.setText(">");
                }
            }
        });
        infoBar.add(navbarButton);

        JPanel userRelatedBar = new JPanel();
        userRelatedBar.setOpaque(false);
        userRelatedBar.setPreferredSize(new Dimension(385, 35));

        JButton changePassButton = new JButton("Đổi mật khẩu");
        changePassButton.setPreferredSize(new Dimension(110, 25));
        changePassButton.setFont(customFont.getRobotoFonts().get(0));
        changePassButton.setForeground(new Color(164, 56, 32));
        changePassButton.setBackground(new Color(241, 211, 178));
        changePassButton.addMouseListener(this);
        userRelatedBar.add(changePassButton);

        JButton signOutButton = new JButton("Đăng xuất");
        signOutButton.setPreferredSize(new Dimension(110, 25));
        signOutButton.setFont(customFont.getRobotoFonts().get(0));
        signOutButton.setForeground(new Color(164, 56, 32));
        signOutButton.setBackground(new Color(241, 211, 178));
        signOutButton.addMouseListener(this);
        userRelatedBar.add(signOutButton);

        JLabel accountNameLabel = new JLabel("Nguyễn Nhật Tấn - Dev");
        accountNameLabel.setPreferredSize(new Dimension(140, 25));
        accountNameLabel.setFont(customFont.getRobotoFonts().get(0));
        accountNameLabel.setForeground(new Color(255, 213, 146));
        userRelatedBar.add(accountNameLabel);
        infoBar.add(Box.createHorizontalStrut(170));

        // Khởi tạo trang chứa
        this.pageContainer = new JPanel();
        this.pageContainer.setPreferredSize(new Dimension(200, 200));
        this.pageContainer.setLayout(new CardLayout());

        this.homePage = new HomePage(); // Khởi tạo trang Trang chủ
        this.sellPage = new SellPage(); // Khởi tạo trang Bán hàng
        this.receiptPage = new ReceiptPage(); // Khởi tạo trang Hóa đơn
        this.productPage = new ProductPage(); // Khởi tạo trang Sản phẩm
        this.promotionPage = new PromotionPage(); // Khởi tạo trang Giảm giá
        this.statisticPage = new StatisticPage(); // Khởi tạo trang Thống kê
        this.employeePage = new EmployeePage(); // Khởi tạo trang Nhân viên

        pageContainer.add(homePage, "Home Page");
        pageContainer.add(sellPage, "Sell Page");
        pageContainer.add(receiptPage, "Receipt Page");
        pageContainer.add(productPage, "Product Page");
        pageContainer.add(promotionPage, "Promotion Page");
        pageContainer.add(statisticPage, "Statistic Page");
        pageContainer.add(employeePage, "Employee Page");

        this.right.add(pageContainer, BorderLayout.CENTER);
        this.right.add(infoBar, BorderLayout.NORTH);
        this.right.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height);

        this.add(this.right, "w 100%");
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
        JButton enteredButton = (JButton) e.getComponent();
        enteredButton.setForeground(Color.white);// Thay đổi màu khi hover
        enteredButton.setBackground(new Color(70, 33, 26));
        enteredButton.setFocusPainted(false);
        enteredButton.setBorder(BorderFactory.createLineBorder(new Color(70, 33, 26)));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JButton exitedButton = (JButton) e.getComponent();
        exitedButton.setForeground(Color.black);// Thay đổi màu khi hover
        exitedButton.setBackground(Color.white);
        exitedButton.setFocusPainted(false);
        exitedButton.setBorder(BorderFactory.createLineBorder(Color.white));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Mongo.getConnection();
            DevCafeGUI devCafeGUI = new DevCafeGUI();
            devCafeGUI.setVisible(true);
        });
    }
}