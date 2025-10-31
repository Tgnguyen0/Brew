package app.SwingWorker;

import app.Components.ImagePanelButton;
import app.DAO.DAO_MenuItem;
import app.GUI.SellPage;
import app.Object.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class MenuItemLoaderWorker extends SwingWorker<List<MenuItem>, ImagePanelButton> {
    private final SellPage sellPage;
    private final GridBagConstraints gbc;
    private final String name;
    private final MODE mode;
    private final Consumer<List<MenuItem>> onDone;

    public MenuItemLoaderWorker(SellPage sellPage, GridBagConstraints gbc, String name, MODE mode, Consumer<List<MenuItem>> onDone) {
        this.sellPage = sellPage;
        this.gbc = gbc;
        this.name = name;
        this.mode = mode;
        this.onDone = onDone;
    }

    @Override
    protected List<MenuItem> doInBackground() {
        if (mode.equals(MODE.SEARCH)) {
            // Tìm kiếm theo tên
            return DAO_MenuItem.findMultipleMenuItem(name);
        }

        if (mode.equals(MODE.CATEGORY)) {
            // Tìm kiếm theo loại
            return DAO_MenuItem.listOfMenuItemByCategory(name);
        }

        // Lấy theo offset
        return DAO_MenuItem.get18MenuItems(sellPage.currentOffset, 18);
    }

    @Override
    protected void done() {
        try {
            List<MenuItem> menuBatch = get();
            onDone.accept(menuBatch); // callback xử lý kết quả
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sellPage.loadingDialog.dispose();
            sellPage.showLoadingSuccessfullyOptionPane();
            sellPage.isLoading = false;
        }
    }
}
