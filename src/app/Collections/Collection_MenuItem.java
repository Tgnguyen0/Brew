package app.Collections;

import app.Object.MenuItem;

import java.util.ArrayList;

public class Collection_MenuItem {
    private ArrayList<MenuItem> listOfItem;

    public Collection_MenuItem() {
        this.listOfItem = new ArrayList<>();
    }

    public ArrayList<MenuItem> getListOfItem() {
        return this.listOfItem;
    }

    public boolean addItem(MenuItem newItem) {
        return listOfItem.add(newItem);
    }

    public boolean addAllItem(ArrayList<MenuItem> newList) {
        return listOfItem.addAll(newList);
    }

    public boolean deleteItem(String id) {
        for (MenuItem i: listOfItem) {
            if (i.getItemId().equals(id)) {
                return listOfItem.remove(i);
            }
        }

        return false;
    }

    public MenuItem findItem(String id) {
        for (int i = 0 ; i < listOfItem.size() ; i++) {
            if (id.equals(listOfItem.get(i).getItemId())) {
                return listOfItem.get(i);
            }
        }

        return null;
    }
}
