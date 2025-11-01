package app.DAO;

import app.Connection.XJdbc;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThongkeDAO {

    // =================== DOANH THU HÔM NAY ===================
    public String getTodayRevenue() {
        String sql = """
            SELECT COALESCE(SUM(total), 0) AS revenue
            FROM Bill
            WHERE CAST(dateCreated AS DATE) = CAST(GETDATE() AS DATE)
        """;
        try (Connection conn = XJdbc.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return String.format("%,.0f VND", rs.getDouble("revenue"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "0 VND";
    }

    // =================== SỐ ĐƠN HÔM NAY ===================
    public String getTodayOrderCount() {
        String sql = """
            SELECT COUNT(*) AS totalOrders
            FROM Bill
            WHERE CAST(dateCreated AS DATE) = CAST(GETDATE() AS DATE)
        """;
        try (Connection conn = XJdbc.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("totalOrders") + " đơn";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "0 đơn";
    }

    // =================== KHÁCH HÀNG MỚI TRONG NGÀY ===================
    public String getNewCustomersToday() {
        String sql = """
            SELECT COUNT(*) AS newCustomers
            FROM Customer
            WHERE CAST(customerCreatedDate AS DATE) = CAST(GETDATE() AS DATE)
        """;
        try (Connection conn = XJdbc.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("newCustomers") + " người";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "0 người";
    }

    // =================== BIỂU ĐỒ TRÒN: DOANH THU THEO LOẠI MÓN (THEO THÁNG/NĂM) ===================
    public DefaultPieDataset getRevenueByCategory(int month, int year) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        String sql = """
        SELECT m.category, SUM(BD.totalPrice) AS totalRevenue
        FROM BillDetail BD
        JOIN Bill B ON BD.billId = B.billId
        JOIN MenuItem M ON BD.menuId = M.itemId
        WHERE MONTH(B.dateCreated) = ? AND YEAR(B.dateCreated) = ?
        GROUP BY M.category
    """;

        try (Connection conn = XJdbc.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();
            boolean hasData = false;

            while (rs.next()) {
                dataset.setValue(rs.getString("category"), rs.getDouble("totalRevenue"));
                hasData = true;
            }

            if (!hasData) {
                dataset.setValue("Không có dữ liệu", 100);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataset;
    }


    // =================== BIỂU ĐỒ LINE: DOANH THU THEO LOẠI SẢN PHẨM TRONG NĂM ===================
    // Trong ThongkeDAO
    public DefaultCategoryDataset getRevenueByMonthAndCategory(int year) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Chuẩn bị danh sách tháng và danh sách category mong muốn (có thể lấy từ DB nếu cần)
        String[] months = new String[12];
        for (int m = 1; m <= 12; m++) months[m - 1] = "Tháng " + m;

        // Lấy danh sách category từ bảng MenuItem (bảo đảm thứ tự cố định nếu bạn muốn)
        List<String> categories = new ArrayList<>();
        // Ví dụ: ưu tiên thứ tự: Cà phê, Trà, Bánh ngọt, Khác
        categories.add("Cà phê");
        categories.add("Trà");
        categories.add("Bánh ngọt");
        categories.add("Khác");
        // Nếu bạn muốn tự động lấy từ DB, thực hiện 1 truy vấn SELECT DISTINCT category FROM MenuItem
        // và thêm vào `categories` (nhưng đảm bảo các tên chính xác/trường hợp chênh lệch).

        // Khởi tạo map: category -> doanh thu theo tháng (index 1..12)
        Map<String, double[]> rev = new HashMap<>();
        for (String cat : categories) rev.put(cat, new double[13]); // 1..12 dùng index 1..12

        // Truy vấn tổng doanh thu nhóm theo category và tháng
        String sql = """
        SELECT M.category, MONTH(B.dateCreated) AS monthNum, COALESCE(SUM(BD.totalPrice),0) AS totalRevenue
        FROM BillDetail BD
        JOIN Bill B ON B.billId = BD.billId
        JOIN MenuItem M ON M.itemId = BD.menuId
        WHERE YEAR(B.dateCreated) = ?
        GROUP BY M.category, MONTH(B.dateCreated)
    """;

        try (Connection conn = XJdbc.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, year);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String category = rs.getString("category");
                int month = rs.getInt("monthNum");
                double total = rs.getDouble("totalRevenue");

                // Nếu category không nằm trong danh sách mặc định, thêm vào (nhưng vẫn giữ thứ tự categories trước)
                if (!rev.containsKey(category)) {
                    rev.put(category, new double[13]);
                    categories.add(category); // thêm ra cuối
                }

                rev.get(category)[month] = total;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Bây giờ add vào dataset theo thứ tự months (bảo đảm cột theo đúng 1..12)
        for (String cat : categories) {
            double[] arr = rev.get(cat);
            for (int m = 1; m <= 12; m++) {
                dataset.addValue(arr[m], cat, "Tháng " + m);
            }
        }

        // Tạo series "Tổng doanh thu" (theo thứ tự tháng)
        for (int m = 1; m <= 12; m++) {
            double monthTotal = 0;
            for (String cat : categories) {
                monthTotal += rev.get(cat)[m];
            }
            dataset.addValue(monthTotal, "Tổng doanh thu", "Tháng " + m);
        }

        return dataset;
    }

    //    public DefaultPieDataset getRevenueByCategory(int month, int year) {
//        DefaultPieDataset dataset = new DefaultPieDataset();
//        // Nếu không có dữ liệu trong DB, dataset trống → biểu đồ trống
//        return dataset;
//    }
//
    public DefaultCategoryDataset getRevenueByMonthAndYear(int month, int year) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // Query DB theo tháng & năm, nếu không có dữ liệu thì để trống dataset
        return dataset;
    }

    /*public DefaultCategoryDataset getRevenueByMonth(int month, int year) {
    }*/

    public static void exportRevenueByYearToExcel(int year, String savePath) {
        String sql = """
        SELECT MONTH(dateCreated) AS month, SUM(total) AS total
        FROM Bill
        WHERE YEAR(dateCreated) = ?
        GROUP BY MONTH(dateCreated)
        ORDER BY MONTH(dateCreated)
    """;

        try (Connection conn = XJdbc.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, year);
            ResultSet rs = stmt.executeQuery();

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Doanh thu năm " + year);

            // ===== Header =====
            Row header = sheet.createRow(0);
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            String[] headers = {"Tháng", "Tổng doanh thu (VNĐ)"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // ===== Ghi dữ liệu =====
            int rowIndex = 1;
            double tongNam = 0;
            while (rs.next()) {
                int monthValue = rs.getInt("month");
                double total = rs.getDouble("total");

                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue("Tháng " + monthValue);
                row.createCell(1).setCellValue(total);

                tongNam += total;
            }

            // ===== Tổng cộng =====
            Row totalRow = sheet.createRow(rowIndex + 1);
            totalRow.createCell(0).setCellValue("TỔNG CỘNG");
            totalRow.createCell(1).setCellValue(tongNam);

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);

            // ===== Xuất file Excel tại đường dẫn người dùng chọn =====
            try (FileOutputStream fos = new FileOutputStream(savePath)) {
                workbook.write(fos);
            }
            workbook.close();

            System.out.println("✅ Xuất file Excel thành công tại: " + savePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
