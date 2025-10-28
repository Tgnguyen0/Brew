package app.Listener;

import app.Collections.Collection_Table;
import app.Components.TableButton;
import app.DAO.DAO_Table;
import app.GUI.CafeLayoutPage;
import app.Object.Table;
import app.Object.Status;

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

        if (o.equals(layoutPage.table1)) handleTableClick(layoutPage.table1);
        if (o.equals(layoutPage.table2)) handleTableClick(layoutPage.table2);
        if (o.equals(layoutPage.table3)) handleTableClick(layoutPage.table3);
        if (o.equals(layoutPage.table4)) handleTableClick(layoutPage.table4);
        if (o.equals(layoutPage.table5)) handleTableClick(layoutPage.table5);

        if (o.equals(layoutPage.confirmedButton)) {
            layoutPage.doDispose();
        }
    }

    private void handleTableClick(TableButton tb) {
        String tableId = tb.getText();
        Table table = DAO_Table.findTable(tableId);

        if (table == null) return;

        if (!tb.isChoosen()) {
            if (table.isFull()) {
                tb.setBackground(Color.RED);
                tb.setChoosen(false);
                System.out.println("⚠ Bàn " + tableId + " đã đủ người!");
            } else {
                table.addPerson();
                tb.setChoosen(true);
                tb.setBackground(Color.ORANGE);
                System.out.println("✅ Đã thêm 1 người vào bàn " + tableId + " (" + table.getCurrentOccupancy() + "/" + table.getCapacity() + ")");
            }
        } else {
            table.removePerson();
            tb.setChoosen(false);
            tb.setBackground(table.getCurrentOccupancy() == 0 ? Color.WHITE : Color.ORANGE);
            System.out.println("↩ Đã bỏ chọn bàn " + tableId + " (" + table.getCurrentOccupancy() + "/" + table.getCapacity() + ")");
        }

        if (table.isFull()) tb.setBackground(Color.RED);
    }
}
