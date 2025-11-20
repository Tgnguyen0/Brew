package app.GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.*;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;

import app.DAO.ThongkeDAO;
import app.InitFont.CustomFont;

public class StatisticPage extends JPanel {

    private ThongkeDAO thongkeDAO = new ThongkeDAO();
    private final CustomFont customFont = new CustomFont();

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

        // ========== CENTER ==========

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.white);

        // Bộ lọc tháng năm
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filterPanel.setBackground(Color.white);

        JLabel lblMonth = new JLabel("Tháng:");
        lblMonth.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 14));

        JLabel lblYear = new JLabel("Năm:");
        lblYear.setFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 14));

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

        // Nút xuất Excel (Preview trước)
        JButton btnExportExcel = new JButton("Xuất Excel");
        btnExportExcel.setFocusPainted(false);
        btnExportExcel.setBackground(new Color(76, 175, 80));
        btnExportExcel.setForeground(Color.white);
        btnExportExcel.setFont(customFont.getRobotoFonts().get(1).deriveFont(Font.BOLD, 14));
        btnExportExcel.setPreferredSize(new Dimension(140, 35));
        btnExportExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));

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

        // → Chỉ mở preview, không xuất ngay
        btnExportExcel.addActionListener(e -> {
            int year = Integer.parseInt(comboYear.getSelectedItem().toString());
            showExcelPreview(year);
        });

        filterPanel.add(btnExportExcel);
        centerPanel.add(filterPanel, BorderLayout.NORTH);

        // Biểu đồ
        JPanel chartContainer = new JPanel(new GridLayout(1, 2, 10, 0));
        chartContainer.setBackground(Color.white);
        chartContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        piePanel = createPieChartPanel(1, 2025);
        linePanel = createLineChartPanel(2025);

        chartContainer.add(piePanel);
        chartContainer.add(linePanel);

        centerPanel.add(chartContainer, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Sự kiện thay đổi filter
        ItemListener updateListener = e -> {
            int month = comboMonth.getSelectedIndex() + 1;
            int year = Integer.parseInt(comboYear.getSelectedItem().toString());
            updateCharts(month, year);
        };
        comboMonth.addItemListener(updateListener);
        comboYear.addItemListener(updateListener);
    }

    // ================= SUMMARY CARD =================
    private JPanel createSummaryCard(String title, String value, Color bgColor) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setForeground(Color.white);
        lblTitle.setFont(customFont.getRobotoFonts().get(1).deriveFont(Font.BOLD, 15));

        JLabel lblValue = new JLabel(value);
        lblValue.setForeground(Color.white);
        lblValue.setFont(customFont.getRobotoFonts().get(1).deriveFont(Font.BOLD, 20));

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);

        return card;
    }

    // ================= STYLE COMBOBOX =================
    private void styleComboBox(JComboBox<String> combo) {
        combo.setFont(customFont.getRobotoFonts().get(0).deriveFont(14f));
        combo.setBackground(Color.white);
        combo.setPreferredSize(new Dimension(110, 28));

        combo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                combo.setBackground(new Color(230, 240, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                combo.setBackground(Color.white);
            }
        });
    }

    // ================= PIE CHART =================
    private ChartPanel createPieChartPanel(int month, int year) {

        DefaultPieDataset dataset = thongkeDAO.getRevenueByCategory(month, year);

        if (dataset == null || dataset.getItemCount() == 0) {
            DefaultPieDataset empty = new DefaultPieDataset();
            empty.setValue("Không có dữ liệu", 100);
            dataset = empty;
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Tỷ lệ doanh thu theo loại món (" + month + "/" + year + ")",
                dataset, true, true, false);

        chart.setBackgroundPaint(Color.white);
        chart.getTitle().setFont(customFont.getRobotoFonts().get(1).deriveFont(Font.BOLD, 15));

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setShadowPaint(null);
        plot.setOutlineVisible(false);
        plot.setCircular(true);

        plot.setLabelFont(customFont.getRobotoFonts().get(0).deriveFont(13f));
        plot.setLabelBackgroundPaint(new Color(255, 255, 255, 180));

        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0}: {2}",
                new DecimalFormat("0"),
                new DecimalFormat("0.00%")
        ));

        plot.setSimpleLabels(false);

        for (Object keyObj : dataset.getKeys()) {
            String key = keyObj.toString();
            switch (key) {
                case "Cà phê" -> plot.setSectionPaint(key, new Color(121, 85, 72));
                case "Trà" -> plot.setSectionPaint(key, new Color(76, 175, 80));
                case "Bánh ngọt" -> plot.setSectionPaint(key, new Color(255, 152, 0));
                default -> plot.setSectionPaint(key, new Color(33, 150, 243));
            }
        }

        return new ChartPanel(chart);
    }

    // ================= LINE CHART =================
    private ChartPanel createLineChartPanel(int year) {

        DefaultCategoryDataset dataset = thongkeDAO.getRevenueByMonthAndCategory(year);

        JFreeChart chart = ChartFactory.createLineChart(
                "Doanh thu theo loại sản phẩm trong năm " + year,
                "Tháng",
                "Doanh thu (VNĐ)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        chart.setBackgroundPaint(Color.white);
        chart.getTitle().setFont(customFont.getRobotoFonts().get(1).deriveFont(Font.BOLD, 15));

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(Color.lightGray);

        LineAndShapeRenderer renderer = new LineAndShapeRenderer(true, true);
        renderer.setDefaultShapesVisible(true);
        renderer.setDefaultShapesFilled(true);

        renderer.setDefaultToolTipGenerator((ds, row, col) -> {
            Number v = ds.getValue(row, col);
            return ds.getRowKey(row) + " - " + ds.getColumnKey(col) +
                    ": " + String.format("%,.0f VNĐ", v.doubleValue());
        });

        Map<String, Paint> colors = new HashMap<>();
        colors.put("Bánh ngọt", new Color(255, 99, 132));
        colors.put("Cà phê", new Color(54, 162, 235));
        colors.put("Trà", new Color(75, 192, 192));
        colors.put("Khác", new Color(255, 206, 86));
        colors.put("Tổng doanh thu", new Color(33, 33, 33));

        for (int i = 0; i < dataset.getRowCount(); i++) {
            String key = dataset.getRowKey(i).toString();
            if (colors.containsKey(key)) {
                renderer.setSeriesPaint(i, colors.get(key));
            }
            renderer.setSeriesStroke(i, new BasicStroke(2.0f));
        }

        plot.setRenderer(renderer);

        CategoryAxis xAxis = plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        xAxis.setTickLabelFont(customFont.getRobotoFonts().get(0).deriveFont(12f));

        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setTickLabelFont(customFont.getRobotoFonts().get(0).deriveFont(12f));
        yAxis.setNumberFormatOverride(NumberFormat.getInstance());

        return new ChartPanel(chart);
    }

    private void showExcelPreview(int year) {

        DefaultCategoryDataset dataset = thongkeDAO.getRevenueByMonthAndCategory(year);

        // ====== TẠO DỮ LIỆU TABLE THEO CHUẨN EXCEL ======
        String[] columns = {
                "Loại sản phẩm",
                "T1","T2","T3","Q1",
                "T4","T5","T6","Q2",
                "T7","T8","T9","Q3",
                "T10","T11","T12","Q4",
                "Tổng năm"
        };

        Object[][] data = new Object[dataset.getRowCount()][18];

        for (int r = 0; r < dataset.getRowCount(); r++) {
            String category = dataset.getRowKey(r).toString();
            data[r][0] = category;

            double q1 = 0, q2 = 0, q3 = 0, q4 = 0, total = 0;

            int[] monthCol = {
                    1,2,3,   // T1 T2 T3
                    5,6,7,   // T4 T5 T6
                    9,10,11, // T7 T8 T9
                    13,14,15 // T10 T11 T12
            };

            for (int m = 0; m < 12; m++) {
                Number val = dataset.getValue(r, m);
                double num = val != null ? val.doubleValue() : 0;

                data[r][monthCol[m]] = String.format("%,.0f", num);

                if (m < 3) q1 += num;
                else if (m < 6) q2 += num;
                else if (m < 9) q3 += num;
                else q4 += num;

                total += num;
            }

            data[r][4]  = String.format("%,.0f", q1);  // Q1
            data[r][8]  = String.format("%,.0f", q2);  // Q2
            data[r][12] = String.format("%,.0f", q3);  // Q3
            data[r][16] = String.format("%,.0f", q4);  // Q4

            data[r][17] = String.format("%,.0f", total); // Tổng năm
        }

        JTable table = new JTable(data, columns);
        table.setFont(customFont.getRobotoFonts().get(0).deriveFont(14f));
        table.setRowHeight(32);
        table.setShowGrid(true);
        table.setGridColor(new Color(230,230,230));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // HEADER
        JTableHeader th = table.getTableHeader();
        th.setPreferredSize(new Dimension(0, 40));
        th.setBackground(new Color(55, 71, 79));
        th.setForeground(Color.white);
        th.setFont(customFont.getRobotoFonts().get(1).deriveFont(Font.BOLD, 15));

        // ========= AUTO FIT WIDTH (KHÔNG BAO GIỜ BỊ "...") =========
        for (int col = 0; col < table.getColumnCount(); col++) {
            TableColumn column = table.getColumnModel().getColumn(col);

            int maxWidth = 90; // width tối thiểu
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, col);
                Component comp = table.prepareRenderer(renderer, row, col);

                int width = comp.getPreferredSize().width + 30; // padding cho đẹp
                if (width > maxWidth) {
                    maxWidth = width;
                }
            }

            column.setPreferredWidth(maxWidth);
        }
        // =============================================================

        // ZEBRA RENDERER
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object val,
                                                           boolean isSel, boolean hasFocus,
                                                           int row, int col) {

                Component c = super.getTableCellRendererComponent(tbl, val, isSel, hasFocus, row, col);

                if (isSel) c.setBackground(new Color(200, 230, 255));
                else c.setBackground(row % 2 == 0 ? new Color(250, 250, 250) : Color.white);

                setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.white);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ==================== PANEL TRUNG TÂM ====================
        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(Color.white);
        center.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel info = new JLabel(
                "<html><div style='font-size:14px;color:#444;'>Ngày in: <b>"
                        + java.time.LocalDate.now() + "</b></div></html>"
        );
        info.setBorder(BorderFactory.createEmptyBorder(0, 5, 12, 0));

        center.add(info, BorderLayout.NORTH);
        center.add(scrollPane, BorderLayout.CENTER);

        // ==================== DIALOG ====================
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Báo Cáo Doanh Thu", true);
        dialog.setSize(1400, 600);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(Color.white);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(38, 50, 56));
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0,0,2,0,new Color(90, 90, 90)),
                BorderFactory.createEmptyBorder(18, 25, 18, 25)
        ));

        JLabel lblTitle = new JLabel("BÁO CÁO DOANH THU NĂM " + year);
        lblTitle.setForeground(Color.white);
        lblTitle.setFont(customFont.getRobotoFonts().get(1).deriveFont(Font.BOLD, 22));

        header.add(lblTitle, BorderLayout.WEST);
        dialog.add(header, BorderLayout.NORTH);

        dialog.add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        bottom.setBackground(Color.white);
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20));

        JButton btnSave = new JButton("Lưu file Excel");
        btnSave.setPreferredSize(new Dimension(150, 38));
        btnSave.setBackground(new Color(0, 137, 123));
        btnSave.setForeground(Color.white);

        JButton btnClose = new JButton("Đóng");
        btnClose.setPreferredSize(new Dimension(120, 38));
        btnClose.setBackground(new Color(198, 40, 40));
        btnClose.setForeground(Color.white);

        bottom.add(btnSave);
        bottom.add(btnClose);
        dialog.add(bottom, BorderLayout.SOUTH);

        btnSave.addActionListener(ev -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
            fc.setSelectedFile(new File("BaoCaoDoanhThu_" + year + ".xlsx"));

            int result = fc.showSaveDialog(dialog);
            if (result == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                String path = f.getAbsolutePath();
                if (!path.endsWith(".xlsx")) path += ".xlsx";

                ThongkeDAO.exportRevenueByYearToExcel(year, path);
                JOptionPane.showMessageDialog(dialog, "Đã lưu file:\n" + path);
            }
        });

        btnClose.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }




    // ================= UPDATE CHARTS =================
    private void updateCharts(int month, int year) {

        DefaultPieDataset pieData = thongkeDAO.getRevenueByCategory(month, year);
        DefaultCategoryDataset lineData = thongkeDAO.getRevenueByMonthAndCategory(year);

        piePanel.getChart().getTitle().setText(
                "Tỷ lệ doanh thu theo loại món (" + month + "/" + year + ")"
        );
        PiePlot plot = (PiePlot) piePanel.getChart().getPlot();
        plot.setDataset(pieData);

        linePanel.getChart().getTitle().setText(
                "Doanh thu theo loại sản phẩm trong năm " + year
        );

        CategoryPlot linePlot = linePanel.getChart().getCategoryPlot();
        linePlot.setDataset(lineData);
    }

    // ================= REFRESH PAGE =================
    public void reloadData() {
        JPanel summary = (JPanel) getComponent(0);
        summary.removeAll();

        summary.add(createSummaryCard("Doanh thu hôm nay",
                thongkeDAO.getTodayRevenue(),
                new Color(121, 85, 72)));

        summary.add(createSummaryCard("Số đơn hàng",
                thongkeDAO.getTodayOrderCount(),
                new Color(33, 150, 243)));

        summary.add(createSummaryCard("Khách hàng mới",
                thongkeDAO.getNewCustomersToday(),
                new Color(76, 175, 80)));

        summary.revalidate();
        summary.repaint();

        int month = comboMonth.getSelectedIndex() + 1;
        int year = Integer.parseInt(comboYear.getSelectedItem().toString());
        updateCharts(month, year);
    }

//    public static void main(String[] args) {
//        StatisticPage statisticPage = new StatisticPage();
//        statisticPage.setVisible(true);
//    }
}
