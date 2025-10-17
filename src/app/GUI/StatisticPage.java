package app.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import app.Components.CustomBarRenderer;
import app.InitFont.CustomFont;

public class StatisticPage extends JPanel {
    CustomFont customFont = new CustomFont();

    public StatisticPage() {
        setPreferredSize(new Dimension(1100, 600));
        setOpaque(true);
        setBackground(Color.white);
        setLayout(new GridBagLayout());

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(100, "Sales", "Coffee");
        dataset.addValue(150, "Sales", "Ice cream");
        dataset.addValue(200, "Sales", "Soda");
        dataset.addValue(120, "Sales", "Milkshake");
        dataset.addValue(50, "Sales", "Omelet");
        dataset.addValue(10, "Sales", "Blue Crystal");

        // Create the bar chart
        JFreeChart barChart = ChartFactory.createBarChart(
                "Monthly Sales of Product", // Chart title
                "Product", // X-axis label
                "Sales", // Y-axis label
                dataset, // Dataset
                PlotOrientation.VERTICAL,
                true, // Include legend
                true, // Tooltips
                false // URLs
        );
        barChart.setBackgroundPaint(null);

        // Customize chart fonts and colors
        TextTitle chartTitle = new TextTitle("Monthly Sales of Product",
                customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 14));
        chartTitle.setPaint(Color.black); // Set chart title color
        barChart.setTitle(chartTitle);

        CategoryPlot plot = (CategoryPlot) barChart.getPlot();
        plot.setBackgroundPaint(null); // Make the plot background transparent
        plot.setOutlinePaint(null); // Make the plot outline transparent

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLabelFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        domainAxis.setTickLabelFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 10));
        domainAxis.setLabelPaint(Color.black); // Set domain axis label color
        domainAxis.setTickLabelPaint(Color.black); // Set domain axis tick label color

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLabelFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 12));
        rangeAxis.setTickLabelFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 10));
        rangeAxis.setLabelPaint(Color.black); // Set range axis label color
        rangeAxis.setTickLabelPaint(Color.black); // Set range axis tick label color

        BarRenderer renderer = new CustomBarRenderer();
        plot.setRenderer(renderer);

        if (barChart.getLegend() != null) {
            barChart.getLegend().setItemFont(customFont.getRobotoFonts().get(0).deriveFont(Font.PLAIN, 10));
            barChart.getLegend().setItemPaint(Color.black); // Set legend item color
            barChart.getLegend().setBackgroundPaint(new Color(255, 213, 146, 0)); // Set legend background color with
            // some opacity
        }

        // Create the panel containing the chart
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(800, 570));
        chartPanel.setOpaque(false);

        // Use GridBagLayout to center the chart panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        add(chartPanel, gbc);
    }

    // @Override
    // protected void paintComponent(Graphics g) {
    // String imagePath = "dev_cafe/asset/background.png"; // Path to your image
    // file
    // File imageFile = new File(imagePath);

    // try {
    // Image image = ImageIO.read(imageFile);
    // int newWidth = getWidth();
    // int newHeight = getHeight();
    // Image scaledImage = image.getScaledInstance(newWidth, newHeight,
    // Image.SCALE_SMOOTH);
    // g.drawImage(scaledImage, 0, 0, null);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }
}
