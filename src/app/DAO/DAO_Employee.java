package app.DAO;

import app.Connection.XJdbc;
import app.Object.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO_Employee {

    // Lấy tất cả nhân viên
    public static List<Employee> findAll() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM Employee";

        try (Connection con = XJdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(extractEmployee(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // Tìm kiếm theo từ khóa (tên/số điện thoại/email)
    public static List<Employee> search(String keyword) {
        List<Employee> list = new ArrayList<>();
        String sql = """
            SELECT * FROM Employee 
            WHERE firstName LIKE ? 
               OR lastName LIKE ? 
               OR phoneNumber LIKE ? 
               OR email LIKE ?
               OR role LIKE ?
        """;

        try (Connection con = XJdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);
            ps.setString(4, kw);
            ps.setString(5, kw);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractEmployee(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // Thêm nhân viên (không truyền employeeId → DB tự generate)
    public static void insert(Employee e) {
        String sql = """
            INSERT INTO Employee (firstName, lastName, sex, phoneNumber, email, role, address)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = XJdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getFirstName());
            ps.setString(2, e.getLastName());
            ps.setBoolean(3, e.isSex());
            ps.setString(4, e.getPhoneNumber());
            ps.setString(5, e.getEmail());
            ps.setString(6, e.getRole());
            ps.setString(7, e.getAddress());
            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    // Cập nhật nhân viên
    public static void update(Employee e) {
        String sql = """
            UPDATE Employee SET 
                firstName=?, 
                lastName=?, 
                sex=?, 
                phoneNumber=?, 
                email=?, 
                role=?, 
                address=?
            WHERE employeeId=?
        """;

        try (Connection con = XJdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getFirstName());
            ps.setString(2, e.getLastName());
            ps.setBoolean(3, e.isSex());
            ps.setString(4, e.getPhoneNumber());
            ps.setString(5, e.getEmail());
            ps.setString(6, e.getRole());
            ps.setString(7, e.getAddress());
            ps.setString(8, e.getEmployeeId());
            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    // Xóa nhân viên
    public static void delete(String id) {
        String sql = "DELETE FROM Employee WHERE employeeId=?";

        try (Connection con = XJdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Lấy nhân viên theo id (dùng khi nhấp chọn trong bảng)
    public static Employee getEmployeeById(String empId) {
        String sql = "SELECT * FROM Employee WHERE employeeId = ?";

        try (Connection con = XJdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, empId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return extractEmployee(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // Tái sử dụng hàm dựng Employee từ ResultSet
    private static Employee extractEmployee(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getString("employeeId"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getBoolean("sex"),
                rs.getString("phoneNumber"),
                rs.getString("email"),
                rs.getString("role"),
                rs.getString("address")
        );
        }
}
