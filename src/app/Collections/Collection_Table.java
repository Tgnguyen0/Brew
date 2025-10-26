package app.Collections;

import app.Object.Table;

import java.util.ArrayList;
import java.util.List;

public class Collection_Table {
    List<Table> tableList;

    public Collection_Table() {
        tableList = new ArrayList<Table>();
    }

    public boolean addAll(List<Table> tables) {
        return tableList.addAll(tables);
    }
}
