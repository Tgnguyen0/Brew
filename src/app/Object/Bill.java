package app.Object;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class Bill {
    private String billId;
    private LocalDate dateCreated; 
    private Timestamp hourIn;       
    private Timestamp hourOut;      
    private String phoneNumber;   
    private double total;          
    private double custPayment;    
    private String status;         
    private String customerId;
    private String employeeId;
    private String tableId;
    
    private List<BillDetail> details;
    private String customerName; 
    private String employeeName; 
    private int quantityOfItems; 

    public Bill() {
    }

    // Constructor ĐẦY ĐỦ NHẤT 
    public Bill(String billId, LocalDate dateCreated, Timestamp hourIn, Timestamp hourOut, String phoneNumber, double total, double custPayment, String status, String customerId, String employeeId, String tableId, String customerName, String employeeName) {
        this.billId = billId;
        this.dateCreated = dateCreated;
        this.hourIn = hourIn;
        this.hourOut = hourOut;
        this.phoneNumber = phoneNumber;
        this.total = total;
        this.custPayment = custPayment;
        this.status = status;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.tableId = tableId;
        this.customerName = customerName;
        this.employeeName = employeeName;
        this.quantityOfItems = 0; 
    }

    public Bill(String billId, LocalDate dateCreated, Timestamp hourIn) {
        this.billId = billId;
        this.dateCreated = dateCreated;
        this.hourIn = hourIn;
    }

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Timestamp getHourIn() {
		return hourIn;
	}

	public void setHourIn(Timestamp hourIn) {
		this.hourIn = hourIn;
	}

	public Timestamp getHourOut() {
		return hourOut;
	}

	public void setHourOut(Timestamp hourOut) {
		this.hourOut = hourOut;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getCustPayment() {
		return custPayment;
	}

	public void setCustPayment(double custPayment) {
		this.custPayment = custPayment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int getQuantityOfItems() {
		return quantityOfItems;
	}

	public void setQuantityOfItems(int quantityOfItems) {
		this.quantityOfItems = quantityOfItems;
	}

	public List<BillDetail> getDetails() {
		return details;
	}

	public void setDetails(List<BillDetail> details) {
		this.details = details;
	}


    
}