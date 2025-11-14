package app.DAO;

import app.Connection.XJdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import app.Object.Account;

public class LoginDAO {

    public static String login(String username, String password) {
        String sql = "SELECT role, employeeId FROM Account WHERE username = ? AND password = ?";
        try (Connection conn = XJdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Ghép cả role và employeeId vào một chuỗi để trả về
                    String role = rs.getString("role");
                    String employeeId = rs.getString("employeeId");
                    return role + "," + employeeId;  // Ví dụ: "ADMIN,EMP001"
                }
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi đăng nhập: " + e.getMessage());
        }
        return null; // Không tìm thấy tài khoản
    }

    public static Account loginAccount(String username, String password) {
        String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
        try (Connection conn = XJdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
//                    // Trả về Account
//                    String role = rs.getString("role");
//                    String employeeId = rs.getString("employeeId");
//                    return role + "," + employeeId;  // Ví dụ: "ADMIN,EMP001"

                    return new Account(
                            rs.getString("accountId"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role"),
                            DAO_Employee.getEmployeeById(rs.getString("employeeId"))
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi đăng nhập: " + e.getMessage());
        }
        return null; // Không tìm thấy tài khoản
    }
}