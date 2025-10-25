package app.GUI;

import app.Components.TableButton;
import app.InitFont.CustomFont;
import app.Listener.ActionListener_CafeLayoutPage;
import com.formdev.flatlaf.FlatDarkLaf;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.swing.FontIcon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;

public class CafeLayoutPage extends JFrame {
    private CustomFont customFont = new CustomFont();
    public ActionListener_CafeLayoutPage action;
    public JPanel buildingPanel;
    public JButton confirmedButton;

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

        JPanel labelPanel = new JPanel();
        labelPanel.setPreferredSize(new Dimension(400, 50));
        labelPanel.setBackground(Color.white);
        add(labelPanel, BorderLayout.NORTH);

        JLabel introLabel = new JLabel("Cafe Layout");
        introLabel.setForeground(Color.black);
        introLabel.setPreferredSize(new Dimension(1445, 40));
        introLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 30));
        introLabel.setVerticalAlignment(SwingConstants.CENTER);
        introLabel.setHorizontalAlignment(SwingConstants.LEFT);
        introLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        introLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelPanel.add(introLabel);

        add(initGroundFloor(), BorderLayout.WEST);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.white);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 30, 110);

        panel.add(initStatusPanel(), gbc);

        gbc.gridy++;
        panel.add(initConfirmedPanel(), gbc);

        add(panel, BorderLayout.CENTER);

        JPanel emptySouth = new JPanel();
        emptySouth.setPreferredSize(new Dimension(100, 40));
        emptySouth.setBackground(Color.white);
        add(emptySouth, BorderLayout.SOUTH);
    }

    private JPanel initStatusPanel() {
        JPanel inforPanel = new JPanel();
        inforPanel.setLayout(new BoxLayout(inforPanel, BoxLayout.Y_AXIS));
        inforPanel.setBackground(Color.WHITE);

        inforPanel.add(createStatusRow(Color.RED, "Table is not available"));
        inforPanel.add(Box.createVerticalStrut(20));
        inforPanel.add(createStatusRow(Color.WHITE, "Table is available"));
        inforPanel.add(Box.createVerticalStrut(20));
        inforPanel.add(createStatusRow(Color.ORANGE, "Your choosen table"));

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
        JPanel holdPanel = new JPanel();
        holdPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        holdPanel.setPreferredSize(new Dimension(1125, (int) (528 * 1.3)));
        holdPanel.setBackground(Color.white);

        JPanel groundFloor = new JPanel() {
            private Image backgroundImage;

            {
                try {
                    BufferedImage icon = ImageIO.read(new File("asset/layout/ground-floor.png"));
                    int iconWitdh = (int) (736 * 1.4);
                    int iconHeight = (int) (528 * 1.4);
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
        groundFloor.setLayout(null);
        groundFloor.setPreferredSize(new Dimension((int) (736 * 1.4), (int) (528 * 1.4)));
        groundFloor.setOpaque(false);
        holdPanel.add(groundFloor);

        TableButton ban1 = new TableButton(
                "1",
                32, 396, 118, 118,
                customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 15),
                Color.white,
                Color.BLACK
        );
        groundFloor.add(ban1);

        TableButton ban2 = new TableButton(
                "2",
                811, 314, 118, 118,
                customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 15),
                Color.white,
                Color.BLACK
        );
        groundFloor.add(ban2);

        TableButton ban3 = new TableButton(
                "3",
                811, 588, 118, 118,
                customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 15),
                Color.white,
                Color.BLACK
        );
        groundFloor.add(ban3);

        TableButton ban4 = new TableButton(
                "4",
                564, 586, 118, 118,
                customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 15),
                Color.white,
                Color.BLACK
        );
        groundFloor.add(ban4);

        TableButton ban5 = new TableButton(
                "5",
                316, 586, 118, 118,
                customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 15),
                Color.white,
                Color.BLACK
        );
        groundFloor.add(ban5);

        return holdPanel;
    }

    private JPanel initConfirmedPanel() {
        JPanel confirmedPanel = new JPanel();
        confirmedPanel.setOpaque(false);

        confirmedButton = new JButton("Confirmed Chosen Table");
        confirmedButton.setBackground(Color.white);
        confirmedButton.setForeground(Color.black);
        confirmedButton.setPreferredSize(new Dimension(240, 60));
        confirmedButton.setMaximumSize(new Dimension(240, 60));
        confirmedButton.setBorder(BorderFactory.createLineBorder(Color.black));
        confirmedButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 14));
        confirmedButton.addActionListener(action);
        confirmedPanel.add(confirmedButton);

        return confirmedPanel;
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            // Mongo.getConnection();
//            CafeLayoutPage layout = new CafeLayoutPage();
//            layout.setVisible(true);
//        });
//    }
}
