package app.Listener;

import app.GUI.BrewGUI;
import app.GUI.BrewLogin;
import app.GUI.CafeLayoutPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListener_BrewGUI implements ActionListener {
    private BrewGUI brewGUI;

    public ActionListener_BrewGUI(BrewGUI brewGUI) {
        this.brewGUI = brewGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o == brewGUI.btnLogout) {
            System.out.println("work!!");

            brewGUI.dispose();
            SwingUtilities.invokeLater(() -> {
                BrewLogin brewLogin = new BrewLogin();
                brewLogin.setVisible(true);
            });
        }
    }
}
