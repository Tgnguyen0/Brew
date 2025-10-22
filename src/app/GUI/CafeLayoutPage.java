package app.GUI;

import app.InitFont.CustomFont;
import app.Listener.ActionListener_CafeLayoutPage;
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

        action = new ActionListener_CafeLayoutPage(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        panel.add(initFloorOption());
        panel.add(initStatusPanel());
        panel.add(initGroundFloor());
        panel.add(initConfirmedPanel());

        add(panel);
    }

    private JPanel initStatusPanel() {
        JPanel inforPanel = new JPanel();
        inforPanel.setLayout(new BoxLayout(inforPanel, BoxLayout.X_AXIS));

        JPanel notVacantPanel = new JPanel();
        notVacantPanel.setLayout(new BoxLayout(notVacantPanel, BoxLayout.X_AXIS));

        JButton redButton = new JButton("");
        redButton.setBackground(Color.red);
        redButton.setPreferredSize(new Dimension(50, 50));
        redButton.setBorder(BorderFactory.createLineBorder(Color.red));
        redButton.setFocusPainted(false);
        redButton.setEnabled(false);
        notVacantPanel.add(redButton);

        JLabel notVaccantLabel = new JLabel("Table is not availiable");
        notVaccantLabel.setForeground(Color.black);
        notVaccantLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        notVacantPanel.add(notVaccantLabel);

        JPanel vacantPanel = new JPanel();
        vacantPanel.setLayout(new BoxLayout(vacantPanel, BoxLayout.X_AXIS));

        JButton whiteButton = new JButton("");
        whiteButton.setBackground(Color.white);
        whiteButton.setPreferredSize(new Dimension(50, 50));
        whiteButton.setBorder(BorderFactory.createLineBorder(Color.black));
        whiteButton.setFocusPainted(false);
        whiteButton.setEnabled(false);
        vacantPanel.add(whiteButton);

        JLabel vaccantLabel = new JLabel("Table is availiable");
        vaccantLabel.setForeground(Color.black);
        vaccantLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        vacantPanel.add(vaccantLabel);

        inforPanel.add(notVacantPanel);
        inforPanel.add(Box.createHorizontalStrut(20));
        inforPanel.add(vacantPanel);
        return inforPanel;
    }

    private JPanel initGroundFloor() {
        JPanel groundFloor = new JPanel();

        try {
            BufferedImage icon = ImageIO.read(new File("asset/layout/ground-floor.png"));

            int iconWitdh = (int) (736 * 1.35);
            int iconHeight = (int) (528 * 1.35);

            Image scaled = icon.getScaledInstance(iconWitdh, iconHeight, Image.SCALE_SMOOTH);

            JLabel imageHolder = new JLabel(new ImageIcon(scaled));
            groundFloor.add(imageHolder);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return groundFloor;
    }

    private JPanel initConfirmedPanel() {
        JPanel confirmedPanel = new JPanel();

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
