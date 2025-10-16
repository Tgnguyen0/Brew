package app.Object;

import java.io.Serializable;

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

    public void setTotal_price(int quantity, double item_price) {
        this.total_price = quantity * (item_price + INC);
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
