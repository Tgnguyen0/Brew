package app.GUI;

import app.Collections.Collection_Table;
import app.Components.TableButton;
import app.DAO.DAO_Table;
import app.InitFont.CustomFont;
import app.Listener.ActionListener_CafeLayoutPage;
import app.Object.Table;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CafeLayoutPage extends JFrame {
    private CustomFont customFont = new CustomFont();
    public ActionListener_CafeLayoutPage action;
    public Collection_Table collectionTable;
    public TableButton table1;
    public TableButton table2;
    public TableButton table3;
    public TableButton table4;
    public TableButton table5;
    public JButton confirmedButton;
    public String labelTableId = "";
    private Map<String, Integer> tableMap;

    public CafeLayoutPage(List<Table> choosenTableList) {
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
        collectionTable = new Collection_Table();
        collectionTable.addAll(choosenTableList);

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

        JPanel editTablePanel = new JPanel();
        editTablePanel.setLayout(new BoxLayout(editTablePanel, BoxLayout.X_AXIS));
        editTablePanel.setBackground(Color.WHITE);

        JLabel tableLabel = new JLabel("Table " + labelTableId + " : ");
        tableLabel.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 14));
        tableLabel.setForeground(Color.BLACK);
        editTablePanel.add(tableLabel);
        editTablePanel.add(Box.createHorizontalStrut(10));

        JButton increaseButton = new JButton("+");
        increaseButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 14));
        increaseButton.setBackground(Color.white);
        increaseButton.setForeground(Color.black);
        editTablePanel.add(increaseButton);
        editTablePanel.add(Box.createHorizontalStrut(10));

        JTextField sizeField = new JTextField();
        sizeField.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 14));
        sizeField.setPreferredSize(new Dimension(50, 25));
        sizeField.setMaximumSize(new Dimension(50, 25));
        sizeField.setMinimumSize(new Dimension(50, 25));
        sizeField.setBackground(Color.white);
        sizeField.setForeground(Color.black);
        editTablePanel.add(sizeField);
        editTablePanel.add(Box.createHorizontalStrut(10));

        JButton decreaseButton = new JButton("-");
        decreaseButton.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 14));
        decreaseButton.setBackground(Color.white);
        decreaseButton.setForeground(Color.black);
        editTablePanel.add(decreaseButton);

        inforPanel.add(editTablePanel);
        inforPanel.add(Box.createVerticalStrut(20));

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

        List<Table> tables = DAO_Table.getAllTable();

        table1 = new TableButton(
                tables.get(0).getTableId(),
                32, 396, 118, 118,
                customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 15),
                Color.white,
                Color.BLACK
        );
        table1.addActionListener(action);
        groundFloor.add(table1);

        table2 = new TableButton(
                tables.get(1).getTableId(),
                811, 314, 118, 118,
                customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 15),
                Color.white,
                Color.BLACK
        );
        table2.addActionListener(action);
        groundFloor.add(table2);

        table3 = new TableButton(
                tables.get(2).getTableId(),
                811, 588, 118, 118,
                customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 15),
                Color.white,
                Color.BLACK
        );
        table3.addActionListener(action);
        groundFloor.add(table3);

        table4 = new TableButton(
                tables.get(3).getTableId(),
                564, 586, 118, 118,
                customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 15),
                Color.white,
                Color.BLACK
        );
        table4.addActionListener(action);
        groundFloor.add(table4);

        table5 = new TableButton(
                tables.get(4).getTableId(),
                316, 586, 118, 118,
                customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 15),
                Color.white,
                Color.BLACK
        );
        table5.addActionListener(action);
        groundFloor.add(table5);

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

    public void doDispose() {
        System.out.println("OK");

        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Mongo.getConnection();
            CafeLayoutPage layout = new CafeLayoutPage(new ArrayList<Table>());
            layout.setVisible(true);
        });
    }
}
