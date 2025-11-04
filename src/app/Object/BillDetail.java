package app.Object;

import java.io.Serializable;
import java.util.Objects;

public class BillDetail{
    private String billId;
    private String menuId; // Đổi itemId thành menuId cho đúng sơ đồ DB
    private int quantity; // soLuong
    private double amount; // đơn giá/price (từ BillDetail.amount)
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
    }

    public BillDetail(String menuId, int quantity) {
        this.menuId = menuId;
        this.quantity = quantity;
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
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
	public String getItemId() {
	    return this.menuId; 
	}

	public void setItemId(String itemId) {
	    this.menuId = itemId;
	}
    
   
}