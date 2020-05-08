package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private JPanel totalPanel;
    private JPanel categoryListPanel;
    private JPanel turnoverListPanel;
    private JLabel label1;
    private JPanel pieChartPanel;
    private JButton button1;
    private JButton button2;
    private PieChart pieChart1;
    public static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    //private Controller controller;

    public MainFrame(/*Controller c*/) {
        //controller = c;
        setTitle("Delete statement");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void createUIComponents() {
        int size = 25; //get from controller
        ArrayList values = new ArrayList<>();
        ArrayList colorList = getColorList(size);
        for (int i = 0; i < size; ++i)
            values.add((double) 100 / (size - 1));
        pieChart1 = new PieChart(values, colorList);
        pieChart1.setPreferredSize(new Dimension(150, 150));

    }

    private ArrayList getColorList(int size) {
        ArrayList colorList = new ArrayList();
        int constant = 0;
        int k = 1;
        int step = 75;
        int delta = 0;
        int change = 0;
        for (int i = 0; i < size; ++i) {
            if (constant == 0) {
                if (k == 1 && change == 0 || k == -1 && change == 1) {
                    colorList.add(new Color(255, delta, 0));
                    change = 0;
                } else
                    colorList.add(new Color(255, 0, delta));
            } else if (constant == 1) {
                if (k == 1 && change == 0 || k == -1 && change == 1) {
                    colorList.add(new Color(delta, 255, 0));
                    change = 0;
                } else
                    colorList.add(new Color(0, 255, delta));
            } else if (constant == 2) {
                if (k == 1 && change == 0 || k == -1 && change == 1) {
                    colorList.add(new Color(delta, 0, 255));
                    change = 0;
                } else
                    colorList.add(new Color(0, delta, 255));
            }
            delta = delta + k * step;
            if (delta > 255 - step) {
                delta = 255;
                constant = (++constant) % 3;
                k = -k;
                change = 1;
            } else if (delta < step) {
                delta = 0;
                constant = (++constant) % 3;
                k = -k;
                change = 1;
            }
        }
        return colorList;
    }
}
