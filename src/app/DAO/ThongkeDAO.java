package app.DAO;

import app.Connection.XJdbc;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
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

        ThongkeDAO dao = new ThongkeDAO();
        DefaultCategoryDataset dataset = dao.getRevenueByMonthAndCategory(year);

        try (Workbook wb = new XSSFWorkbook()) {

            Sheet sheet = wb.createSheet("BaoCao_" + year);

            // ==== FONT ====
            Font fontNormal = wb.createFont();
            fontNormal.setFontHeightInPoints((short) 11);

            Font fontBold = wb.createFont();
            fontBold.setFontHeightInPoints((short) 12);
            fontBold.setBold(true);

            Font fontTitle = wb.createFont();
            fontTitle.setFontHeightInPoints((short) 16);
            fontTitle.setBold(true);

            // ==== STYLE CHUNG ====
            CellStyle styleTitle = wb.createCellStyle();
            styleTitle.setFont(fontTitle);
            styleTitle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle styleBoldLeft = wb.createCellStyle();
            styleBoldLeft.setFont(fontBold);
            styleBoldLeft.setAlignment(HorizontalAlignment.LEFT);

            CellStyle styleHeader = wb.createCellStyle();
            styleHeader.setFont(fontBold);
            styleHeader.setAlignment(HorizontalAlignment.CENTER);
            styleHeader.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
            styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            styleHeader.setBorderTop(BorderStyle.THIN);
            styleHeader.setBorderBottom(BorderStyle.THIN);
            styleHeader.setBorderLeft(BorderStyle.THIN);
            styleHeader.setBorderRight(BorderStyle.THIN);

            CellStyle styleNormal = wb.createCellStyle();
            styleNormal.setFont(fontNormal);
            styleNormal.setAlignment(HorizontalAlignment.RIGHT);
            styleNormal.setDataFormat(wb.createDataFormat().getFormat("#,##0"));
            styleNormal.setBorderTop(BorderStyle.THIN);
            styleNormal.setBorderBottom(BorderStyle.THIN);
            styleNormal.setBorderLeft(BorderStyle.THIN);
            styleNormal.setBorderRight(BorderStyle.THIN);

            CellStyle styleBoldRight = wb.createCellStyle();
            styleBoldRight.cloneStyleFrom(styleNormal);
            styleBoldRight.setFont(fontBold);

            // Dùng để tô vàng dòng "Tổng doanh thu" (từ dataset)
            CellStyle styleTotalRow = wb.createCellStyle();
            styleTotalRow.cloneStyleFrom(styleBoldRight);
            styleTotalRow.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
            styleTotalRow.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // ======================= HEADER BÁO CÁO =======================

            // Tiêu đề chính
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("BÁO CÁO DOANH THU NĂM " + year);
            titleCell.setCellStyle(styleTitle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 17));

            // Thông tin phụ
            Row r1 = sheet.createRow(2);
            r1.createCell(0).setCellValue("BREW COFFEE");
            r1.getCell(0).setCellStyle(styleBoldLeft);

            Row r2 = sheet.createRow(3);
            r2.createCell(0).setCellValue("Ngày tạo: " + java.time.LocalDate.now());
            r2.getCell(0).setCellStyle(styleBoldLeft);

            Row r3 = sheet.createRow(4);
            r3.createCell(0).setCellValue("Người lập báo cáo: Admin");
            r3.getCell(0).setCellStyle(styleBoldLeft);

            // ======================= HEADER BẢNG =======================

            String[] headers = {
                    "Loại sản phẩm",
                    "T1","T2","T3","Q1",
                    "T4","T5","T6","Q2",
                    "T7","T8","T9","Q3",
                    "T10","T11","T12","Q4",
                    "Tổng năm"
            };

            Row header = sheet.createRow(6);
            for (int i = 0; i < headers.length; i++) {
                Cell h = header.createCell(i);
                h.setCellValue(headers[i]);
                h.setCellStyle(styleHeader);
            }

            // ======================= DỮ LIỆU =======================

            int rowIndex = 7;

            for (int r = 0; r < dataset.getRowCount(); r++) {

                Row row = sheet.createRow(rowIndex++);
                String category = dataset.getRowKey(r).toString();

                boolean isTotalRow = "Tổng doanh thu".equalsIgnoreCase(category.trim());

                // cột "Loại sản phẩm"
                Cell catCell = row.createCell(0);
                catCell.setCellValue(category);
                catCell.setCellStyle(isTotalRow ? styleTotalRow : styleBoldLeft);

                double q1 = 0, q2 = 0, q3 = 0, q4 = 0, total = 0;

                // mapping đúng vị trí của từng tháng
                int[] monthCol = {
                        1,  // T1
                        2,  // T2
                        3,  // T3
                        5,  // T4
                        6,  // T5
                        7,  // T6
                        9,  // T7
                        10, // T8
                        11, // T9
                        13, // T10
                        14, // T11
                        15  // T12
                };

                for (int m = 0; m < 12; m++) {

                    double value = dataset.getValue(r, m) != null ?
                            dataset.getValue(r, m).doubleValue() : 0;

                    int colIndex = monthCol[m];  // ⭐ lấy đúng cột
                    Cell c = row.createCell(colIndex);
                    c.setCellValue(value);
                    c.setCellStyle(styleNormal);

                    // TÍNH QUÝ
                    if (m < 3) q1 += value;
                    else if (m < 6) q2 += value;
                    else if (m < 9) q3 += value;
                    else q4 += value;

                    total += value;
                }

                // ghi Q1–Q4 + Tổng năm
                Cell cQ1 = row.createCell(4);
                cQ1.setCellValue(q1);
                cQ1.setCellStyle(isTotalRow ? styleTotalRow : styleBoldRight);

                Cell cQ2 = row.createCell(8);
                cQ2.setCellValue(q2);
                cQ2.setCellStyle(isTotalRow ? styleTotalRow : styleBoldRight);

                Cell cQ3 = row.createCell(12);
                cQ3.setCellValue(q3);
                cQ3.setCellStyle(isTotalRow ? styleTotalRow : styleBoldRight);

                Cell cQ4 = row.createCell(16);
                cQ4.setCellValue(q4);
                cQ4.setCellStyle(isTotalRow ? styleTotalRow : styleBoldRight);

                Cell cYear = row.createCell(17);
                cYear.setCellValue(total);
                cYear.setCellStyle(isTotalRow ? styleTotalRow : styleBoldRight);
            }

            // ❌ KHÔNG thêm dòng "Tổng doanh thu" thứ 2 nữa
            // (vì dataset đã có 1 row "Tổng doanh thu" rồi)

            // ======================= FORMAT + FREEZE =======================
            sheet.createFreezePane(1, 7); // khóa header + cột loại sản phẩm

            for (int i = 0; i <= 17; i++) {
                sheet.autoSizeColumn(i);
                // tránh cột quá hẹp → bị ####
                if (sheet.getColumnWidth(i) < 3500) {
                    sheet.setColumnWidth(i, 3500);
                }
            }

            try (FileOutputStream fos = new FileOutputStream(savePath)) {
                wb.write(fos);
            }

            System.out.println("🎉 Xuất Excel đẹp & đúng dữ liệu thành công!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}