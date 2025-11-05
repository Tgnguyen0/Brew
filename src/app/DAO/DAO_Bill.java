package app.DAO;

import app.Connection.XJdbc;
import app.Object.Bill;
import app.Object.BillDetail;
import app.Object.Customer;
import app.Object.Employee;
import app.Object.Table;
// Giả định bạn có lớp Status cho Table.java
// import app.Object.Status; 

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAO_Bill {

	private DAO_BillDetail detailDAO = new DAO_BillDetail();
	
    // ***************************************************************
    // CÁC HÀM TRA CỨU OBJECT (Cần được thay bằng DAO thực tế)
    // ***************************************************************
    // Tạm thời trả về object mới để tránh NullPointer, sử dụng constructor của bạn
	private Customer getCustomerById(String id) {
        if (id == null || id.isEmpty()) return null;
        // Customer(String customerId, String firstName, String lastName, String phoneNumber, String email, Boolean sex, LocalDateTime createdDate)
        return new Customer(id, "", "", null, null, false, null); 
    }
    
    private Employee getEmployeeById(String id) { 
        if (id == null || id.isEmpty()) return null;
        // Employee(String id, String name, String gender, LocalDate dob, String phone, String email, String responsibility)
        return new Employee(id, "", "", null, null, null, null);
    }
    
    private Table getTableById(String id) { 
        if (id == null || id.isEmpty()) return null;
        // Table(String tableId, String floor, Status status, int currentOccupancy, int capacity)
        // Cần truyền Status (null tạm thời nếu chưa có lớp Status)
        return new Table(id, "", null, 0, 0); 
    }
	
    // ***************************************************************
    // 1. SELECT ALL (ĐÃ SỬA LỖI VÀ REFACCTOR)
    // ***************************************************************
    public List<Bill> selectAll() {
		String sql = "SELECT TOP 35 \n" + 
                "    B.billId, B.dateCreated, B.hourIn, B.hourOut, B.phoneNumber, B.total, B.custPayment, B.status, B.customerId, B.employeeId, B.tableId,\n" +
                "    CONCAT(C.firstName, ' ', C.lastName) AS customerFullName, \n" + 
                "    CONCAT(E.firstName, ' ', E.lastName) AS employeeFullName \n" +  
                "FROM \n" +
                "    BILL B \n" +
                "LEFT JOIN \n" +
                "    Customer C ON B.customerId = C.customerId \n" +
                "LEFT JOIN \n" +
                "    Employee E ON B.employeeId = E.employeeId \n" +
                "ORDER BY B.dateCreated DESC, B.hourIn DESC";
        
        List<Bill> list = new ArrayList<>();
        try (Connection con = XJdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Ánh xạ các trường cơ bản (Không bao gồm Object/Name/Quantity)
                Bill bill = mapResultSetToBill(rs);
                String currentBillId = bill.getBillId();
                
                // 1. Customer: KHỞI TẠO VÀ GÁN OBJECT
                String customerId = rs.getString("customerId");
                Customer customer = getCustomerById(customerId); 
                if (customer != null && rs.getString("customerFullName") != null) {
                    customer.setFullName(rs.getString("customerFullName")); 
                }
                bill.setCustomer(customer); 
                
                // 2. Employee: KHỞI TẠO VÀ GÁN OBJECT
                String employeeId = rs.getString("employeeId");
                Employee employee = getEmployeeById(employeeId); 
                if (employee != null && rs.getString("employeeFullName") != null) {
                    employee.setName(rs.getString("employeeFullName")); // Dùng setName()
                }
                bill.setEmployee(employee); 

                // 3. Table: KHỞI TẠO VÀ GÁN OBJECT
                bill.setTable(getTableById(rs.getString("tableId"))); 

                // 4. TẢI BILL DETAIL (Cần thiết cho getQuantityOfItems() trong Bill.java)
                bill.setDetails(detailDAO.selectByBillId(currentBillId)); 
                
                list.add(bill);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn SQL trong DAO_Bill.selectAll(): " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
    
    // ***************************************************************
    // 2. GET BILLS BY CUSTOMER ID (ĐÃ SỬA LỖI VÀ REFACCTOR)
    // ***************************************************************
	public List<Bill> getBillsByCustomerId(String customerId) {
        // Cần JOIN Employee để lấy employeeFullName, nếu không sẽ bị Null khi hiển thị
        String sql = "SELECT \n" +
                     "    B.billId, B.dateCreated, B.hourIn, B.hourOut, B.phoneNumber, B.total, B.custPayment, B.status, B.customerId, B.employeeId, B.tableId,\n" +
                     "    CONCAT(C.firstName, ' ', C.lastName) AS customerFullName, \n" +
                     "    CONCAT(E.firstName, ' ', E.lastName) AS employeeFullName \n" + // JOIN Employee để lấy tên
                     "FROM \n" +
                     "    BILL B \n" +
                     "LEFT JOIN \n" +
                     "    Customer C ON B.customerId = C.customerId \n" +
                     "LEFT JOIN \n" +
                     "    Employee E ON B.employeeId = E.employeeId \n" + // Thêm JOIN Employee
                     "WHERE B.customerId = ? \n" +
                     "ORDER BY B.dateCreated DESC, B.hourIn DESC";
        
        List<Bill> list = new ArrayList<>();
        try (Connection con = XJdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Bill bill = mapResultSetToBill(rs);
                    String currentBillId = bill.getBillId();
                    
                    // 1. Customer
                    Customer customer = getCustomerById(rs.getString("customerId")); 
                    if (customer != null && rs.getString("customerFullName") != null) {
                        customer.setFullName(rs.getString("customerFullName"));
                    }
                    bill.setCustomer(customer);
                    
                    // 2. Employee
                    Employee employee = getEmployeeById(rs.getString("employeeId")); 
                    if (employee != null && rs.getString("employeeFullName") != null) {
                        employee.setName(rs.getString("employeeFullName")); 
                    }
                    bill.setEmployee(employee);
                    
                    // 3. Table
                    bill.setTable(getTableById(rs.getString("tableId")));

                    // 4. TẢI BILL DETAIL (Đảm bảo quantityOfItems được tính đúng)
                    bill.setDetails(detailDAO.selectByBillId(currentBillId));
                    
                    list.add(bill);
                }
            }

        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn SQL trong DAO_Bill.getBillsByCustomerId(): " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
    
    // ***************************************************************
    // 3. HÀM HỖ TRỢ: ÁNH XẠ KẾT QUẢ SANG BILL (ĐÃ SỬA LỖI)
    // ***************************************************************
    private Bill mapResultSetToBill(ResultSet rs) throws SQLException {
        Bill bill = new Bill();
        bill.setBillId(rs.getString("billId"));
        
        Date sqlDateCreated = rs.getDate("dateCreated");
        bill.setDateCreated(sqlDateCreated != null ? sqlDateCreated.toLocalDate() : null);
        
        bill.setHourIn(rs.getTimestamp("hourIn"));
        bill.setHourOut(rs.getTimestamp("hourOut"));
        bill.setPhoneNumber(rs.getString("phoneNumber"));
        bill.setTotal(rs.getDouble("total"));
        bill.setCustPayment(rs.getDouble("custPayment"));
        bill.setStatus(rs.getString("status"));
        
        // ** LOẠI BỎ CÁC DÒNG CŨ ĐÃ BỊ LỖI KHI REFACCTOR SANG OBJECT **
        // bill.setCustomerId(rs.getString("customerId")); 
        // bill.setEmployeeId(rs.getString("employeeId"));
        // bill.setTableId(rs.getString("tableId"));
        // bill.setCustomerName(rs.getString("customerFullName"));
        // bill.setEmployeeName(rs.getString("employeeFullName"));
        // bill.setQuantityOfItems(0); 
        
        return bill;
    }
    
    // ***************************************************************
    // 4. HÀM CALCULATE TOTAL QUANTITY (ĐÃ BỎ/TÁI CẤU TRÚC)
    // ***************************************************************
    // HÀM NÀY ĐƯỢC TẠO RA ĐỂ TRUY VẤN LẠI DB, DỄ DẪN ĐẾN VÒNG LẶP.
    // Tốt nhất là KHÔNG sử dụng nó nữa, mà thay bằng bill.setDetails() 
    // và để Bill.java tự tính toán.

    // ***************************************************************
    // 5. GET BILL BY ID (ĐÃ SỬA LỖI VÀ REFACCTOR)
    // ***************************************************************
    public Bill getBillById(String billId) {
        String sql = "SELECT \n" +
                     "B.billId, B.dateCreated, B.hourIn, B.hourOut, B.phoneNumber, B.total, B.custPayment, B.status, B.customerId, B.employeeId, B.tableId, \n" +
                     "CONCAT(C.firstName, ' ', C.lastName) AS customerFullName, \n" +
                     "CONCAT(E.firstName, ' ', E.lastName) AS employeeFullName \n" +
                     "FROM BILL B \n" +
                     "LEFT JOIN Customer C ON B.customerId = C.customerId \n" +
                     "LEFT JOIN Employee E ON B.employeeId = E.employeeId \n" +
                     "WHERE B.billId = ? "; 

        try (Connection con = XJdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, billId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Bill bill = mapResultSetToBill(rs);
                    String currentBillId = bill.getBillId();
                    
                    // 1. Customer
                    Customer customer = getCustomerById(rs.getString("customerId")); 
                    if (customer != null && rs.getString("customerFullName") != null) {
                        customer.setFullName(rs.getString("customerFullName"));
                    }
                    bill.setCustomer(customer);
                    
                    // 2. Employee
                    Employee employee = getEmployeeById(rs.getString("employeeId")); 
                    if (employee != null && rs.getString("employeeFullName") != null) {
                        employee.setName(rs.getString("employeeFullName")); 
                    }
                    bill.setEmployee(employee);
                    
                    // 3. Table
                    bill.setTable(getTableById(rs.getString("tableId")));

                    // 4. TẢI BILL DETAIL
                    bill.setDetails(detailDAO.selectByBillId(currentBillId));
                    
                    return bill;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn SQL trong DAO_Bill.getBillById(): " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    // ***************************************************************
    // 6. CÁC PHƯƠNG THỨC CÒN LẠI (GIỮ NGUYÊN)
    // ***************************************************************
    public static void createBill() {
        String sql = "INSERT INTO BILL (dateCreated, hourIn) VALUES (GETDATE(), GETDATE())";

        try (Connection con = XJdbc.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Bill getLatestBill() {
        String sql = "SELECT TOP 1 billId, dateCreated, hourIn FROM BILL ORDER BY billId DESC"; 

        try (Connection con = XJdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String billId = rs.getString("billId");
                // Cần kiểm tra null trước khi gọi toLocalDate()
                Date date = rs.getDate("dateCreated");
                LocalDate localDate = date != null ? date.toLocalDate() : null;
                
                Timestamp hourIn = rs.getTimestamp("hourIn");

                return new Bill(billId, localDate, hourIn);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}