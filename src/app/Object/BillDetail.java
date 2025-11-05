package app.Object;

import java.io.Serializable;
import java.util.Objects;

public class BillDetail{
    private String billId;
    private String menuId; // Đổi itemId thành menuId cho đúng sơ đồ DB
    private int quantity; // soLuong
    private double price; // đơn giá/price (từ BillDetail.amount)
    private double totalPrice; // totalPrice (từ BillDetail.totalPrice)
    
    // Thuộc tính bổ sung từ MenuItem (sau khi JOIN)
    private String itemName;   
    private String category;   

    // Constructor mặc định cần thiết cho DAO
    public BillDetail() {} 

    // Constructor đã có, giữ lại
    public BillDetail(String billId, String menuId, int quantity) {
        this.billId = billId;
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
        Total_price();
    }

    public BillDetail(String menuId, int quantity) {
        this.menuId = menuId;
    }

    public BillDetail(String itemId, int quantity, float price) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
        Total_price();
    }

    public String getBillId() {
      return billId;
    }

    public void setBillId(String billId) {
      this.billId = billId;
    }

    public String getMenuId() {
      return menuId;
    }

    public void setMenuId(String menuId) {
      this.menuId = menuId;
    }

    public int getQuantity() {
      return quantity;
    }

    public void setQuantity(int quantity) {
      this.quantity = quantity;
    }

    public String getItemName() {
      return itemName;
    }

    public void setItemName(String itemName) {
      this.itemName = itemName;
    }

    public String getCategory() {
      return category;
    }

    public void setCategory(String category) {
      this.category = category;
    }

    public double getINC() {
        return this.INC;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public double getTotal_price() {
        return this.total_price;
    }

    public void Total_price() {
        this.total_price = price * quantity;
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
