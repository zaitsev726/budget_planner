package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import controller.GuiController;
import entities.Expense;
import entities.Income;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class MainFrame extends JFrame {
    private JPanel totalPanel;
    private PieChart pieChart1;
    private JButton button2;
    private JTextField textField1;
    private JButton moreButton;
    private JPanel categoryListPanel;
    private JButton editButton;
    private JScrollPane scrollPane1;
    private JPanel morePanel;
    private JPanel incomeListPanel;
    private JPanel incomeTemplatePanel;
    private JLabel incomeLabel;
    private JButton button3;
    private JLabel monthLabel;
    private JButton button1;
    private JPanel expenseListPanel;
    private JPanel panel1;
    private JLabel categoryLabel;
    private JScrollPane categoryExpenseScrollPane;
    private JPanel categoryExpenseList;
    private JPanel addExpensePanel;
    private JButton addNewExpenseButton;
    private JTextField addExpenseField;
    private JButton button4;
    private JButton бButton;
    private static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    private final SimpleDateFormat formattedDate = new SimpleDateFormat("dd-MMM-yyyy");
    private final transient GuiController controller;
    private List<Color> colorList;

    public MainFrame(GuiController guiController) {
        controller = guiController;
        $$$setupUI$$$();
        setTitle("Budget planner");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );

        setContentPane(totalPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        fillCategoryListPanel();
        fillIncomeListPanel();
        monthLabel.setText(controller.getCurrentMonth());
        pack();
        setVisible(true);
        button2.addActionListener(e -> {
            if (textField1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Введите название новой категории");
                return;
            }
            controller.addNewCategory(textField1.getText());
            generateColorsValuesAndPieChart();
            fillCategoryListPanel();
        });
        button3.addActionListener(e -> {
            controller.setNextMonth();
            monthLabel.setText(controller.getCurrentMonth());
            morePanel.setVisible(false);
            fillCategoryListPanel();
            fillIncomeListPanel();
            fillExpenseListPanel();
            validate();
            repaint();
        });
        button1.addActionListener(e -> {
            controller.setPreviousMonth();
            monthLabel.setText(controller.getCurrentMonth());
            morePanel.setVisible(false);
            fillCategoryListPanel();
            fillIncomeListPanel();
            fillExpenseListPanel();
            validate();
            repaint();
        });
    }

    private void fillCategoryListPanel() {
        int size = controller.getCategoryList().size();
        int i = 0;
        categoryListPanel.removeAll();
        categoryListPanel.setLayout(new GridLayoutManager(size, 1, new Insets(0, 0, 0, 0), -1, -1));
        for (String category : controller.getCategoryList()) {
            final JPanel panel3 = new JPanel();
            panel3.setLayout(new GridLayoutManager(1, 3, new Insets(0, 5, 0, 0), -1, -1));
            panel3.setBackground(Color.white);
            categoryListPanel.add(panel3, new GridConstraints(i, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
            final JPanel panel4 = new JPanel();
            panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
            panel4.setBackground(colorList.get(i++));
            panel3.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(20, 20), new Dimension(20, 20), 0, false));
            final JLabel label1 = new JLabel();
            label1.setText(category);
            panel3.add(label1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            moreButton = new JButton(new ImageIcon(getClass().getResource("/more.png")));
            moreButton.setBackground(new Color(-1));
            moreButton.setBorderPainted(false);
            moreButton.setFocusPainted(false);
            moreButton.setContentAreaFilled(false);
            panel3.add(moreButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(40, 20), 0, false));
            moreButton.addActionListener(e -> activateMorePanel(category));
        }
        validate();
        repaint();
    }

    private void activateMorePanel(String categoryName) {
        morePanel.setVisible(true);
        categoryLabel.setText(categoryName);
        List<Expense> expenseList = controller.getExpenseListByCategoryName(categoryName);
        categoryExpenseList.removeAll();
        if (expenseList.isEmpty()) return;
        categoryExpenseList.setLayout(new GridLayoutManager(expenseList.size() + 1, 1, new Insets(0, 0, 0, 0), -1, -1));
        int i = 0;
        for (Expense expense : expenseList) {
            JPanel expenseTemplatePanel = new JPanel();
            expenseTemplatePanel.setBackground(new Color(-657931));
            categoryExpenseList.add(expenseTemplatePanel, new GridConstraints(i++, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
            expenseTemplatePanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
            final JLabel label1 = new JLabel();
            Font label1Font = this.$$$getFont$$$(null, Font.BOLD, -1, label1.getFont());
            if (label1Font != null) label1.setFont(label1Font);
            label1.setText(String.valueOf(expense.getSum()));
            expenseTemplatePanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            final JLabel label2 = new JLabel();
            Font label2Font = this.$$$getFont$$$(null, -1, -1, label2.getFont());
            if (label2Font != null) label2.setFont(label2Font);
            label2.setForeground(new Color(-7631989));
            label2.setHorizontalAlignment(4);
            label2.setHorizontalTextPosition(4);
            label2.setText(formattedDate.format(expense.getDate()));
            expenseTemplatePanel.add(label2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            editButton = new JButton(new ImageIcon(getClass().getResource("/edit.png")));
            editButton.setBorderPainted(false);
            editButton.setContentAreaFilled(false);
            editButton.setFocusPainted(true);
            editButton.setHorizontalAlignment(4);
            editButton.setHorizontalTextPosition(4);
            editButton.setIconTextGap(0);
            editButton.setSelected(false);
            editButton.setText("");
            expenseTemplatePanel.add(editButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(20, 20), new Dimension(40, 20), 0, false));
            editButton.addActionListener(e -> {
                expenseTemplatePanel.removeAll();
                expenseTemplatePanel.setLayout(new GridLayoutManager(1, 3, new Insets(2, 2, 2, 2), -1, -1));
                JTextField textField = new JTextField();
                textField.setText(String.valueOf(expense.getSum()));
                expenseTemplatePanel.add(textField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
                UtilDateModel model = new UtilDateModel();
                Properties properties = new Properties();
                properties.put("text.today", "Today");
                properties.put("text.month", "Month");
                properties.put("text.year", "Year");
                JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
                JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
                expenseTemplatePanel.add(datePicker, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
                JButton saveButton = new JButton("OK");
                expenseTemplatePanel.add(saveButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), null, 0, false));
                validate();
                repaint();
                saveButton.addActionListener(r -> {
                    try {
                        Double.parseDouble(textField.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Некорректно введена сумма расхода");
                        return;
                    }
                    if (datePicker.getModel().getValue() == null) {
                        JOptionPane.showMessageDialog(null, "Выберите дату");
                        return;
                    }
                    controller.setNewCategoryExpense(expense, Double.parseDouble(textField.getText()), (Date) datePicker.getModel().getValue());
                    activateMorePanel(categoryName);
                });
            });
        }
        final Spacer spacer2 = new Spacer();
        categoryExpenseList.add(spacer2, new GridConstraints(i, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        addNewExpenseButton.setBackground(new Color(-1));
        addNewExpenseButton.setText("+");
        addNewExpenseButton.addActionListener(e -> {
            if (addExpenseField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Введите сумму");
                return;
            }
            double sum;
            try {
                sum = Double.parseDouble(addExpenseField.getText());
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, "Введите сумму корректно. Например, 1850.20");
                return;
            }
            controller.addNewExpenseByCategory(categoryLabel.getText(), sum);
            addExpenseField.setText("");
            activateMorePanel(categoryName);
        });
        validate();
        repaint();
    }

    private void fillIncomeListPanel() {
        List<Income> incomeList = controller.getIncomeList();
        float totalIncome = controller.getTotalIncome();
        incomeLabel.setText("Доходы (" + totalIncome + ")");
        incomeListPanel.removeAll();
        if (incomeList.isEmpty()) return;
        incomeListPanel.setLayout(new GridLayoutManager(incomeList.size() + 1, 2, new Insets(0, 0, 0, 0), -1, -1));
        int i = 0;
        for (Income income : incomeList) {
            incomeTemplatePanel = new JPanel();
            incomeTemplatePanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
            incomeTemplatePanel.setBackground(new Color(-657931));
            incomeListPanel.add(incomeTemplatePanel, new GridConstraints(i++, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
            final JLabel label1 = new JLabel();
            Font label1Font = this.$$$getFont$$$(null, Font.BOLD, -1, label1.getFont());
            if (label1Font != null) label1.setFont(label1Font);
            label1.setForeground(new Color(-16734075));
            label1.setText("+" + income.getSum());
            incomeTemplatePanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            final JButton editIncomeButton = new JButton(new ImageIcon(getClass().getResource("/edit.png")));
            editIncomeButton.setBorderPainted(false);
            editIncomeButton.setContentAreaFilled(false);
            editIncomeButton.setFocusPainted(true);
            editIncomeButton.setHorizontalAlignment(4);
            editIncomeButton.setHorizontalTextPosition(4);
            editIncomeButton.setIconTextGap(0);
            editIncomeButton.setSelected(false);
            editIncomeButton.setText("");
            editIncomeButton.addActionListener(e -> {
                incomeTemplatePanel.removeAll();
                incomeTemplatePanel.setLayout(new GridLayoutManager(1, 3, new Insets(2, 2, 2, 2), -1, -1));
                JTextField textField = new JTextField();
                textField.setText(String.valueOf(income.getSum()));
                incomeTemplatePanel.add(textField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
                UtilDateModel model = new UtilDateModel();
                Properties properties = new Properties();
                properties.put("text.today", "Today");
                properties.put("text.month", "Month");
                properties.put("text.year", "Year");
                JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
                JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
                incomeTemplatePanel.add(datePicker, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
                JButton saveButton = new JButton("OK");
                incomeTemplatePanel.add(saveButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), null, 0, false));
                validate();
                repaint();
                saveButton.addActionListener(r -> {
                    try {
                        Double.parseDouble(textField.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Некорректно введена сумма расхода");
                        return;
                    }
                    if (datePicker.getModel().getValue() == null) {
                        JOptionPane.showMessageDialog(null, "Выберите дату");
                        return;
                    }
                    controller.setNewIncome(income, Double.parseDouble(textField.getText()), (Date) datePicker.getModel().getValue());
                    fillIncomeListPanel();
                });
            });
            incomeTemplatePanel.add(editIncomeButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(20, 20), new Dimension(40, 20), 0, false));
            final JLabel label2 = new JLabel();
            Font label2Font = this.$$$getFont$$$(null, -1, -1, label2.getFont());
            if (label2Font != null) label2.setFont(label2Font);
            label2.setForeground(new Color(-7631989));
            label2.setHorizontalAlignment(4);
            label2.setHorizontalTextPosition(4);
            label2.setText(formattedDate.format(income.getDate()));
            incomeTemplatePanel.add(label2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        }
        final Spacer spacer2 = new Spacer();
        incomeListPanel.add(spacer2, new GridConstraints(i, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        validate();
        repaint();
    }

    private void fillExpenseListPanel() {

    }

    //TODO: нужно в pieChart1 разобраться со случаем, когда в категории еще нет никаких расходов
    private void createUIComponents() {
        generateColorsValuesAndPieChart();
    }

    private void generateColorsValuesAndPieChart() {
        int size = controller.getCategoryList().size();
        List<Double> values = controller.getCategoryValuesList();
        colorList = controller.getColorList(size);
        if (values.isEmpty())
            pieChart1 = new PieChart(new ArrayList<>(Collections.singleton(100.0)), new ArrayList<>(Collections.singleton(new Color(140, 140, 140))));
        else
            pieChart1 = new PieChart(values, colorList);
        pieChart1.setPreferredSize(new Dimension(150, 150));
    }

    private static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private static final String DATE_PATTERN = "yyyy-MM-dd";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_PATTERN);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        totalPanel = new JPanel();
        totalPanel.setLayout(new GridLayoutManager(6, 6, new Insets(0, 0, 0, 0), -1, -1));
        totalPanel.setBackground(new Color(-1));
        totalPanel.setPreferredSize(new Dimension(738, 500));
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-1));
        totalPanel.add(panel1, new GridConstraints(2, 3, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel1.add(pieChart1, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-1));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        scrollPane1 = new JScrollPane();
        scrollPane1.setAutoscrolls(false);
        scrollPane1.setHorizontalScrollBarPolicy(31);
        scrollPane1.setOpaque(true);
        panel2.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        categoryListPanel = new JPanel();
        categoryListPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        categoryListPanel.setBackground(new Color(-1));
        scrollPane1.setViewportView(categoryListPanel);
        final Spacer spacer1 = new Spacer();
        categoryListPanel.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-5131855));
        panel1.add(panel3, new GridConstraints(0, 2, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(1, -1), null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setBackground(new Color(-1));
        panel1.add(panel4, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 25), null, 0, false));
        button2 = new JButton();
        button2.setBackground(new Color(-1));
        button2.setText("+");
        panel4.add(button2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 25), new Dimension(20, 25), new Dimension(-1, 25), 0, false));
        textField1 = new JTextField();
        panel4.add(textField1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 25), new Dimension(150, 25), null, 0, false));
        morePanel = new JPanel();
        morePanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        morePanel.setBackground(new Color(-1));
        morePanel.setVisible(false);
        panel1.add(morePanel, new GridConstraints(0, 3, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(300, 120), new Dimension(300, 150), null, 0, false));
        categoryLabel = new JLabel();
        categoryLabel.setHorizontalAlignment(0);
        categoryLabel.setHorizontalTextPosition(0);
        categoryLabel.setText("Категория");
        categoryLabel.setVisible(true);
        morePanel.add(categoryLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 30), null, 0, false));
        categoryExpenseScrollPane = new JScrollPane();
        categoryExpenseScrollPane.setBackground(new Color(-1));
        categoryExpenseScrollPane.setFocusable(false);
        categoryExpenseScrollPane.setHorizontalScrollBarPolicy(31);
        categoryExpenseScrollPane.setVisible(true);
        morePanel.add(categoryExpenseScrollPane, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        categoryExpenseList = new JPanel();
        categoryExpenseList.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        categoryExpenseList.setBackground(new Color(-1));
        categoryExpenseList.setMinimumSize(new Dimension(295, 100));
        categoryExpenseList.setPreferredSize(new Dimension(296, 100));
        categoryExpenseList.setVisible(true);
        categoryExpenseScrollPane.setViewportView(categoryExpenseList);
        final Spacer spacer2 = new Spacer();
        categoryExpenseList.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        categoryExpenseList.add(panel5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Сумма");
        panel5.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        button4 = new JButton();
        button4.setText("Button");
        panel5.add(button4, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Дата");
        panel5.add(label2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addExpensePanel = new JPanel();
        addExpensePanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        addExpensePanel.setBackground(new Color(-1));
        morePanel.add(addExpensePanel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 25), null, 0, false));
        addNewExpenseButton = new JButton();
        addNewExpenseButton.setBackground(new Color(-1));
        addNewExpenseButton.setText("+");
        addNewExpenseButton.setVisible(true);
        addExpensePanel.add(addNewExpenseButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(15, 20), null, 0, false));
        addExpenseField = new JTextField();
        addExpenseField.setVisible(true);
        addExpensePanel.add(addExpenseField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 25), null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel6.setBackground(new Color(-1));
        totalPanel.add(panel6, new GridConstraints(4, 3, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel7.setBackground(new Color(-1));
        panel6.add(panel7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        incomeLabel = new JLabel();
        incomeLabel.setText("Доходы (5000,00)");
        panel7.add(incomeLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel7.add(scrollPane2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        incomeListPanel = new JPanel();
        incomeListPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        incomeListPanel.setBackground(new Color(-1));
        scrollPane2.setViewportView(incomeListPanel);
        final Spacer spacer3 = new Spacer();
        incomeListPanel.add(spacer3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel8.setBackground(new Color(-1));
        panel7.add(panel8, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 25), null, 0, false));
        final JButton button5 = new JButton();
        button5.setBackground(new Color(-1));
        button5.setText("+");
        panel8.add(button5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(15, 15), null, 0, false));
        final JTextField textField2 = new JTextField();
        panel8.add(textField2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 25), null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel9.setBackground(new Color(-1));
        panel6.add(panel9, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Расходы (1800,00)");
        panel9.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane3 = new JScrollPane();
        panel9.add(scrollPane3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        expenseListPanel = new JPanel();
        expenseListPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        expenseListPanel.setBackground(new Color(-1));
        scrollPane3.setViewportView(expenseListPanel);
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel10.setBackground(new Color(-657931));
        expenseListPanel.add(panel10, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 30), null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$(null, Font.BOLD, -1, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setForeground(new Color(-7405514));
        label4.setText("-1800,00");
        panel10.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$(null, -1, -1, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setForeground(new Color(-7631989));
        label5.setHorizontalAlignment(4);
        label5.setHorizontalTextPosition(4);
        label5.setText("22.05.2020");
        panel10.add(label5, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        expenseListPanel.add(spacer4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel11.setBackground(new Color(-1));
        panel6.add(panel11, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Итого: 3200,00");
        panel11.add(label6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel12.setBackground(new Color(-5131855));
        panel6.add(panel12, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 1), null, 0, false));
        final JPanel panel13 = new JPanel();
        panel13.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel13.setBackground(new Color(-5131855));
        totalPanel.add(panel13, new GridConstraints(3, 3, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 1), null, 0, false));
        final JPanel panel14 = new JPanel();
        panel14.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel14.setBackground(new Color(-5131855));
        totalPanel.add(panel14, new GridConstraints(2, 2, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(1, -1), null, 0, false));
        final JLabel label7 = new JLabel();
        Font label7Font = this.$$$getFont$$$(null, -1, -1, label7.getFont());
        if (label7Font != null) label7.setFont(label7Font);
        label7.setHorizontalAlignment(0);
        label7.setHorizontalTextPosition(0);
        label7.setText("<html>Р<br>А<br>С<br>Х<br>О<br>Д<br>Ы");
        totalPanel.add(label7, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setAlignmentX(0.5f);
        label8.setHorizontalAlignment(0);
        label8.setHorizontalTextPosition(0);
        label8.setText("<html>И<br>С<br>Т<br>О<br>Р<br>И<br>Я");
        totalPanel.add(label8, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        totalPanel.add(spacer5, new GridConstraints(2, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, null, new Dimension(10, -1), null, 0, false));
        final Spacer spacer6 = new Spacer();
        totalPanel.add(spacer6, new GridConstraints(0, 1, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        final Spacer spacer7 = new Spacer();
        totalPanel.add(spacer7, new GridConstraints(5, 1, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        final Spacer spacer8 = new Spacer();
        totalPanel.add(spacer8, new GridConstraints(3, 5, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, null, new Dimension(10, -1), null, 0, false));
        final JPanel panel15 = new JPanel();
        panel15.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel15.setBackground(new Color(-1));
        totalPanel.add(panel15, new GridConstraints(1, 3, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 30), null, 0, false));
        monthLabel = new JLabel();
        monthLabel.setText("Label");
        panel15.add(monthLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        button3 = new JButton();
        button3.setContentAreaFilled(false);
        button3.setText(">");
        panel15.add(button3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        button1 = new JButton();
        button1.setContentAreaFilled(false);
        button1.setFocusPainted(false);
        button1.setText("<");
        panel15.add(button1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return totalPanel;
    }

}
