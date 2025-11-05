package app.Object;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class BillDetail implements Serializable{
    private Bill bill;
    private MenuItem menuItem; 
    private int quantity; 
    private double amount; 
    private double totalPrice; 
    

    public BillDetail() {} 

    

	public BillDetail(Bill bill,MenuItem menuItem, int quantity, double amount, double totalPrice) {
		super();
		this.bill = bill;
		this.menuItem = menuItem;
		this.quantity = quantity;
		this.amount = amount;
		this.totalPrice = totalPrice;
	}

	


	public Bill getBill() {
		return bill;
	}


	public void setBill(Bill bill) {
		this.bill = bill;
	}




	public MenuItem getMenuItem() {
		return menuItem;
	}



	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
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


}