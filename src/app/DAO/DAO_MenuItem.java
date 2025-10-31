package app.DAO;

import app.Connection.XJdbc;
import app.Object.MenuItem;

import java.awt.*;
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

    public static List<MenuItem> get18MenuItems(int offset, int limit) {
        List<MenuItem> items = new ArrayList<>();
        String sql = "SELECT * FROM MenuItem ORDER BY itemId OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = XJdbc.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MenuItem item = new MenuItem(
                        rs.getString("itemId"),
                        rs.getString("item_name"),
                        rs.getFloat("price"),
                        rs.getString("category"),
                        rs.getString("description")
                );
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public static List<MenuItem> findMultipleMenuItem(String name) {
        List<MenuItem> list = new ArrayList<MenuItem>();
        String sql = "SELECT * FROM MenuItem WHERE item_name LIKE ?";

        try (Connection con = XJdbc.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + name.trim() + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MenuItem mi = new MenuItem(
                        rs.getString("itemId"),
                        rs.getString("item_name"),
                        rs.getFloat("price"),
                        rs.getString("category"),
                        rs.getString("description")
                );

                list.add(mi);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public static List<MenuItem> listOfMenuItemByCategory(String category) {
        List<MenuItem> list = new ArrayList<MenuItem>();
        String sql = "SELECT * FROM MenuItem WHERE category = ?";

        try (Connection con = XJdbc.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MenuItem mi = new MenuItem(
                        rs.getString("itemId"),
                        rs.getString("item_name"),
                        rs.getFloat("price"),
                        rs.getString("category"),
                        rs.getString("description")
                );

                list.add(mi);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
}
