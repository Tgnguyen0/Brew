package app.SwingWorker;

import app.Components.ImagePanelButton;
import app.DAO.DAO_MenuItem;
import app.GUI.SellPage;
import app.Object.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class MenuItemLoaderWorker extends SwingWorker<Void, ImagePanelButton> {
    private final SellPage sellPage;
    private final GridBagConstraints gbc;
    private final String name;
    private final MODE mode;

    public MenuItemLoaderWorker(SellPage sellPage, GridBagConstraints gbc, String name, MODE mode) {
        this.sellPage = sellPage;
        this.gbc = gbc;
        this.name = name;
        this.mode = mode;
    }

    @Override
    protected Void doInBackground() {
        List<MenuItem> menuBatch;

        switch (mode) {
            case SEARCH:
                menuBatch = DAO_MenuItem.findMultipleMenuItem(name);
                break;
            case CATEGORY:
                menuBatch = DAO_MenuItem.listOfMenuItemByCategory(name);
                break;
            default:
                menuBatch = DAO_MenuItem.get18MenuItems(sellPage.currentOffset, 18);
                break;
        }

        for (MenuItem item : menuBatch) {
            ImagePanelButton button = new ImagePanelButton(
                    item, sellPage.collectionBillDetails, "asset/placeholder.png", 200, 200, 0.8
            );
            button.setPreferredSize(new Dimension(250, 250));
            publish(button);

            sellPage.currentOffset++;
            try {
                Thread.sleep(30); // Giả lập tải mượt
            } catch (InterruptedException ignored) {}
        }

        return null;
    }

    @Override
    protected void process(List<ImagePanelButton> chunks) {
        int columns = 3;
        for (ImagePanelButton button : chunks) {
            int index = sellPage.productPanel.getComponentCount();
            gbc.gridx = index % columns;
            gbc.gridy = index / columns;
            sellPage.productPanel.add(button, gbc);
            sellPage.allProductButtons.add(button);
        }
        sellPage.productPanel.revalidate();
        sellPage.productPanel.repaint();
    }

    @Override
    protected void done() {
        sellPage.loadingDialog.dispose();

        switch (mode) {
            case SEARCH:
                sellPage.showSearchingSuccessfullyOptionPane();
                break;
            case CATEGORY:
                sellPage.showCategorizingSuccessfullyOptionPane();
                break;
            default:
                sellPage.showLoadingSuccessfullyOptionPane();
                break;
        }

        sellPage.isLoading = false;
        sellPage.previousOffset = sellPage.currentOffset;
    }
}

