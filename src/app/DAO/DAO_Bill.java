
package app.DAO;

import app.Connection.XJdbc;
import app.Object.Bill;
import app.Object.BillDetail;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAO_Bill {
	public List<Bill> selectTop35() {
        // Cú pháp SQL Server: TOP 35 (hoặc 100 tùy theo nhu cầu)
        String sql = """
            SELECT TOP 35 
                billId, dateCreated, hourIn, hourOut, 
                phoneNumber, total, custPayment, status, 
                customerId, employeeId, tableId
            FROM BILL
            ORDER BY dateCreated DESC, hourIn DESC
        """;

        List<Bill> list = new ArrayList<>();

        try (Connection con = XJdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
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

                bill.setCustomer(DAO_Customer.getCustomerById(rs.getString("customerId")));
                bill.setEmployee(DAO_Employee.getEmployeeById(rs.getString("employeeId")));
                bill.setTable(DAO_Table.findTable(rs.getString("tableId")));
//                String billId = bill.getBillId();
//                List<BillDetail> details = detailDAO.selectByBillId(billId);
//                bill.setDetails(details);
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
        String sql = """
            SELECT 
                billId, dateCreated, hourIn, hourOut, 
                phoneNumber, total, custPayment, status, 
                customerId, employeeId, tableId
            FROM BILL
            WHERE billId = ?
        """;

        try (Connection con = XJdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, billId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Bill bill = new Bill();
                    bill.setBillId(rs.getString("billId"));

                    java.sql.Date sqlDateCreated = rs.getDate("dateCreated");
                    bill.setDateCreated(sqlDateCreated != null ? sqlDateCreated.toLocalDate() : null);

                    bill.setHourIn(rs.getTimestamp("hourIn"));
                    bill.setHourOut(rs.getTimestamp("hourOut"));
                    bill.setPhoneNumber(rs.getString("phoneNumber"));
                    bill.setTotal(rs.getDouble("total"));
                    bill.setCustPayment(rs.getDouble("custPayment"));
                    bill.setStatus(rs.getString("status"));

                    bill.setCustomer(DAO_Customer.getCustomerById(rs.getString("customerId")));
                    bill.setEmployee(DAO_Employee.getEmployeeById(rs.getString("employeeId")));
                    bill.setTable(DAO_Table.findTable(rs.getString("tableId")));

                    bill.setDetails(DAO_BillDetail.selectByBillId(billId));

                    return bill;
                }
            }

        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn SQL trong DAO_Bill.getBillById(): " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public List<Bill> getBillsByCustomerId(String customerId) {
        String sql = "SELECT * FROM BILL WHERE customerId = ? ORDER BY dateCreated DESC, hourIn DESC";
        List<Bill> list = new ArrayList<>();

        try (Connection con = XJdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // THAY THẾ LOGIC BỊ LỖI BẰNG LOGIC MAPPING ĐẦY ĐỦ VÀ ROBUST
                    Bill bill = new Bill();
                    bill.setBillId(rs.getString("billId"));

                    java.sql.Date sqlDateCreated = rs.getDate("dateCreated");
                    bill.setDateCreated(sqlDateCreated != null ? sqlDateCreated.toLocalDate() : null);

                    bill.setHourIn(rs.getTimestamp("hourIn"));
                    bill.setHourOut(rs.getTimestamp("hourOut"));
                    bill.setPhoneNumber(rs.getString("phoneNumber"));
                    bill.setTotal(rs.getDouble("total"));
                    bill.setCustPayment(rs.getDouble("custPayment"));
                    bill.setStatus(rs.getString("status"));

                    // ✅ Lấy đối tượng Customer, Employee, Table đầy đủ qua ID
                    // (Giả định DAO_Customer, DAO_Employee, DAO_Table đã được import)
                    bill.setCustomer(DAO_Customer.getCustomerById(rs.getString("customerId")));
                    bill.setEmployee(DAO_Employee.getEmployeeById(rs.getString("employeeId")));
                    bill.setTable(DAO_Table.findTable(rs.getString("tableId")));

                    // Lấy chi tiết hóa đơn
                    bill.setDetails(new DAO_BillDetail().selectByBillId(bill.getBillId()));

                    list.add(bill);
                }
            }

        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn SQL trong DAO_Bill.getBillsByCustomerId(): " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }


    public static boolean updateBill(Bill bill) {
        String sql = """
        UPDATE BILL
        SET 
            dateCreated = ?, 
            hourIn = ?, 
            hourOut = ?, 
            phoneNumber = ?, 
            total = ?, 
            custPayment = ?, 
            status = ?, 
            customerId = ?, 
            employeeId = ?, 
            tableId = ?
        WHERE billId = ?
    """;

        try (Connection con = XJdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // 1️⃣ Gán giá trị cho từng tham số
            ps.setDate(1, bill.getDateCreated() != null ? Date.valueOf(bill.getDateCreated()) : null);
            ps.setTimestamp(2, bill.getHourIn());
            ps.setTimestamp(3, bill.getHourOut());
            ps.setString(4, bill.getPhoneNumber());
            ps.setDouble(5, bill.getTotal());
            ps.setDouble(6, bill.getCustPayment());
            ps.setString(7, bill.getStatus());

            // ⚠️ Nếu customer/employee/table có thể null thì cần kiểm tra
            ps.setString(8, bill.getCustomer() != null ? bill.getCustomer().getCustomerId() : null);
            ps.setString(9, bill.getEmployee() != null ? bill.getEmployee().getId() : null);
            ps.setString(10, bill.getTable() != null ? bill.getTable().getTableId() : null);

            ps.setString(11, bill.getBillId());

            // 2️⃣ Thực thi câu lệnh
            int rows = ps.executeUpdate();
            return rows > 0; // Trả về true nếu có dòng bị ảnh hưởng

        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn SQL trong DAO_Bill.updateBill(): " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}
