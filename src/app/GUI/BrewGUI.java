package app.GUI;

import app.Components.NavbarPanel;
import app.InitFont.CustomFont;
import app.Object.Account;
import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import com.formdev.flatlaf.FlatDarkLaf;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.swing.FontIcon;

// @important
// old Color: 161, 103, 37
// old OnClick Color: 196, 125, 44

public class BrewGUI extends JFrame implements MouseListener {
    public static Account acc;
    private MigLayout layout;
    private JPanel right;
    public static JPanel pageContainer;
    public static HomePage homePage;
    public static SellPage sellPage;
    public static ReceiptPage receiptPage;
    public static ProductPage productPage;
    public static CustomerPage customerPage;
    public static StatisticPage statisticPage;
    public static EmployeePage employeePage;
    private CustomFont customFont = new CustomFont();
    private JButton navbarButton;
    private JPanel slidePanel;
    private NavbarPanel optionBar;
    private boolean isOptionBarVisible = false;
    private JPanel infoBar;
    private Animator animator;
    public static String BREW_HOTLINE = "1234567898";

    // Function tạo GUI chính
    public BrewGUI(Account acc) {
        this.acc = acc;

        ImageIcon icon = new ImageIcon("asset/icon.png");
        setTitle("Brew");
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setIconImage(icon.getImage());
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
            public void timingEvent(float fraction) {
                double width, borderWidth;
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
                    optionBar.timingEventCloseButton(optionBar.customerButton, 30f, 22f, 13f, fraction);
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
                    optionBar.timingEventShowButton(optionBar.customerButton, 24f, 10f, 10f, fraction);
                    optionBar.timingEventShowButton(optionBar.statisticsButton, 24f, 10f, 10f, fraction);
                    optionBar.timingEventShowButton(optionBar.employeeButton, 24f, 10f, 10f, fraction);
                }
                layout.setComponentConstraints(optionBar, "w " + width + "!, spany2");
                optionBar.revalidate();
            }

