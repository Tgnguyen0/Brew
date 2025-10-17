package app.Components;

import java.awt.Color;
import java.awt.Paint;

import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;

public class CustomBarRenderer extends BarRenderer {

    public CustomBarRenderer() {
        setBarPainter(new StandardBarPainter());
    }

    @Override
    public Paint getItemPaint(int row, int column) {
        Number value = getPlot().getDataset().getValue(row, column);
        if (value != null) {
            double val = value.doubleValue();
            if (val > 50) {
                return Color.RED;
            } else if (val > 30) {
                return Color.ORANGE;
            } else {
                return Color.GREEN;
            }
        }
        return super.getItemPaint(row, column);
    }
}
