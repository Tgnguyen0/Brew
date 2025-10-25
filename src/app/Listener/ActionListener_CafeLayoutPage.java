package app.Listener;

import app.GUI.CafeLayoutPage;

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
        Object o = e.getSource();

        if (o.equals(layoutPage.confirmedButton)) {
            layoutPage.dispose();
        }
    }
}
