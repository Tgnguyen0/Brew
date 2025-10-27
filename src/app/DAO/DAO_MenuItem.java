package app.DAO;

import app.Connection.XJdbc;
import app.Object.MenuItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO_MenuItem {
    public static List<MenuItem> getAllMenuItem() {
        String sql = "SELECT * FROM MenuItem";
        List<MenuItem> menu = new ArrayList<>();

        try (Connection con = XJdbc.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MenuItem ni = new MenuItem(
                        rs.getString("itemId"),
                        rs.getString("item_name"),
                        rs.getFloat("price"),
                        rs.getString("category"),
                        rs.getString("description")
                );

                menu.add(ni);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return menu;
    }
}
