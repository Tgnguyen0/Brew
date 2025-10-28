package app.DAO;

import app.Connection.XJdbc;
import app.Object.Status;
import app.Object.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO_Table {
    public static List<Table> getAllTable() {
        String sql = "SELECT * FROM CafeTable";
        List<Table> tables = new ArrayList<Table>();

        try (Connection con = XJdbc.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Table t = new Table(
                        rs.getString("tableId"),
                        rs.getString("floor"),
                        Status.valueOf(rs.getString("status")),
                        rs.getInt("current_occupancy"),
                        rs.getInt("capacity")
                );

                tables.add(t);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tables;
    }

    public static Table findTable(String tableId) {
        String sql = "SELECT * FROM CafeTable WHERE tableId = ?";

        try (Connection con = XJdbc.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tableId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new Table(
                        rs.getString("tableId"),
                        rs.getString("floor"),
                        Status.valueOf(rs.getString("status").toUpperCase()),
                        rs.getInt("current_occupancy"),
                        rs.getInt("capacity")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
