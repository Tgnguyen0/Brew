package app.GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.*;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;

import app.DAO.ThongkeDAO;

public class StatisticPage extends JPanel {


    private ThongkeDAO thongkeDAO = new ThongkeDAO();

    private JComboBox<String> comboMonth;
    private JComboBox<String> comboYear;
    private ChartPanel piePanel;
    private ChartPanel linePanel;

    public StatisticPage() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.white);
        setPreferredSize(new Dimension(1100, 600));

        // ========== TOP SUMMARY ==========
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 15, 10));
        summaryPanel.setBackground(Color.white);
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        summaryPanel.add(createSummaryCard("Doanh thu hôm nay", thongkeDAO.getTodayRevenue(), new Color(121, 85, 72)));
        summaryPanel.add(createSummaryCard("Số đơn hàng", thongkeDAO.getTodayOrderCount(), new Color(33, 150, 243)));
        summaryPanel.add(createSummaryCard("Khách hàng mới", thongkeDAO.getNewCustomersToday(), new Color(76, 175, 80)));

        add(summaryPanel, BorderLayout.NORTH);

        // ========== CENTER (Filter + Charts) ==========
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.white);

        // Bộ lọc tháng & năm
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filterPanel.setBackground(Color.white);

        JLabel lblMonth = new JLabel("Tháng:");
        JLabel lblYear = new JLabel("Năm:");

        comboMonth = new JComboBox<>(new String[]{
                "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6",
                "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"
        });
        comboYear = new JComboBox<>(new String[]{"2023", "2024", "2025"});

        styleComboBox(comboMonth);
        styleComboBox(comboYear);

        filterPanel.add(lblMonth);
        filterPanel.add(comboMonth);
        filterPanel.add(lblYear);
        filterPanel.add(comboYear);

        JButton btnExportExcel = new JButton("Xuất Excel");
        btnExportExcel.setFocusPainted(false);
        btnExportExcel.setBackground(new Color(76, 175, 80));
        btnExportExcel.setForeground(Color.white);
        btnExportExcel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnExportExcel.setPreferredSize(new Dimension(140, 35));
        btnExportExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));

// Hover
        btnExportExcel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnExportExcel.setBackground(new Color(56, 142, 60));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnExportExcel.setBackground(new Color(76, 175, 80));
            }
        });

// === Hành động khi nhấn nút ===
        btnExportExcel.addActionListener(e -> {
            int year = Integer.parseInt(comboYear.getSelectedItem().toString());
            File defaultDir = new File(System.getProperty("user.dir") + "/excel");
            if (!defaultDir.exists()) defaultDir.mkdirs();

            // Hộp thoại chọn nơi lưu file
            JFileChooser fileChooser = new JFileChooser(defaultDir);
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
            fileChooser.setSelectedFile(new File("ThongKeDoanhThu_" + year + ".xlsx"));

            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();

                // Nếu người dùng không nhập đuôi .xlsx thì thêm vào
                if (!filePath.toLowerCase().endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }

                // Gọi DAO như cũ, chỉ cần sửa hàm DAO nhận thêm tham số path
                ThongkeDAO.exportRevenueByYearToExcel(year, filePath);

                JOptionPane.showMessageDialog(this,
                        "✅ Đã xuất thống kê doanh thu năm " + year + " ra file Excel!\n" + filePath,
                        "Xuất Excel", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        filterPanel.add(btnExportExcel);



        centerPanel.add(filterPanel, BorderLayout.NORTH);

        // Biểu đồ nằm chung 1 hàng
        JPanel chartContainer = new JPanel(new GridLayout(1, 2, 10, 0));
        chartContainer.setBackground(Color.white);
        chartContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        piePanel = createPieChartPanel(1, 2025);  // Mặc định Tháng 1, 2025
        linePanel = createLineChartPanel(1, 2025);

        chartContainer.add(piePanel);
        chartContainer.add(linePanel);

        centerPanel.add(chartContainer, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Xử lý sự kiện thay đổi tháng/năm
        ItemListener filterListener = e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                int month = comboMonth.getSelectedIndex() + 1;
                int year = Integer.parseInt(comboYear.getSelectedItem().toString());
                updateCharts(month, year);
            }
        };
        comboMonth.addItemListener(filterListener);
        comboYear.addItemListener(filterListener);
    }

    // ================= SUMMARY CARD =================
    private JPanel createSummaryCard(String title, String value, Color bgColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        card.setPreferredSize(new Dimension(300, 100));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setForeground(Color.white);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel lblValue = new JLabel(value);
        lblValue.setForeground(Color.white);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 18));

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        return card;
    }

    // ================= STYLE CHO COMBOBOX =================
    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboBox.setBackground(Color.white);
        comboBox.setPreferredSize(new Dimension(100, 25));

        comboBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                comboBox.setBackground(new Color(230, 240, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                comboBox.setBackground(Color.white);
            }
        });
    }

    // ================= TẠO BIỂU ĐỒ =================
    private ChartPanel createPieChartPanel(int month, int year) {
        DefaultPieDataset dataset = thongkeDAO.getRevenueByCategory(month, year);
        JFreeChart chart = ChartFactory.createPieChart(
                "Tỷ lệ doanh thu theo loại sản phẩm (" + month + "/" + year + ")",
                dataset, true, true, false);
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 14));
        chart.setBackgroundPaint(Color.white);

        ChartPanel panel = new ChartPanel(chart);
        panel.setBackground(Color.white);
        return panel;
    }

    private ChartPanel createLineChartPanel(int month, int year) {
        DefaultCategoryDataset dataset = thongkeDAO.getRevenueByMonthAndYear(month, year);
        JFreeChart chart = ChartFactory.createLineChart(
                "Doanh thu theo thời gian (" + month + "/" + year + ")",
                "Ngày", "VNĐ", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        chart.setBackgroundPaint(Color.white);
        chart.setTitle(new TextTitle(chart.getTitle().getText(), new Font("Segoe UI", Font.BOLD, 14)));

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(Color.gray);

        // 👇 Thêm đoạn này để xoay nhãn trục X chống tràn
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 10)
        );
        domainAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 8));

        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        plot.setRenderer(renderer);

        ChartPanel panel = new ChartPanel(chart);
        panel.setBackground(Color.white);
        return panel;
    }

    // ================= CẬP NHẬT BIỂU ĐỒ =================
    private void updateCharts(int month, int year) {
        DefaultPieDataset pieData = thongkeDAO.getRevenueByCategory(month, year);
        DefaultCategoryDataset lineData = thongkeDAO.getRevenueByMonth(month, year);

        if (pieData.getItemCount() == 0 && lineData.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Không có dữ liệu cho " + month + "/" + year,
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }

        piePanel.setChart(ChartFactory.createPieChart(
                "Tỷ lệ doanh thu theo loại sản phẩm (" + month + "/" + year + ")",
                pieData, true, true, false));

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Doanh thu theo thời gian (" + year + ")",
                "Tháng", "VNĐ", lineData,
                PlotOrientation.VERTICAL, true, true, false);

        // 👇 Thêm đoạn này để xoay nhãn trục X chống tràn
        CategoryPlot plot = lineChart.getCategoryPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 4)
        );
        domainAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 11));

        linePanel.setChart(lineChart);
    }

}
