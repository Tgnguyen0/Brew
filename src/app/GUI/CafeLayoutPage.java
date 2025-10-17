package app.GUI;

import app.InitFont.CustomFont;
import app.Listener.ActionListener_CafeLayoutPage;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;

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
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(Color.white);
        setLocationRelativeTo(null);
        setResizable(false);

        action = new ActionListener_CafeLayoutPage(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(initFloorOption());
        panel.add(initBuildingPanel());

        add(panel);
    }

    private JPanel initFloorOption() {
        JPanel optionBar = new JPanel();
        optionBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        optionBar.setLayout(new BoxLayout(optionBar, BoxLayout.X_AXIS));
        optionBar.setBackground(new Color(164, 56, 32));

        optionBar.add(Box.createHorizontalGlue());
        groundFloorButton = initFoorButton("Tầng trệt");
        groundFloorButton.addActionListener(action);
        optionBar.add(groundFloorButton);
        optionBar.add(Box.createHorizontalStrut(10));

        firstFloorButton = initFoorButton("Tầng một");
        firstFloorButton.addActionListener(action);
        optionBar.add(firstFloorButton);
        optionBar.add(Box.createHorizontalStrut(10));

        secondFloorButton = initFoorButton("Tầng hai");
        secondFloorButton.addActionListener(action);
        optionBar.add(secondFloorButton);
        optionBar.add(Box.createHorizontalGlue());

        return optionBar;
    }

    private JButton initFoorButton(String text) {
        JButton button = new JButton(text);
        button.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        button.setPreferredSize(new Dimension(100, 40));
        button.setBackground(Color.white);
        button.setForeground(Color.black);

        return button;
    }

    private JPanel initBuildingPanel() {
        buildingPanel = new JPanel();
        buildingPanel.setBackground(Color.white);
        buildingPanel.setLayout(new CardLayout());

        buildingPanel.add(initGroundFloor(), "Ground Floor");
        buildingPanel.add(initFirstFloor(), "First Floor");
        buildingPanel.add(initSecondFloor(), "Second Floor");
        return buildingPanel;
    }

    private JPanel initGroundFloor() {
        JPanel groundFloor = new JPanel();
        groundFloor.setBackground(Color.white);

        JLabel label = new JLabel("Tầng trệt");
        groundFloor.add(label);

        return groundFloor;
    }

    private JPanel initFirstFloor() {
        JPanel firstFloor = new JPanel();
        firstFloor.setBackground(Color.white);

        JLabel label = new JLabel("Tầng một");
        firstFloor.add(label);

        return firstFloor;
    }

    private JPanel initSecondFloor() {
        JPanel secondFloor = new JPanel();
        secondFloor.setBackground(Color.white);

        JLabel label = new JLabel("Tầng hai");
        secondFloor.add(label);

        return secondFloor;
    }
}