            @Override
            public void end() {
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
                if (!animator.isRunning()) { animator.start(); }
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

        optionBar = new NavbarPanel(acc);
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
        infoBar.setBounds(10, 10, 200, 30);
        infoBar.setBackground(new Color(164, 56, 32));
        infoBar.setLayout(new BorderLayout());

        // --- Nút mũi tên ---
        navbarButton = new JButton(">");
        navbarButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        navbarButton.setForeground(Color.BLACK);
        navbarButton.setBackground(Color.WHITE);
        navbarButton.setFocusPainted(false);
        navbarButton.setPreferredSize(new Dimension(100, 25));
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

        infoBar.add(Box.createVerticalStrut(10));
        infoBar.add(navbarButton, BorderLayout.WEST); // căn trái
        infoBar.add(Box.createVerticalStrut(10));

        // Panel bên phải (thông tin nhân viên)
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        userPanel.setOpaque(false);

        JLabel lblDateTime = new JLabel("Đang tải...");
        lblDateTime.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.BOLD, 12));
        lblDateTime.setForeground(Color.WHITE);

        JLabel lblEmployeeName = new JLabel(acc.getEmployee().getFirstName() + " " + acc.getEmployee().getLastName());
        lblEmployeeName.setForeground(Color.WHITE);
        lblEmployeeName.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.BOLD, 12));

        JLabel lblRole = new JLabel(acc.getEmployee().getRole());
        lblRole.setForeground(Color.LIGHT_GRAY);
        lblRole.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));

        // Avatar + chấm trạng thái trực tuyến
        ImageIcon avatarIcon = new ImageIcon("asset/1.png");
        Image img = avatarIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel avatarLabel = new JLabel(scaledIcon);
        avatarLabel.setPreferredSize(new Dimension(40, 40));
        // avatarLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        avatarLabel.setOpaque(false);
        avatarLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel statusDot = new JLabel("\u2B24"); // chấm tròn Unicode
        statusDot.setForeground(new Color(46, 204, 113)); // xanh lá cây sáng
        statusDot.setFont(new Font("Dialog", Font.PLAIN, 10));
        statusDot.setToolTipText("Đang trực tuyến");

        JPanel avatarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        avatarPanel.setOpaque(false);
        avatarPanel.add(avatarLabel);
        avatarPanel.add(Box.createHorizontalStrut(4));
        avatarPanel.add(statusDot);

        // --- Nút Đăng xuất (gradient tùy chỉnh) ---
        // Sử dụng thư viện Ikonli để dùng icon với Feather.LOG_OUT, độ lớn 15, màu đen
        JButton btnLogout = new JButton("Đăng xuất", FontIcon.of(Feather.LOG_OUT, 15, Color.BLACK)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Gradient từ vàng cam sang đỏ
                Color color1 = new Color(255, 165, 0); // vàng cam
                Color color2 = new Color(231, 76, 60); // đỏ
                g2d.setPaint(new GradientPaint(0, 0, color1, 0, getHeight(), color2));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10); // bo góc 10px
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Dialog", Font.BOLD, 12));
        btnLogout.setFocusPainted(false);
        btnLogout.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        btnLogout.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btnLogout.setContentAreaFilled(false); // để paintComponent tự vẽ
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Optional: hover đổi gradient sang đỏ đậm hơn
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogout.setForeground(Color.WHITE); // giữ chữ trắng
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogout.setForeground(Color.WHITE);
            }
        });

        // Thêm nút Logout vào userPanel
        userPanel.add(lblDateTime);
        userPanel.add(lblRole);
        userPanel.add(lblEmployeeName);
        userPanel.add(avatarPanel);
        userPanel.add(Box.createHorizontalStrut(10));
        userPanel.add(btnLogout);

        infoBar.setLayout(new BorderLayout());
        infoBar.add(navbarButton, BorderLayout.WEST);
        infoBar.add(userPanel, BorderLayout.EAST);

        // Khởi tạo trang chứa
        this.pageContainer = new JPanel();
        this.pageContainer.setPreferredSize(new Dimension(200, 200));
        this.pageContainer.setLayout(new CardLayout());

        this.homePage = new HomePage(); // Khởi tạo trang Trang chủ
        this.sellPage = new SellPage(); // Khởi tạo trang Bán hàng
        this.receiptPage = new ReceiptPage(); // Khởi tạo trang Hóa đơn
        this.productPage = new ProductPage(); // Khởi tạo trang Sản phẩm
        this.customerPage = new CustomerPage(); // Khởi tạo trang Giảm giá
        this.statisticPage = new StatisticPage(); // Khởi tạo trang Thống kê
        this.employeePage = new EmployeePage(); // Khởi tạo trang Nhân viên

        pageContainer.add(homePage, "Home Page");
        pageContainer.add(sellPage, "Sell Page");
        pageContainer.add(receiptPage, "Receipt Page");
        pageContainer.add(productPage, "Product Page");
        pageContainer.add(customerPage, "Customer Page");
        pageContainer.add(statisticPage, "Statistic Page");
        pageContainer.add(employeePage, "Employee Page");

        this.right.add(pageContainer, BorderLayout.CENTER);
        this.right.add(infoBar, BorderLayout.NORTH);
        this.right.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height);

        this.add(this.right, "w 100%");

        // ---- Cập nhật thời gian thực ----
        Timer timer = new Timer(1000, e -> {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
            lblDateTime.setText(now.format(fmt));
        });
        timer.start();
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
//        JButton enteredButton = (JButton) e.getComponent();
//        enteredButton.setForeground(Color.white);// Thay đổi màu khi hover
//        enteredButton.setBackground(new Color(70, 33, 26));
//        enteredButton.setFocusPainted(false);
//        enteredButton.setBorder(BorderFactory.createLineBorder(new Color(70, 33, 26)));
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        JButton exitedButton = (JButton) e.getComponent();
//        exitedButton.setForeground(Color.black);// Thay đổi màu khi hover
//        exitedButton.setBackground(Color.white);
//        exitedButton.setFocusPainted(false);
//        exitedButton.setBorder(BorderFactory.createLineBorder(Color.white));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Mongo.getConnection();
            BrewGUI devCafeGUI = new BrewGUI(new Account());
            devCafeGUI.setVisible(true);
        });
    }
}