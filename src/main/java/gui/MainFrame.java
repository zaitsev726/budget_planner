package gui;

import controller.Controller;

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
    private Controller controller;

    public MainFrame(Controller c) {
        controller = c;
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
        int r = 255, g = 0, b = 0;
        ArrayList colorList = new ArrayList<>();
        ArrayList values = new ArrayList<>();
        new Color(255, 0, 0);
        for (int i = 0; i < 10; ++i) {
            values.add(10.0);
            colorList.add(new Color(r, g, b));


        }
        pieChart1 = new PieChart(values, colorList);
        pieChart1.setPreferredSize(new Dimension(150, 150));

    }
}
