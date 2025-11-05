package app.DAO;

import app.Connection.XJdbc;
import app.Object.BillDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DAO_BillDetail {
    public static void saveAllBD(List<BillDetail> list) {
        String sql = "INSERT INTO BillDetail (billId, menuId, amount, org_price, totalPrice) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = XJdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (BillDetail bd : list) {
                ps.setString(1, bd.getBillId());
                ps.setString(2, bd.getItemId());
                ps.setInt(3, bd.getQuantity());
                ps.setFloat(4, bd.getPrice());
                ps.setDouble(5, bd.getTotal_price());
                ps.addBatch(); // Thêm vào batch
            }

            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
