package app.Object;

import java.time.LocalDate;

public class Bill {
    String billId;
    String empId;
    double price;
    LocalDate date;

    public Bill() {
    }

    public Bill(String billId, String empId, double price, LocalDate date) {
        this.billId = billId;
        this.empId = empId;
        this.price = price;
        this.date = date;
    }

    public String getBillId() {
        return this.billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getEmpId() {
        return this.empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
