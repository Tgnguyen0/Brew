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

    // =================== BIỂU ĐỒ TRÒN: DOANH THU THEO LOẠI MÓN ===================
    public DefaultPieDataset getRevenueByCategory() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        String sql = """
            SELECT m.category, SUM(bd.soLuong * bd.amount) AS total
            FROM BillDetail bd
            JOIN Bill b ON bd.billId = b.billId
            JOIN MenuItem m ON bd.menuId = m.itemId
            GROUP BY m.category
        """;
        try (Connection conn = XJdbc.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                dataset.setValue(rs.getString("category"), rs.getDouble("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataset;
    }

    // =================== BIỂU ĐỒ CỘT: DOANH THU THEO THÁNG ===================
    public DefaultCategoryDataset getRevenueByMonth(int month, int year) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = """
        SELECT MONTH(dateCreated) AS month, SUM(total) AS total
        FROM Bill
        WHERE YEAR(dateCreated) = ?
        GROUP BY MONTH(dateCreated)
        ORDER BY MONTH(dateCreated)
    """;

        try (Connection conn = XJdbc.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, year); // <-- lấy đúng theo năm chọn

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int monthValue = rs.getInt("month");
                    double total = rs.getDouble("total");
                    dataset.addValue(total, "Doanh thu", "Tháng " + monthValue);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataset;
    }


    public DefaultPieDataset getRevenueByCategory(int month, int year) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        // Nếu không có dữ liệu trong DB, dataset trống → biểu đồ trống
        return dataset;
    }

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
