package app.Listener;

import app.Components.ImagePanelButton;
import app.GUI.SellPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListener_ImagePanelButton implements ActionListener {
    private ImagePanelButton imagePanelButton;

    public ActionListener_ImagePanelButton(ImagePanelButton imagePanelButton) {
        this.imagePanelButton = imagePanelButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        imagePanelButton.collectionMenuItem.addItem(imagePanelButton.mi);
    }
}
