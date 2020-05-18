package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PieChart extends JPanel {

    enum Type {
        STANDARD, SIMPLE_INDICATOR, GRADED_INDICATOR
    }

    private Type type = null; //the type of pie chart

    private List<Double> values;
    private List<Color> colors;


    double percent = 0; //percent is used for simple indicator and graded indicator

    public PieChart(int percent) {

        type = Type.SIMPLE_INDICATOR;
        this.percent = percent;
    }

    public PieChart(List<Double> values, List<Color> colors) {

        type = Type.STANDARD;

        this.values = values;
        this.colors = colors;
    }

    @Override
    protected void paintComponent(Graphics g) {

        int width = getSize().width;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
                RenderingHints.VALUE_ANTIALIAS_ON);

        if (type == Type.SIMPLE_INDICATOR) {

            //colours used for simple indicator
            Color backgroundColor = Color.WHITE;
            Color mainColor = Color.BLUE;

            g2d.setColor(backgroundColor);
            g2d.fillOval(0, 0, width, width);
            g2d.setColor(mainColor);
            Double angle = (percent / 100) * 360;
            g2d.fillArc(0, 0, width, width, -270, -angle.intValue());

        } else if (type == Type.STANDARD) {

            int lastPoint = -270;

            for (int i = 0; i < values.size(); i++) {
                g2d.setColor((Color) colors.get(i));

                Double val = (Double) values.get(i);
                Double angle = (val / 100) * 360;

                g2d.fillArc(0, 0, width, width, lastPoint, -angle.intValue());
                System.out.println("fill arc " + lastPoint + " "
                        + -angle.intValue());

                lastPoint = lastPoint + -angle.intValue();
            }
        }
    }
}