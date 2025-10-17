package app.GUI;

import app.Components.NavbarPanel;
//import app.Connection.Mongo;
import app.InitFont.CustomFont;
//import app.SaveToFile.ReadSaveFromFile;
import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

// @important
// old Color: 161, 103, 37
// old OnClick Color: 196, 125, 44

public class DevCafeGUI extends JFrame implements MouseListener {
    private MigLayout layout;
    private JLabel timeLabel;
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
//    private ReadSaveFromFile s = new ReadSaveFromFile();
    private JButton navbarButton;
    private JPanel slidePanel;
    private NavbarPanel optionBar;
    private boolean isOptionBarVisible = false;
    private JPanel infoBar;
    private int menuWidth = 220;
    private int offsetY;
    private Point endPointSlidePanel;
    private Animator animator;
    // private JLayeredPane layeredPane;

    // Function tạo GUI chính
    public DevCafeGUI() {
        ImageIcon icon = new ImageIcon("asset/icon.png"); // For vscode
        setTitle("Dev Cafe");
        // setSize(new Dimension(1200, 700));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setIconImage(icon.getImage());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // setBackground(new Color(96, 69, 113)); // getContentPane().setBackground(new
        // Color(225, 203, 177));
        setResizable(true);
        layout = new MigLayout("fill", "0[]0[100%, fill]0", "0[fill, top]0");
        // setLayout(new BorderLayout());
        setLayout(layout);

        // SET GIAO DIỆN TRỰC TIẾP KHÔNG QUA MAIN
        /*
         * try {
         * for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
         * if ("Nimbus".equals(info.getName())) {
         * UIManager.setLookAndFeel(info.getClassName());
         *
         * // Cấu hình thuộc tính Nimbus
         * UIManager.put("control", new javax.swing.plaf.ColorUIResource(255, 255,
         * 255)); // Màu nền
         * UIManager.put("nimbusBase", new javax.swing.plaf.ColorUIResource(255, 255,
         * 255)); // Màu cơ bản
         * UIManager.put("nimbusBorder", new javax.swing.plaf.ColorUIResource(0, 112,
         * 255)); // Màu viền
         * UIManager.put("nimbusLightBackground", new
         * javax.swing.plaf.ColorUIResource(255, 255, 255)); // Màu
         * // nền
         * // sáng
         * UIManager.put("nimbusFocus", new javax.swing.plaf.ColorUIResource(0, 112,
         * 255)); // Màu focus
         * UIManager.put("textForeground", new Color(0, 112, 255)); // Màu chữ
         * UIManager.put("ComboBox.foreground", new Color(0, 112, 255)); // Màu chữ cho
         * JComboBox
         * UIManager.put("ComboBox.background", new Color(255, 255, 255));
         * UIManager.put("JCalendar.border", new Color(255, 255, 255));
         *
         * // Đặt màu nền và màu chữ khi chọn cho JTextField
         * UIManager.put("TextField.selectionBackground", new Color(0, 112, 255)); //
         * Màu nền khi chọn
         * UIManager.put("TextField.selectionForeground", new Color(255, 255, 255)); //
         * Màu chữ khi chọncho
         * // JComboBox
         *
         * break;
         * }
         * }
         * } catch (Exception ex) {
         * ex.printStackTrace();
         * }
         */

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // layeredPane = new JLayeredPane();
        // layeredPane.setBounds(0, 0,
        // Toolkit.getDefaultToolkit().getScreenSize().width,
        // Toolkit.getDefaultToolkit().getScreenSize().height);

        navbarInit();
        guiUserBarInit();

        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                if (!optionBar.isShowMenu()) {
                    // optionBar.homeButton.setForeground(new Color(0, 0, 0, 255));
                }
            }

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
                if (optionBar.isShowMenu()) {
                    // optionBar.homeButton.setForeground(new Color(0, 0, 0, 255));
                }

                optionBar.setShowMenu(!optionBar.isShowMenu());
                // optionBar.homeButton.setForeground(new Color(0, 0, 0, 255));
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
        slidePanel.setBounds(-menuWidth, 0, 265,
                Toolkit.getDefaultToolkit().getScreenSize().height);
        slidePanel.setLocation(-menuWidth, 0);
        // slidePanel.setLayout(null);
        slidePanel.setBackground(new Color(164, 56, 32));
        // this.add(slidePanel, "");

        // optionBar = new JPanel() {
        // @Override
        // protected void paintComponent(Graphics g) {
        // String imagePath = "dev_cafe/asset/menu-banner.png"; // Path to your GIF
        // image file
        // File imageFile = new File(imagePath);

        // // Chèn ảnh vào Option menu-
        // try {
        // // Đọc ảnh từ file
        // Image image = ImageIO.read(imageFile);

