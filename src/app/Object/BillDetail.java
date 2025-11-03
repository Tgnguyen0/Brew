package app.Object;

import java.io.Serializable;
import java.util.Objects;

public class BillDetail implements Serializable {
    String billId;
    String itemId;
    int quantity;
    double total_price;
    private final double INC = 5.0;

    public BillDetail(String billId, String itemId, int quantity) {
        this.billId = billId;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public BillDetail(String itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public String getBillId() {
        return this.billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public double getINC() {
        return this.INC;
    }

    public String getItemId() {
        return this.itemId;
    }

    public void setItem(String itemId) {
        this.itemId = itemId;
    }

    public double getTotal_price() {
        return this.total_price;
    }

    public void setTotal_price(int ammount, double price) {
        this.total_price = price + INC * ammount;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillDetail that = (BillDetail) o;
        return Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }

    @Override
    public String toString() {
        return "BillDetail{" +
                "billId='" + billId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", quantity=" + quantity +
                ", total_price=" + total_price +
                ", INC=" + INC +
                '}';
    }
}
