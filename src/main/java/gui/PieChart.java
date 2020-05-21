package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PieChart extends JPanel {
    private final List<Double> values;
    private final List<Color> colors;

    public PieChart(List<Double> values, List<Color> colors) {
        this.values = values;
        this.colors = colors;
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getSize().width;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
                RenderingHints.VALUE_ANTIALIAS_ON);
        int lastPoint = -270;
        for (int i = 0; i < values.size(); i++) {
            g2d.setColor(colors.get(i));
            Double val = values.get(i);
            double angle = (val / 100) * 360;
            g2d.fillArc(0, 0, width, width, lastPoint, -(int) angle);
            lastPoint = lastPoint + -(int) angle;
        }
    }
}