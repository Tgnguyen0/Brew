package app.DAO;

import app.Connection.XJdbc;
import app.Object.Bill;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAO_Bill {

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
                Bill bill = new Bill();
                bill.setBillId(rs.getString("billId"));
                
                // Chuyển đổi từ SQL Date sang LocalDate
                Date sqlDateCreated = rs.getDate("dateCreated");
                bill.setDateCreated(sqlDateCreated != null ? sqlDateCreated.toLocalDate() : null);
                
                bill.setHourIn(rs.getTimestamp("hourIn"));
                bill.setHourOut(rs.getTimestamp("hourOut"));
                
                // Tên cột đúng là phoneNumber
                bill.setPhoneNumber(rs.getString("phoneNumber")); 
                bill.setTotal(rs.getDouble("total"));
                
                // Tên cột đúng là custPayment
                bill.setCustPayment(rs.getDouble("custPayment")); 
                
                bill.setStatus(rs.getString("status"));
                bill.setCustomerId(rs.getString("customerId"));
                bill.setEmployeeId(rs.getString("employeeId"));
                bill.setTableId(rs.getString("tableId"));
                
                // Lấy tên đã ghép từ ALIAS
                bill.setCustomerName(rs.getString("customerFullName")); 
                bill.setEmployeeName(rs.getString("employeeFullName"));    
                
                bill.setQuantityOfItems(0); 
                
                list.add(bill);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn SQL trong DAO_Bill.selectAll(): " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
    

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
                LocalDate date = rs.getDate("dateCreated").toLocalDate();
                Timestamp hourIn = rs.getTimestamp("hourIn");

                return new Bill(billId, date, hourIn);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public Bill getBillById(String billId) {
        String sql = "SELECT \n" +
                     "B.billId, B.dateCreated, B.hourIn, B.hourOut, B.phoneNumber, B.total, B.custPayment, B.status, B.customerId, B.employeeId, B.tableId, \n" +
                     "CONCAT(C.firstName, ' ', C.lastName) AS customerFullName, \n" +
                     "CONCAT(E.firstName, ' ', E.lastName) AS employeeFullName \n" +
                     "FROM BILL B \n" +
                     "LEFT JOIN Customer C ON B.customerId = C.customerId \n" +
                     "LEFT JOIN Employee E ON B.employeeId = E.employeeId \n" +
                     "WHERE B.billId = ? "; // Loại bỏ dấu * và đảm bảo nối chuỗi đúng

        try (Connection con = XJdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, billId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Bill bill = new Bill();
                    bill.setBillId(rs.getString("billId"));
                    
                    // Xử lý Date và chuyển sang LocalDate
                    java.sql.Date sqlDateCreated = rs.getDate("dateCreated");
                    bill.setDateCreated(sqlDateCreated != null ? sqlDateCreated.toLocalDate() : null);

                    // Ánh xạ các trường khác
                    bill.setHourIn(rs.getTimestamp("hourIn"));
                    bill.setHourOut(rs.getTimestamp("hourOut"));
                    bill.setPhoneNumber(rs.getString("phoneNumber"));
                    bill.setTotal(rs.getDouble("total"));
                    bill.setCustPayment(rs.getDouble("custPayment"));
                    bill.setStatus(rs.getString("status"));
                    bill.setCustomerId(rs.getString("customerId"));
                    bill.setEmployeeId(rs.getString("employeeId"));
                    bill.setTableId(rs.getString("tableId"));

                    // Lấy tên đầy đủ từ Alias (CONCAT)
                    bill.setCustomerName(rs.getString("customerFullName"));
                    bill.setEmployeeName(rs.getString("employeeFullName"));

                    // Thu thập chi tiết sản phẩm (BillDetail)
                    DAO_BillDetail detailDAO = new DAO_BillDetail();
                    bill.setDetails(detailDAO.selectByBillId(billId));
                    
                    return bill;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn SQL trong DAO_Bill.getBillById(): " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}