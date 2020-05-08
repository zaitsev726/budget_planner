package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel totalPanel;
    private JPanel categoryListPanel;
    private JPanel turnoverListPanel;
    private PieChart pieChart1;
    public static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

    public MainFrame(Controller controller) {
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
        // TODO: place custom component creation code here
        pieChart1 = new PieChart(30);
    }
}
