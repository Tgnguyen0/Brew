package app.Object;

import java.io.Serializable;
import java.util.Objects;

public class BillDetail {
    private String billId;
    private String menuId; // Đổi itemId thành menuId cho đúng sơ đồ DB
    private int quantity; // soLuong
    private float price; // đơn giá/price (từ BillDetail.amount)
    private double totalPrice; // Đây là thuộc tính dẫn xuất nên không truyền giá trị
    
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
        Total_price();
    }

    public BillDetail(String itemId, int quantity, float price, String name, String category) {
        this.menuId = itemId;
        this.quantity = quantity;
        this.price = price;
        this.itemName = name;
        this.category = category;
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

    public void setQuantity(int quantity) {
      this.quantity = quantity;
    }

    public int getQuantity() {
        return this.quantity;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public double getTotal_price() {
        return this.totalPrice;
    }

    // Thuộc tính dẫn xuất. Chỉ kêu khi đã truyền price với amount
    public void Total_price() {
        this.totalPrice = price * quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillDetail that = (BillDetail) o;
        return Objects.equals(menuId, that.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId);
    }

    @Override
    public String toString() {
        return "BillDetail{" +
                "billId='" + billId + '\'' +
                ", menuId='" + menuId + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", itemName='" + itemName + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}