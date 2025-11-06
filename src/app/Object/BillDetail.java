package app.Object;

import java.io.Serializable;
import java.util.Objects;

public class BillDetail {
    private String billId;
    private String menuId; 
    private int quantity; 
    private float price; 
    private double totalPrice; 
    

    private String itemName;   
    private String category;   


    public BillDetail() {} 

    

    public BillDetail(String billId, String menuId, int quantity, float price, double totalPrice, String itemName,
			String category) {
		super();
		this.billId = billId;
		this.menuId = menuId;
		this.quantity = quantity;
		this.price = price;
		this.totalPrice = totalPrice;
		this.itemName = itemName;
		this.category = category;
	}



	public BillDetail(String itemId, int quantity, float price) {
        this.menuId = itemId;
        this.quantity = quantity;
        this.price = price;
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

//    public double getTotal_price() {
//        return this.totalPrice;
//    }
//
//    public void Total_price() {
//        this.totalPrice = price * quantity;
//    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillDetail that = (BillDetail) o;
        return Objects.equals(menuId, that.menuId);
    }

    public double getTotalPrice() {
		return totalPrice;
	}



	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
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