package app.Object;

import java.util.Objects;

public class Table {
    private String tableId;
    private String floor;
    private Status status;
    private int currentOccupancy;
    private int capacity;
    public Table() {}

    public Table(String tableId, String floor, Status status, int currentOccupancy, int capacity) {
        this.tableId = tableId;
        this.floor = floor;
        this.status = status;
        this.currentOccupancy = currentOccupancy;
        this.capacity = capacity;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setCurrentOccupancy(int currentOccupancy) {
        this.currentOccupancy = currentOccupancy;
    }

    public void addPerson() {
        if (currentOccupancy < capacity) currentOccupancy++;
    }

    public void removePerson() {
        if (currentOccupancy > 0) currentOccupancy--;
    }

    public boolean isFull() {
        return currentOccupancy >= capacity;
    }

    public int getCurrentOccupancy() {
        return currentOccupancy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return Objects.equals(tableId, table.tableId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tableId);
    }

    @Override
    public String toString() {
        return "Table{" +
                "tableId='" + tableId + '\'' +
                ", floor='" + floor + '\'' +
                ", status=" + status +
                ", currentOccupancy=" + currentOccupancy +
                ", capacity=" + capacity +
                '}';
    }
}
