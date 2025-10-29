package app.Listener;

import app.Components.ImagePanelButton;
import app.DAO.DAO_MenuItem;
import app.GUI.CafeLayoutPage;
import app.GUI.SellPage;
import app.Object.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
            SwingUtilities.invokeLater(() -> {
                CafeLayoutPage layoutPage = new CafeLayoutPage(sellPage.choosenTableList);
                layoutPage.setVisible(true);
            });
        }

        if (o == sellPage.loadProductButton) {
            loadMoreMenuItems(sellPage.gbc);
        }
    }

    private void loadMoreMenuItems(GridBagConstraints gbc) {
        if (sellPage.isLoading) return;
        sellPage.isLoading = true;

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(sellPage);
        sellPage.showLoadingDialog(frame);

        SwingWorker<Void, ImagePanelButton> worker = new SwingWorker<>() {
            List<MenuItem> menuBatch;

            @Override
            protected Void doInBackground() {
                menuBatch = DAO_MenuItem.get18MenuItems(sellPage.currentOffset, 18);
                for (MenuItem item : menuBatch) {
                    ImagePanelButton button = new ImagePanelButton(
                            item.getName(), "", item.getPrice(),
                            "asset/placeholder.png", 200, 200, 0.8
                    );
                    button.setPreferredSize(new Dimension(250, 250));
                    publish(button); // gửi từng cái để cập nhật dần
                    try { Thread.sleep(50); } catch (InterruptedException ignored) {}
                }
                return null;
            }

            @Override
            protected void process(List<ImagePanelButton> chunks) {
                int columns = 3;
                for (ImagePanelButton productButton : chunks) {
                    int index = sellPage.productPanel.getComponentCount();
                    gbc.gridx = index % columns;
                    gbc.gridy = index / columns;
                    sellPage.productPanel.add(productButton, gbc);
                }
                sellPage.productPanel.revalidate();
                sellPage.productPanel.repaint();
            }

            @Override
            protected void done() {
                sellPage.loadingDialog.dispose();
                sellPage.isLoading = false;
            }
        };
        worker.execute();

        worker.execute();
        SwingUtilities.invokeLater(() -> sellPage.loadingDialog.setVisible(true));
    }
}