        // // Tạo icon cho ảnh
        // int newWidth = getWidth(); // Get the width of the panel
        // int newHeight = getHeight(); // Get the height of the panel
        // Image scaledImage = image.getScaledInstance(newWidth, newHeight,
        // Image.SCALE_SMOOTH);
        // g.drawImage(scaledImage, 0, 0, null);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }

        // };
        optionBar = new NavbarPanel();
        optionBar.setBackground(new Color(164, 56, 32));
        // optionBar.setLocation(-optionBar.getWidth(), optionBar.getY());
        optionBar.setBounds(0, 0, slidePanel.getWidth(), slidePanel.getHeight());
        // optionBar.setVisible(true);
        // Border lineBorder = BorderFactory.createMatteBorder(0, 0, 0, 5, new Color(44,
        // 31, 93)); // Tạo MatteBorder với
        optionBar.setBorder(BorderFactory.createLineBorder(new Color(164, 56, 32)));
        // slidePanel.add();
        this.add(optionBar, "w 200!, spany 2");

        /*
         * SwingUtilities.invokeLater(() -> {
         * slidePanel.setLocation(-slidePanel.getWidth(), 0);
         * });
         */

        JPanel empty = new JPanel();
        empty.setPreferredSize(new Dimension(110, 30));
        empty.setOpaque(false);
        optionBar.add(empty);

        // String imagePath = "dev_cafe/asset/dev_cafe_icon.gif"; // for vs code
        // // String imagePath = "asset/dev_cafe.png"; // for eclipse, intelj
        // // File imageFile = new File(imagePath);

        // ImageIcon imageIcon = new ImageIcon(imagePath);

        // // Tạo imageLabel cho ảnh
        // JLabel imageLabel = new JLabel(imageIcon);
        // imageLabel.setPreferredSize(new Dimension(110, 77));

        // optionBar.add(imageLabel, BorderLayout.WEST);

        // Chèn ảnh vào Option menu
        /*
         * try {
         * // Đọc ảnh từ file
         * Image image = ImageIO.read(imageFile);
         *
         * // Tạo icon cho ảnh
         * ImageIcon imageIcon = new ImageIcon(imagePath);
         *
         * // Tạo imageLabel cho ảnh
         * JLabel imageLabel = new JLabel(imageIcon);
         *
         * // Thêm imageLabel vào optionMenu
         * optionBar.add(imageLabel, BorderLayout.WEST);
         * } catch (IOException e) {
         * e.printStackTrace();
         * }
         */

