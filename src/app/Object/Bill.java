package app.Object;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class Bill implements Serializable{
	private String billId;
	private LocalDate dateCreated;
	private Timestamp hourIn;
	private Timestamp hourOut;
	private String phoneNumber;
	private double total;
	private double custPayment;
	private String status;
	private List<BillDetail> details;
	private Customer customer;
	private Employee employee;
	private Table table;

	public Bill() {
		super();
		this.customer = new Customer();
		this.employee = new Employee();
		this.table = new Table();
	}

	public Bill(String billId, LocalDate dateCreated, Timestamp hourIn) {
		this.billId = billId;
		this.dateCreated = dateCreated;
		this.hourIn = hourIn;
	}

	public Bill(String billId, LocalDate dateCreated, Timestamp hourIn, Timestamp hourOut, String phoneNumber,
				double total, double custPayment, String status,
				List<BillDetail> details, Customer customer, Employee employee, Table table) {
		this.billId = billId;
		this.dateCreated = dateCreated;
		this.hourIn = hourIn;
		this.hourOut = hourOut;
		this.phoneNumber = phoneNumber;
		this.total = total;
		this.custPayment = custPayment;
		this.status = status;

		this.details = details;
		this.customer = customer;
		this.employee = employee;
		this.table=table;
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

	public List<BillDetail> getDetails() {
		return details;
	}

	public void setDetails(List<BillDetail> details) {
		this.details = details;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public int getQuantityOfItems() {
		if (details == null) {
			return 0;
		}
		int totalQuantity = 0;
		for (BillDetail detail : details) {
			totalQuantity += detail.getQuantity();
		}
		return totalQuantity;
	}

	public double calculateTotal() {
		if (this.details != null && !this.details.isEmpty()) {
			double sum = 0.0;
			for (BillDetail detail : this.details) {
				sum += detail.getTotal_price();
			}
			return sum;
		}
		return this.total;
	}

	@Override
	public String toString() {
		return "Bill{" +
				"billId='" + billId + '\'' +
				", dateCreated=" + dateCreated +
				", hourIn=" + hourIn +
				", hourOut=" + hourOut +
				", phoneNumber='" + phoneNumber + '\'' +
				", total=" + total +
				", custPayment=" + custPayment +
				", status='" + status + '\'' +
				", details=" + details +
				'}';
	}
}