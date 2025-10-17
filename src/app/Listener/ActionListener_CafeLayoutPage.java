package app.Listener;

import app.GUI.CafeLayoutPage;
import app.GUI.DevCafeGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListener_CafeLayoutPage implements ActionListener {
    private CafeLayoutPage layoutPage;

    public ActionListener_CafeLayoutPage(CafeLayoutPage layoutPage) {
        this.layoutPage = layoutPage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cardLayout = (CardLayout) layoutPage.buildingPanel.getLayout();
        String command = e.getActionCommand();
        Object o = e.getSource();

        switch (command) {
            case "Tầng trệt":
                cardLayout.show(layoutPage.buildingPanel, "Ground Floor");
                break;
            case "Tầng một":
                cardLayout.show(layoutPage.buildingPanel, "First Floor");
                break;
            case "Tầng hai":
                cardLayout.show(layoutPage.buildingPanel, "Second Floor");
                break;
        }
    }
}
