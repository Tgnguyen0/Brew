package app.Listener;

import app.Components.ImagePanelButton;
import app.DAO.DAO_Bill;
import app.DAO.DAO_BillDetail;
import app.DAO.DAO_MenuItem;
import app.GUI.BrewGUI;
import app.GUI.CafeLayoutPage;
import app.GUI.SellPage;
import app.Object.Bill;
import app.Object.MenuItem;
import app.SwingWorker.MODE;
import app.SwingWorker.MenuItemLoaderWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ActionListener_SellPage implements ActionListener {
    private SellPage sellPage;

    public ActionListener_SellPage(SellPage sellPage) {
        this.sellPage = sellPage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o == sellPage.findProduct) {
            loadMBaseOnMode(sellPage.gbc, sellPage.searchBar.getText(), MODE.SEARCH);
        }

        if (o == sellPage.takeAwayRadioButton) {
            sellPage.seatingButton.setEnabled(!sellPage.takeAwayRadioButton.isSelected());
        }

        if (o == sellPage.seatingButton) {
            SwingUtilities.invokeLater(() -> {
                CafeLayoutPage layoutPage = new CafeLayoutPage(sellPage.choosenTableList);
                layoutPage.setVisible(true);
            });
        }

        if (o == sellPage.loadProductButton) {
            loadMBaseOnMode(sellPage.gbc, null, MODE.LOAD);
        }

        if (o == sellPage.clearSearchButton) {
            reloadAllProducts();
        }

        if (o == sellPage.productCategory) {
            String selectedItem = (String) sellPage.productCategory.getSelectedItem();

            if (selectedItem.equals("Tất cả")) {
                reloadAllProducts();
            } else {
                loadMBaseOnMode(sellPage.gbc, (String) sellPage.productCategory.getSelectedItem(), MODE.CATEGORY);
            }
        }

        if (o == sellPage.updateButton) {
            if (SellPage.productTable.isEditing()) {
                SellPage.productTable.getCellEditor().stopCellEditing();
            }

            for (int row = 0; row < SellPage.productTable.getRowCount(); row++) {
                Object quantityObj = SellPage.productTable.getValueAt(row, 1);
                Object priceObj = SellPage.productTable.getValueAt(row, 2);

                int amount;
                float price;

                // Ép kiểu an toàn
                if (quantityObj instanceof String)
                    amount = Integer.parseInt((String) quantityObj);
                else if (quantityObj instanceof Number)
                    amount = ((Number) quantityObj).intValue();
                else
                    amount = 0;

                if (priceObj instanceof String)
                    price = Float.parseFloat((String) priceObj);
                else if (priceObj instanceof Number)
                    price = ((Number) priceObj).floatValue();
                else
                    price = 0f;

                sellPage.collectionBillDetails.updateBDOnOrder(row, amount, price);
            }
        }

        if (o == sellPage.deleteButton) {
            for (int i = SellPage.productTableModel.getRowCount() - 1; i >= 0; i--) {
                SellPage.productTableModel.removeRow(i);
            }
            sellPage.collectionBillDetails.removeAll();
        }

        if (o == sellPage.toInvoiceButton) {
            DAO_Bill.createBill();
            Bill bill = DAO_Bill.getLatestBill();
            System.out.println(bill.toString());

            sellPage.collectionBillDetails.updateAllBillDetail(bill.getBillId());
            DAO_BillDetail.saveAllBD(sellPage.collectionBillDetails.getList());

            CardLayout cardLayout = (CardLayout) BrewGUI.pageContainer.getLayout();
            cardLayout.show(BrewGUI.pageContainer, "Receipt Page");

            DAO_Bill.
        }
    }

    public void reloadAllProducts() {
        sellPage.productPanel.removeAll();
        sellPage.currentOffset = sellPage.previousOffset;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        int columns = 3;

        for (int i = 0; i < sellPage.allProductButtons.size(); i++) {
            gbc.gridx = i % columns;
            gbc.gridy = i / columns;
            sellPage.productPanel.add(sellPage.allProductButtons.get(i), gbc);
        }

        sellPage.productPanel.revalidate();
        sellPage.productPanel.repaint();
    }

    private void loadMBaseOnMode(GridBagConstraints gbc, String keyword, MODE mode) {
        if (sellPage.isLoading) return;
        sellPage.isLoading = true;

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(sellPage);
        sellPage.showLoadingDialog(frame);

        if (mode.equals(MODE.CATEGORY) || mode.equals(MODE.SEARCH)) {
            sellPage.productPanel.removeAll();
        }

        var worker = new MenuItemLoaderWorker(sellPage, gbc, keyword, mode);
        worker.execute();
    }
}
