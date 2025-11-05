package app.Listener;

import app.Components.ImagePanelButton;
import app.DAO.DAO_Bill;
import app.GUI.SellPage;
import app.Object.Bill;
import app.Object.BillDetail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class ActionListener_ImagePanelButton implements ActionListener {
    private ImagePanelButton imagePanelButton;

    public ActionListener_ImagePanelButton(ImagePanelButton imagePanelButton) {
        this.imagePanelButton = imagePanelButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        boolean isAdded = imagePanelButton.collectionBillDetails.addBillDetail(new BillDetail());

        if (isAdded) {
            Vector<String> row = new Vector<String>();
            row.add(imagePanelButton.mi.getName());
            row.add(String.valueOf(1));
            row.add(String.valueOf(imagePanelButton.mi.getPrice()));
            SellPage.productTableModel.addRow(row);
        } else {
            for (int i = 0 ; i < SellPage.productTableModel.getRowCount(); i++) {
                if (SellPage.productTableModel.getValueAt(i, 0).equals(imagePanelButton.mi.getName())) {
                    int ammount = Integer.parseInt(SellPage.productTableModel.getValueAt(i, 1).toString());
                    ammount++;

                    SellPage.productTableModel.setValueAt(ammount, i, 1);
                    SellPage.productTableModel.setValueAt(ammount * imagePanelButton.mi.getPrice(), i, 2);
                    imagePanelButton.collectionBillDetails.updateBillDetail(new BillDetail());
                    break;
                }
            }
        }
    }
}
