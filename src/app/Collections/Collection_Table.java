package app.Collections;

import app.Object.Table;
import app.Object.Status;
import java.util.ArrayList;
import java.util.List;

public class Collection_Table {
    private List<Table> tableList;

    public Collection_Table() {
        tableList = new ArrayList<>();
        // Khởi tạo 5 bàn (bạn có thể thay đổi số lượng)
        for (int i = 1; i <= 5; i++) {
            tableList.add(new Table("B" + i, "Tầng trệt", Status.AVAILABLE, 3));
        }
    }

    public boolean addAll(List<Table> tables) {
        return tableList.addAll(tables);
    }

    public List<Table> getAllTables() {
        return tableList;
    }

    public Table getTableById(String id) {
        for (Table t : tableList) {
            if (t.getTableId().equalsIgnoreCase(id)) return t;
        }
        return null;
    }

    public void updateStatus(String id, Status status) {
        Table t = getTableById(id);
        if (t != null) {
            t.setStatus(status);
        }
    }
}
