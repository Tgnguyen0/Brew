package app.Listener;

import app.GUI.CafeLayoutPage;
import app.GUI.DevCafeGUI;
import app.GUI.SellPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListener_SellPage implements ActionListener {
    private SellPage sellPage;

    public ActionListener_SellPage(SellPage sellPage) {
        this.sellPage = sellPage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o == sellPage.takeAwayRadioButton) {
            sellPage.seatingButton.setEnabled(!sellPage.takeAwayRadioButton.isSelected());
        }

        if (o == sellPage.seatingButton) {
            System.out.print("Oke");
            SwingUtilities.invokeLater(() -> {
                CafeLayoutPage layoutPage = new CafeLayoutPage();
                layoutPage.setVisible(true);
            });
        }
    }
}
