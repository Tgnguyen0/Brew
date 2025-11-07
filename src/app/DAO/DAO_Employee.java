package app.DAO;

import app.Connection.XJdbc;
import app.Object.Employee;
import app.Object.Status;
import app.Object.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Employee {
    public static Employee getEmployeeById(String empId) {
        String sql = "SELECT * FROM Employee WHERE employeeId = ?";

        try (Connection con = XJdbc.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, empId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}