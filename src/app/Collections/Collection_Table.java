package app.Collections;

import app.Object.Table;
import app.Object.Status;
import com.itextpdf.layout.element.Tab;

import java.util.ArrayList;
import java.util.List;

public class Collection_Table {
    private List<Table> tableList;

    public Collection_Table() {
        tableList = new ArrayList<>();
    }

    public boolean addAll(List<Table> tables) {
        return tableList.addAll(tables);
    }

    public List<Table> getAllTables() {
        return tableList;
    }

    public boolean addTable(Table t) {
        return tableList.add(t);
    }

    public Table getTableById(String id) {
        for (Table t : tableList) {
            if (t.getTableId().equalsIgnoreCase(id)) return t;
        }
        return null;
    }

    public boolean removeTable(Table t) {
        return tableList.remove(t);
    }

    public boolean removeAll() { return tableList.removeAll(tableList); }

    public void updateStatus(String id, Status status) {
        Table t = getTableById(id);
        if (t != null) {
            t.setStatus(status);
        }
    }
}
