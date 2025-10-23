package app.GUI;

import app.InitFont.CustomFont;
import app.Listener.ActionListener_CafeLayoutPage;
import com.formdev.flatlaf.FlatDarkLaf;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.swing.FontIcon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;

public class CafeLayoutPage extends JFrame {
    private CustomFont customFont = new CustomFont();
    public ActionListener_CafeLayoutPage action;
    public JPanel buildingPanel;
    public JLabel logoNameLabel;
    public JButton groundFloorButton;
    public JButton firstFloorButton;
    public JButton secondFloorButton;

    public CafeLayoutPage() {
        //setSize(new Dimension(800, 600));
        setTitle("Cafe layout");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(Color.white);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        action = new ActionListener_CafeLayoutPage(this);

        JPanel emptyNorth = new JPanel();
        emptyNorth.setPreferredSize(new Dimension(100, 100));
        emptyNorth.setOpaque(false);
        add(emptyNorth, BorderLayout.NORTH);

        add(initGroundFloor(), BorderLayout.WEST);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
//        panel.add(initFloorOption());
        panel.add(initStatusPanel());
        panel.add(initConfirmedPanel());
        add(panel, BorderLayout.CENTER);

        JPanel emptySouth = new JPanel();
        emptySouth.setPreferredSize(new Dimension(100, 100));
        emptySouth.setOpaque(false);
        add(emptySouth, BorderLayout.SOUTH);
    }

    private JPanel initStatusPanel() {
        JPanel inforPanel = new JPanel();
        inforPanel.setLayout(new BoxLayout(inforPanel, BoxLayout.Y_AXIS));
        inforPanel.setBackground(Color.WHITE);
        inforPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inforPanel.add(createStatusRow(Color.RED, "Table is not available"));
        inforPanel.add(Box.createVerticalStrut(10));
        inforPanel.add(createStatusRow(Color.WHITE, "Table is available"));

        return inforPanel;
    }

    private JPanel createStatusRow(Color color, String text) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        rowPanel.setMaximumSize(new Dimension(500, 70));
        rowPanel.setBackground(Color.white);

        JPanel colorBox = new JPanel();
        colorBox.setBackground(color);
        colorBox.setPreferredSize(new Dimension(60, 60));
        colorBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel label = new JLabel(text);
        label.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 14));
        label.setForeground(Color.BLACK);

        rowPanel.add(colorBox);
        rowPanel.add(label);
        return rowPanel;
    }

    private JPanel initGroundFloor() {
        JPanel groundFloor = new JPanel() {
            private Image backgroundImage;

            {
                try {
                    BufferedImage icon = ImageIO.read(new File("asset/layout/ground-floor.png"));
                    int iconWitdh = (int) (736 * 1.35);
                    int iconHeight = (int) (528 * 1.35);
                    backgroundImage = icon.getScaledInstance(iconWitdh, iconHeight, Image.SCALE_SMOOTH);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    int x = (getWidth() - backgroundImage.getWidth(null)) / 2;
                    int y = (getHeight() - backgroundImage.getHeight(null)) / 2;
                    g.drawImage(backgroundImage, x, y, this);
                }
            }
        };
        groundFloor.setOpaque(false);

        groundFloor.setLayout(null); // Cho phép bạn đặt ghế, bàn tự do theo toạ độ
        groundFloor.setPreferredSize(new Dimension((int) (736 * 1.25), (int) (528 * 1.25)));
        groundFloor.setOpaque(false);

        JButton ban1 = new JButton("1");
        ban1.setBounds(328, 390, 105, 105);
        groundFloor.add(ban1);

        JButton ban2 = new JButton("2");
        ban2.setBounds(1040, 318, 105, 105);
        groundFloor.add(ban2);

        JButton ban3 = new JButton("3");
        ban3.setBounds(1040, 318, 105, 105);
        groundFloor.add(ban3);

        JButton ban4 = new JButton("4");
        ban4.setBounds(1040, 318, 105, 105);
        groundFloor.add(ban4);

        JButton ban5 = new JButton("5");
        ban5.setBounds(1040, 318, 105, 105);
        groundFloor.add(ban5);

        return groundFloor;
    }

    private JPanel initConfirmedPanel() {
        JPanel confirmedPanel = new JPanel();
        confirmedPanel.setOpaque(false);

        JButton confirmedButton = new JButton("Confirmed Chosen Table");
        confirmedButton.setBackground(Color.white);
        confirmedButton.setForeground(Color.black);
        confirmedButton.setPreferredSize(new Dimension(240, 60));
        confirmedButton.setMaximumSize(new Dimension(240, 60));
        confirmedButton.setBorder(BorderFactory.createLineBorder(Color.black));
        confirmedButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 14));
        confirmedPanel.add(confirmedButton);

        return confirmedPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Mongo.getConnection();
            CafeLayoutPage layout = new CafeLayoutPage();
            layout.setVisible(true);
        });
    }
}
