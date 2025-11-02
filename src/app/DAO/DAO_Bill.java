package app.DAO;

import app.Connection.XJdbc;
import app.Object.Bill;

import java.sql.*;
import java.time.LocalDate;

public class DAO_Bill {
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
        String sql = "SELECT TOP 1 * FROM BILL ORDER BY billId DESC";

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

}