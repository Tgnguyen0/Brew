package app.GUI;

import app.DAO.DAO_Employee;
import app.DAO.LoginDAO;
import app.InitFont.CustomFont;
import app.Object.Account;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BrewLogin extends JFrame {
    private CustomFont cf = new CustomFont();
    private final Font titleFont;
    private final Font labelFont;
    private final Font fieldFont;
    private JTextField nameField;
    private JPasswordField passwordField;
    private static BrewLogin instance;
    private JDialog loadingDialog;
    private BrewGUI page;
    private Account acc;

    public BrewLogin() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("☕ Đăng Nhập - Brew");
        ImageIcon icon = new ImageIcon("asset/icon.png");
        setIconImage(icon.getImage());
        setSize(950, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        titleFont = cf.getRobotoFonts().get(0).deriveFont(Font.BOLD, 30);
        labelFont = cf.getRobotoFonts().get(0).deriveFont(Font.BOLD, 16);
        fieldFont = cf.getRobotoFonts().get(0).deriveFont(Font.BOLD, 15);
        instance = this;

        // ==== Màu nền ====
        Color bgColor = new Color(242, 238, 230);
        Color panelColor = new Color(255, 255, 255, 230);
        Color buttonColor = new Color(164, 56, 32);
        Color buttonHover = new Color(180, 130, 90);
        Color textColor = new Color(60, 40, 30);

        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0,
                        new Color(250, 245, 230),
                        0, getHeight(),
                        new Color(240, 230, 200)));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 50, 30)); // thêm khoảng cách dưới

        JLabel lblTitle = new JLabel("WELCOME TO BREW", SwingConstants.CENTER);
        lblTitle.setFont(titleFont);
        lblTitle.setForeground(textColor);
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // ==== Bên trái: hình ảnh ====
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(new Color(0, 0, 0, 0));
        leftPanel.setPreferredSize(new Dimension(450, 450));

        ImageIcon coffeeIcon;
        try {
            coffeeIcon = new ImageIcon(new ImageIcon("asset/layout/BackGround_DangNhap.jpg")
                    .getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            coffeeIcon = new ImageIcon(new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB));
        }

        JLabel lblImage = new JLabel(coffeeIcon);
        leftPanel.add(lblImage);
        mainPanel.add(leftPanel, BorderLayout.WEST);

        // ==== Bên phải: form ====
        JPanel rightContainer = new JPanel(new GridBagLayout());
        rightContainer.setOpaque(false);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(panelColor);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(40, 50, 40, 50)
        ));

        JLabel lblFormTitle = new JLabel("Đăng nhập hệ thống", SwingConstants.CENTER);
        lblFormTitle.setFont(cf.getRobotoFonts().get(0).deriveFont(Font.BOLD, 22));
        lblFormTitle.setForeground(textColor);
        lblFormTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(panelColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 15, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Tên tài khoản
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblUser = new JLabel("Tên tài khoản:");
        lblUser.setFont(fieldFont);
        lblUser.setForeground(textColor);
        formPanel.add(lblUser, gbc);

        gbc.gridy++;
        nameField = new JTextField();
        nameField.setFont(fieldFont);
        nameField.setPreferredSize(new Dimension(0, 45));
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        formPanel.add(nameField, gbc);

        // Mật khẩu
        gbc.gridy++;
        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setFont(fieldFont);
        lblPass.setForeground(textColor);
        formPanel.add(lblPass, gbc);

        gbc.gridy++;
        passwordField = new JPasswordField();
        passwordField.setFont(fieldFont);
        passwordField.setPreferredSize(new Dimension(0, 45));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        formPanel.add(passwordField, gbc);

        // Nút đăng nhập
        gbc.gridy++;
        JButton btnLogin = new JButton("Đăng nhập");
        btnLogin.setFont(labelFont);
        btnLogin.setBackground(buttonColor);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        btnLogin.addActionListener(e -> {
            String username = nameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                return;
            }

            acc = LoginDAO.loginAccount(username, password);
            // System.out.println(LoginDAO.loginAccount(username, password).toString());

            if (acc == null) {
                JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!", "Đăng nhập thất bại", JOptionPane.ERROR_MESSAGE);
            }

            if (acc.getRole() != null) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công với vai trò: " + acc.getRole(), "Thành công", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();

                showLoadingDialog();
                initBrewGUI();
            } else {
                JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!", "Đăng nhập thất bại", JOptionPane.ERROR_MESSAGE);
            }
        });

        formPanel.add(btnLogin, gbc);

        // Quên mật khẩu / Tạo tài khoản
        gbc.gridy++;
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(panelColor);

        JLabel lblForgot = new JLabel("Quên mật khẩu?");
        lblForgot.setForeground(new Color(0, 102, 204));
        lblForgot.setFont(cf.getRobotoFonts().get(0).deriveFont(Font.BOLD, 12));
        lblForgot.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblCreate = new JLabel("Tạo tài khoản mới");
        lblCreate.setForeground(new Color(0, 102, 204));
        lblCreate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblCreate.setFont(cf.getRobotoFonts().get(0).deriveFont(Font.BOLD, 12));

        bottomPanel.add(lblForgot);
        bottomPanel.add(lblCreate);
        gbc.weighty = 1.0; // đẩy phần này xuống cuối form
        gbc.anchor = GridBagConstraints.SOUTH;
        formPanel.add(bottomPanel, gbc);

        rightPanel.add(lblFormTitle);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(formPanel);

        GridBagConstraints rGbc = new GridBagConstraints();
        rGbc.gridx = 0;
        rGbc.gridy = 0;
        rightContainer.add(rightPanel, rGbc);

        mainPanel.add(rightContainer, BorderLayout.CENTER);
        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BrewLogin().setVisible(true));
    }

    public void showLoadingDialog() {
        loadingDialog = new JDialog(this, "Đang khởi tạo...", false); // false = non-modal
        loadingDialog.setUndecorated(true);
        loadingDialog.setSize(200, 200);
        loadingDialog.setLocationRelativeTo(this);
        loadingDialog.setAlwaysOnTop(true);
        loadingDialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

//        JLabel label = new JLabel("Đang khởi tạo hệ thống...", SwingConstants.CENTER);
//        label.setAlignmentX(Component.CENTER_ALIGNMENT);
//        label.setFont(cf.getRobotoFonts().get(0).deriveFont(Font.BOLD, 10));
//        label.setPreferredSize(new Dimension(200, 25));

        JLabel gifLabel = new JLabel(new ImageIcon("asset/loading.gif"));
        gifLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(gifLabel);
//        panel.add(label);

        loadingDialog.add(panel);
        loadingDialog.setVisible(true);
    }

    public void initBrewGUI() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                page = new BrewGUI(acc);
                page.setVisible(true);

                return null;
            }

            @Override
            protected void done() {
                page.setLocationRelativeTo(instance);
                page.setExtendedState(JFrame.MAXIMIZED_BOTH);
                loadingDialog.dispose();
            }
        };
        worker.execute();
    }
}