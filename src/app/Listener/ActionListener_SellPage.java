package app.Listener;

import app.Components.ImagePanelButton;
import app.DAO.DAO_Bill;
import app.DAO.DAO_MenuItem;
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
            loadBasedOnName(sellPage.gbc, sellPage.searchBar.getText());
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
            loadMoreMenuItems(sellPage.gbc);
        }

        if (o == sellPage.clearSearchButton) {
            reloadAllProducts();
        }

        if (o == sellPage.productCategory) {
            String selectedItem = (String) sellPage.productCategory.getSelectedItem();

            if (selectedItem.equals("Tất cả")) {
                reloadAllProducts();
            } else {
                loadBaseOnCategory(sellPage.gbc, (String) sellPage.productCategory.getSelectedItem());
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
//            DAO_Bill.createBill();
//            Bill bill = DAO_Bill.getLatestBill();
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

    private void loadMoreMenuItems(GridBagConstraints gbc) {
        if (sellPage.isLoading) return;
        sellPage.isLoading = true;

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(sellPage);
        sellPage.showLoadingDialog(frame);

        var worker = new MenuItemLoaderWorker(
                sellPage, gbc, null, MODE.LOAD, // Chế độ load thêm
                (menuBatch) -> {
                    int columns = 3;
                    for (MenuItem item : menuBatch) {
                        ImagePanelButton button = new ImagePanelButton(
                                item, sellPage.collectionBillDetails, "asset/placeholder.png", 200, 200, 0.8
                        );
                        button.setPreferredSize(new Dimension(250, 250));
                        int index = sellPage.productPanel.getComponentCount();
                        gbc.gridx = index % columns;
                        gbc.gridy = index / columns;
                        sellPage.productPanel.add(button, gbc);
                        sellPage.allProductButtons.add(button);
                        sellPage.currentOffset++;
                    }
                    sellPage.productPanel.revalidate();
                    sellPage.productPanel.repaint();
                    sellPage.previousOffset = sellPage.currentOffset;
                }
        );

        worker.execute();
        SwingUtilities.invokeLater(() -> sellPage.loadingDialog.setVisible(true));
    }

    private void loadBasedOnName(GridBagConstraints gbc, String name) {
        if (sellPage.isLoading) return;
        sellPage.isLoading = true;

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(sellPage);
        sellPage.showLoadingDialog(frame);

        var worker = new MenuItemLoaderWorker(
                sellPage, gbc, name, MODE.SEARCH, // Chế độ search
                (menuBatch) -> {
                    sellPage.productPanel.removeAll();
                    int columns = 3;
                    int index = 0;
                    for (MenuItem item : menuBatch) {
                        ImagePanelButton button = new ImagePanelButton(
                                item, sellPage.collectionBillDetails, "asset/placeholder.png", 200, 200, 0.8
                        );
                        button.setPreferredSize(new Dimension(250, 250));

                        gbc.gridx = index % columns;
                        gbc.gridy = index / columns;
                        sellPage.productPanel.add(button, gbc);
                        index++;
                    }
                    sellPage.productPanel.revalidate();
                    sellPage.productPanel.repaint();
                }
        );

        worker.execute();
        SwingUtilities.invokeLater(() -> sellPage.loadingDialog.setVisible(true));
    }

    private void loadBaseOnCategory(GridBagConstraints gbc, String category) {
        if (sellPage.isLoading) return;
        sellPage.isLoading = true;

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(sellPage);
        sellPage.showLoadingDialog(frame);

        var worker = new MenuItemLoaderWorker(
                sellPage, gbc, category, MODE.CATEGORY, // Chế độ category
                (menuBatch) -> {
                    sellPage.productPanel.removeAll();
                    int columns = 3;
                    int index = 0;
                    for (MenuItem item : menuBatch) {
                        ImagePanelButton button = new ImagePanelButton(
                                item, sellPage.collectionBillDetails,"asset/placeholder.png", 200, 200, 0.8
                        );
                        button.setPreferredSize(new Dimension(250, 250));

                        gbc.gridx = index % columns;
                        gbc.gridy = index / columns;
                        sellPage.productPanel.add(button, gbc);
                        index++;
                    }
                    sellPage.productPanel.revalidate();
                    sellPage.productPanel.repaint();
                }
        );

        worker.execute();
        SwingUtilities.invokeLater(() -> sellPage.loadingDialog.setVisible(true));
    }
}
