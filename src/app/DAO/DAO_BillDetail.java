package app.DAO;

import app.Connection.XJdbc;
import app.Object.Bill;
import app.Object.BillDetail;
import app.Object.MenuItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO_BillDetail {

    public List<BillDetail> selectByBillId(String billId) {
        String sql = "SELECT \n" +
                "    BD.billDetailId, BD.billId, BD.menuId, BD.amount, BD.org_price, BD.totalPrice, \n" +
                "    MI.item_name, MI.category \n" + 
                "FROM \n" +
                "    BillDetail BD \n" +
                "JOIN \n" +
                "    MenuItem MI ON BD.menuId = MI.itemId \n" + 
                "WHERE \n" +
                "    BD.billId = ?";

        List<BillDetail> list = new ArrayList<>();
        
        try (Connection con = XJdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, billId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BillDetail bd = new BillDetail();
                    bd.setBill(new Bill());
                    bd.setMenuItem(new MenuItem());
                    
                    bd.getBill().setBillId(rs.getString("billId"));
                    //bd.setBillId(rs.getString("billId"));
                    bd.getMenuItem().setItemId(rs.getString("menuId"));
                    //bd.setMenuId(rs.getString("menuId"));
                    bd.setQuantity(rs.getInt("amount")); 
                    bd.setAmount(rs.getDouble("org_price"));
                    bd.setTotalPrice(rs.getDouble("totalPrice")); ;
                    
                    bd.getMenuItem().setName(rs.getString("item_name"));
                    bd.getMenuItem().setCategory(rs.getString("category"));

                    //bd.setItemName(rs.getString("item_name"));
                    //bd.setCategory(rs.getString("category"));
                    
                    list.add(bd);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn SQL trong DAO_BillDetail.selectByBillId(): " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
}