        // layeredPane.add(slidePanel, JLayeredPane.POPUP_LAYER);
    }

    public void guiUserBarInit() {
        this.right = new JPanel();
        this.right.setPreferredSize(new Dimension(660, 400));
        this.right.setLayout(new BorderLayout());

        infoBar = new JPanel() {
            // @Override
            // protected void paintComponent(Graphics g) {
            // /*
            // * super.paintComponent(g);
            // * Graphics2D g2d = (Graphics2D) g;
            // * int w = getWidth();
            // * int h = getHeight();
            // * GradientPaint gp = new GradientPaint(0, 0, new Color(79, 92, 133), 0, h,
            // new
            // * Color(104, 101, 133));
            // * g2d.setPaint(gp);
            // * g2d.fillRect(0, 0, w, h);
            // */

            // String imagePath = "dev_cafe/asset/infoBar-background.png"; // Path to your
            // GIF image file
            // File imageFile = new File(imagePath);

            // // Chèn ảnh vào Option menu
            // try {
            // // Đọc ảnh từ file
            // Image image = ImageIO.read(imageFile);

            // // Tạo icon cho ảnh
            // int newWidth = getWidth(); // Get the width of the panel
            // int newHeight = getHeight(); // Get the height of the panel.
            // Image scaledImage = image.getScaledInstance(newWidth, newHeight,
            // Image.SCALE_SMOOTH);
            // g.drawImage(scaledImage, 0, 0, null);
            // } catch (IOException e) {
            // e.printStackTrace();
            // }
            // }
        };
        infoBar.setPreferredSize(new Dimension(670, 35));
        infoBar.setBounds(10, 10, 50, 30);
        infoBar.setBackground(new Color(164, 56, 32));
        // infoBar.setBackground(new Color(51, 62, 116));
        infoBar.setLayout(new BorderLayout());
        // banner.setBorder(new LineBorder(Color.red, 3));

        navbarButton = new JButton(">"); // ☰
        navbarButton.setBounds(5, 3, 45, 30);
        // navbarButton.setPreferredSize(new Dimension(45, 30));
        navbarButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        navbarButton.setForeground(Color.BLACK);
        navbarButton.setBackground(Color.WHITE);
        navbarButton.setFocusPainted(false);
        // navbarButton.setBorderPainted(false);
        // navbarButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new
        // Color(255, 213, 146)));
        navbarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                offsetY = e.getY();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (isOptionBarVisible) {
                    navbarButton.setText("<");
                } else {
                    navbarButton.setText(">");
                }
            }
        });
        // navbarButton.addMouseMotionListener(new MouseMotionAdapter() {
        // @Override
        // public void mouseDragged(MouseEvent e) {
        // Point mouseOnScreen = SwingUtilities.convertPoint(navbarButton, e.getPoint(),
        // slidePanel);

        // int newY = mouseOnScreen.y - offsetY;

        // newY = Math.max(0, Math.min(newY, slidePanel.getHeight() -
        // navbarButton.getHeight()));

        // navbarButton.setLocation(navbarButton.getX(), newY);
        // }
        // });
        // navbarButton.addActionListener(e -> toggleNavBar());
        infoBar.add(navbarButton);

        JPanel userRelatedBar = new JPanel();
        userRelatedBar.setOpaque(false);
        userRelatedBar.setPreferredSize(new Dimension(385, 35));
        // userRelatedBar.setBackground(new Color(51, 62, 116));

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

        // // Tạo thanh ngày giờ
        // timeLabel = new JLabel();
        // timeLabel.setPreferredSize(new Dimension(140, 25));
        // timeLabel.setFont(customFont.getRobotoFonts().get(0));
        // timeLabel.setForeground(new Color(255, 213, 146));
        // infoBar.add(timeLabel, BorderLayout.EAST);
        // infoBar.add(userRelatedBar, BorderLayout.WEST);

        // Khởi tạo trang chứa
        this.pageContainer = new JPanel();
        this.pageContainer.setPreferredSize(new Dimension(200, 200));
        // this.pageContainer.setBackground(new Color(225, 203, 177));
        this.pageContainer.setLayout(new CardLayout());

        this.homePage = new HomePage(); // Khởi tạo trang Trang chủ
        this.sellPage = new SellPage(); // Khởi tạo trang Bán hàng
        this.receiptPage = new ReceiptPage(); // Khởi tạo trang Hóa đơn
        this.productPage = new ProductPage(); // Khởi tạo trang Sản phẩm
        this.promotionPage = new PromotionPage(); // Khởi tạo trang Giảm giá
        this.statisticPage = new StatisticPage(); // Khởi tạo trang Thống kê
        this.employeePage = new EmployeePage(); // Khởi tạo trang Nhân viên

        // sellPage.setOrderButtonListener(() -> {
        // // Khi nút orderButton được nhấn:
        // receiptButton.setEnabled(true); // Kích hoạt receiptButton

        // // Kiểm tra xem ReceiptPage đã được thêm vào chưa
        // if (receiptPage == null) {
        // receiptPage = new ReceiptPage(); // Tạo trang ReceiptPage mới
        // pageContainer.add(receiptPage, "Receipt Page"); // Thêm vào pageContainer
        // }

        // try {
        // Object o = s.ReadFile("dev_cafe/data/bill_details_data.txt");

        // if (o instanceof ArrayList<?>) {
        // receiptPage = new ReceiptPage();
        // pageContainer.add(receiptPage, "Receipt Page");
        // }

        // System.out.println("Import successfully!");
        // } catch (Exception ee) {
        // ee.printStackTrace();
        // }

        // // Chuyển đến trang Hóa đơn ngay lập tức nếu cần
        // CardLayout cardLayout = (CardLayout) pageContainer.getLayout();
        // cardLayout.show(pageContainer, "Receipt Page");
        // });

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

    // private void updateTime() {
    // SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    // String currentTime = sdf.format(new Date());
    // timeLabel.setText(currentTime);
    // }

    // private void startTimer() {
    // Timer timer = new Timer(1000, e -> updateTime());
    // timer.start();
    // }

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

    private void toggleNavBar() {
        int startX = isOptionBarVisible ? 0 : -menuWidth; // Vị trí bắt đầu
        int endX = isOptionBarVisible ? -menuWidth : 0; // Vị trí kết thúc

        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                int x = (int) (startX + (endX - startX) * fraction);
                optionBar.setLocation(x, 0);
                optionBar.repaint();
            }

            @Override
            public void end() {
                isOptionBarVisible = !isOptionBarVisible;
            }
        };

        Animator animator = new Animator(300, target); // 300ms
        animator.setResolution(1);
        animator.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Mongo.getConnection();
            DevCafeGUI devCafeGUI = new DevCafeGUI();
            devCafeGUI.setVisible(true);
        });
    }
}