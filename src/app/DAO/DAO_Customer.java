package app.DAO;

import app.Connection.XJdbc;
import app.Object.Customer;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DAO_Customer {
    
    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        String id = rs.getString("customerId");
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String phoneNumber = rs.getString("phoneNumber");
        String email = rs.getString("email");
        boolean sex = rs.getBoolean("sex");
        Timestamp createdTimestamp = rs.getTimestamp("customerCreatedDate");
        LocalDateTime createdDate = createdTimestamp != null ? createdTimestamp.toLocalDateTime() : null;

        return new Customer(id, firstName, lastName, phoneNumber, email, sex, createdDate);
    }
    
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM dbo.Customer ORDER BY customerId";

        try (Connection con = XJdbc.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                customers.add(mapResultSetToCustomer(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi đọc dữ liệu khách hàng: " + e.getMessage());
        }
        return customers;
    }
    
    // --- READ (Tìm kiếm theo ID) ---
    public Customer getCustomerById(String customerId) {
        String sql = "SELECT * FROM dbo.Customer WHERE customerId = ?";
        Customer customer = null;

        try (Connection con = XJdbc.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, customerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    customer = mapResultSetToCustomer(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
    

    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO dbo.Customer (customerId, firstName, lastName, phoneNumber, email, sex, customerCreatedDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = XJdbc.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, customer.getCustomerId());
            pstmt.setString(2, customer.getFirstName());
            pstmt.setString(3, customer.getLastName());
            pstmt.setString(4, customer.getPhoneNumber());
            pstmt.setString(5, customer.getEmail());
            pstmt.setBoolean(6, customer.getSex());
            // Chuyển LocalDateTime sang Timestamp để lưu vào DB
            pstmt.setTimestamp(7, Timestamp.valueOf(customer.getCreatedDate())); 

            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // --- UPDATE (Cập nhật khách hàng) ---
    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE dbo.Customer SET firstName = ?, lastName = ?, phoneNumber = ?, email = ?, sex = ? WHERE customerId = ?";
        
        try (Connection con = XJdbc.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getPhoneNumber());
            pstmt.setString(4, customer.getEmail());
            pstmt.setBoolean(5, customer.getSex());
            pstmt.setString(6, customer.getCustomerId());

            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // --- DELETE (Xóa khách hàng) ---
    public boolean deleteCustomer(String customerId) {
        String sql = "DELETE FROM dbo.Customer WHERE customerId = ?";
        
        try (Connection con = XJdbc.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, customerId);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Customer> searchCustomers(String keyword) {
        List<Customer> customers = new ArrayList<>();
        // Tìm kiếm theo ID, Họ, Tên hoặc SĐT
        String sql = "SELECT * FROM dbo.Customer WHERE customerId LIKE ? OR firstName LIKE ? OR lastName LIKE ? OR phoneNumber LIKE ? ORDER BY customerId";
        
        String searchPattern = "%" + keyword + "%";

        try (Connection con = XJdbc.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setString(4, searchPattern);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    customers.add(mapResultSetToCustomer(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi tìm kiếm khách hàng: " + e.getMessage());
        }
        return customers;
    }
}