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
    private JButton addIncomeButton;
    private JTextField addIncomeField;
    private JPanel addIncomePanel;
    private JLabel expenseLabel;
    private JPanel totalExpenseTemplatePanel;
    private JLabel expenseSumLabel;
    private JLabel expenseDateLabel;
    private JPanel trap;
    private static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    private static final String FORMAT_PATTERN = "dd-MMM-yyyy";
    private final transient GuiController controller;
    private List<Color> colorList;

    public MainFrame(GuiController guiController) {
        controller = guiController;
        createUIComponents();
        setTitle("Budget planner");
        setLocation(
                (SCREEN_WIDTH - totalPanel.getPreferredSize().width) / 2,
                (SCREEN_HEIGHT - totalPanel.getPreferredSize().height) / 2
        );
        setContentPane(totalPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        monthLabel.setText(controller.getCurrentMonth());
        pack();
        setVisible(true);
        fillCategoryListPanel();
        fillIncomeListPanel();
        fillExpenseListPanel();
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
            JButton moreButton = new JButton(new ImageIcon(getClass().getResource("/more.png")));
            moreButton.setBackground(new Color(-1));
            moreButton.setBorderPainted(false);
            moreButton.setFocusPainted(false);
            moreButton.setContentAreaFilled(false);
            panel3.add(moreButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(40, 20), 0, false));
            if (moreButton.getActionListeners().length > 0)
                moreButton.removeActionListener(moreButton.getActionListeners()[0]);
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
            i = constructExpensePanel(categoryName, i, expense);
        }
        final Spacer spacer2 = new Spacer();
        categoryExpenseList.add(spacer2, new GridConstraints(i, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        addingNewExpenseProperty(categoryName);
        validate();
        repaint();
    }

    private void addingNewExpenseProperty(String categoryName) {
        addNewExpenseButton.setBackground(new Color(-1));
        addNewExpenseButton.setText("+");
        if (addNewExpenseButton.getActionListeners().length > 0)
            addNewExpenseButton.removeActionListener(addNewExpenseButton.getActionListeners()[0]);
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
            fillExpenseListPanel();
        });
    }

    private int constructExpensePanel(String categoryName, int i, Expense expense) {
        JPanel expenseTemplatePanel = new JPanel();
        expenseTemplatePanel.setBackground(new Color(-657931));
        categoryExpenseList.add(expenseTemplatePanel, new GridConstraints(i++, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        expenseTemplatePanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        Font label1Font = new Font(label1.getFont().getName(), Font.BOLD, label1.getFont().getSize());
        label1.setFont(label1Font);
        label1.setText(String.valueOf(expense.getSum()));
        expenseTemplatePanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setForeground(new Color(-7631989));
        label2.setHorizontalAlignment(4);
        label2.setHorizontalTextPosition(4);
        SimpleDateFormat formattedDate = new SimpleDateFormat(FORMAT_PATTERN);
        label2.setText(formattedDate.format(expense.getDate()));
        expenseTemplatePanel.add(label2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        JButton editButton = new JButton(new ImageIcon(getClass().getResource("/edit.png")));
        editButton.setBorderPainted(false);
        editButton.setContentAreaFilled(false);
        editButton.setFocusPainted(true);
        editButton.setHorizontalAlignment(4);
        editButton.setHorizontalTextPosition(4);
        editButton.setIconTextGap(0);
        editButton.setSelected(false);
        editButton.setText("");
        expenseTemplatePanel.add(editButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(20, 20), new Dimension(40, 20), 0, false));
        if (editButton.getActionListeners().length > 0)
            editButton.removeActionListener(editButton.getActionListeners()[0]);
        editButton.addActionListener(e -> repaintExpenseTemplateForEditting(categoryName, expense, expenseTemplatePanel));
        return i;
    }

    private void repaintExpenseTemplateForEditting(String categoryName, Expense expense, JPanel expenseTemplatePanel) {
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
        initializeSaveButton(categoryName, expense, textField, datePicker, saveButton);
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
            i = constructIncomeTemplatePanel(i, income);
        }
        final Spacer spacer2 = new Spacer();
        incomeListPanel.add(spacer2, new GridConstraints(i, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        addingNewIncomeProperty();
        validate();
        repaint();
    }

    private void addingNewIncomeProperty() {
        if (addIncomeButton.getActionListeners().length > 0)
            addIncomeButton.removeActionListener(addIncomeButton.getActionListeners()[0]);
        addIncomeButton.addActionListener(e -> {
            if (addIncomeField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Введите данные");
                return;
            }
            float sum;
            try {
                sum = Float.parseFloat(addIncomeField.getText());
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, "Введите данные корректно. Например, 1777.77");
                return;
            }
            controller.addNewIncome(sum);
            addIncomeField.setText("");
            fillIncomeListPanel();
        });
    }

    private int constructIncomeTemplatePanel(int i, Income income) {
        incomeTemplatePanel = new JPanel();
        incomeTemplatePanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        incomeTemplatePanel.setBackground(new Color(-657931));
        incomeListPanel.add(incomeTemplatePanel, new GridConstraints(i++, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = new Font(label1.getFont().getName(), Font.BOLD, label1.getFont().getSize());
        label1.setFont(label1Font);
        label1.setForeground(new Color(-16734075));
        label1.setText("+" + income.getSum());
        incomeTemplatePanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JButton editIncomeButton = new JButton(new ImageIcon(getClass().getResource("/edit.png")));
        editIncomeButton.setBorderPainted(false);
        editIncomeButton.setContentAreaFilled(false);
        editIncomeButton.setFocusPainted(false);
        editIncomeButton.setHorizontalAlignment(4);
        editIncomeButton.setHorizontalTextPosition(4);
        editIncomeButton.setIconTextGap(0);
        editIncomeButton.setSelected(false);
        editIncomeButton.setText("");
        if (editIncomeButton.getActionListeners().length > 0)
            editIncomeButton.removeActionListener(editIncomeButton.getActionListeners()[0]);
        editIncomeButton.addActionListener(e -> repaintIncomeTemplateForEditing(income));
        incomeTemplatePanel.add(editIncomeButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(20, 20), new Dimension(40, 20), 0, false));
        final JLabel label2 = new JLabel();
        label2.setForeground(new Color(-7631989));
        label2.setHorizontalAlignment(4);
        label2.setHorizontalTextPosition(4);
        SimpleDateFormat formattedDate = new SimpleDateFormat(FORMAT_PATTERN);
        label2.setText(formattedDate.format(income.getDate()));
        incomeTemplatePanel.add(label2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return i;
    }

    private void initializeSaveButton(String categoryName, Object operation, JTextField textField, JDatePickerImpl datePicker, JButton saveButton) {
        if (saveButton.getActionListeners().length > 0)
            saveButton.removeActionListener(saveButton.getActionListeners()[0]);
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
            if (operation instanceof Expense) {
                Expense expense = (Expense) operation;
                controller.setNewCategoryExpense(expense, Double.parseDouble(textField.getText()), (Date) datePicker.getModel().getValue());
                activateMorePanel(categoryName);
                fillExpenseListPanel();
            } else if (operation instanceof Income) {
                Income income = (Income) operation;
                controller.setNewIncome(income, Double.parseDouble(textField.getText()), (Date) datePicker.getModel().getValue());
                fillIncomeListPanel();
            }
        });
    }

    private void repaintIncomeTemplateForEditing(Income income) {
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
        initializeSaveButton("", income, textField, datePicker, saveButton);
        validate();
        repaint();
    }

    private void fillExpenseListPanel() {
        List<Expense> expenseList = controller.getExpenseList();
        double totalExpense = controller.getTotalExpense();
        expenseLabel.setText("Расходы (" + totalExpense + ")");
        expenseListPanel.removeAll();
        if (expenseList.isEmpty()) return;
        expenseListPanel.setLayout(new GridLayoutManager(expenseList.size() + 1, 1, new Insets(0, 0, 0, 0), -1, -1));
        expenseListPanel.setBackground(new Color(-1));
        int i = 0;
        for (Expense expense : expenseList) {
            i = constructTotalExpensePanel(i, expense);
        }
        final Spacer spacer4 = new Spacer();
        expenseListPanel.add(spacer4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        validate();
        repaint();
    }

    private int constructTotalExpensePanel(int i, Expense expense) {
        JPanel totalExpenseTemplatePanel = new JPanel();
        totalExpenseTemplatePanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        totalExpenseTemplatePanel.setBackground(new Color(-657931));
        expenseListPanel.add(totalExpenseTemplatePanel, new GridConstraints(i++, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 30), null, 0, false));
        JLabel expenseSumLabel = new JLabel();
        Font expenseSumLabelFont = new Font(expenseSumLabel.getFont().getName(), Font.BOLD, expenseSumLabel.getFont().getSize());
        expenseSumLabel.setFont(expenseSumLabelFont);
        expenseSumLabel.setForeground(new Color(-7405514));
        expenseSumLabel.setText("-" + expense.getSum());
        totalExpenseTemplatePanel.add(expenseSumLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        JLabel expenseDateLabel = new JLabel();
        expenseDateLabel.setForeground(new Color(-7631989));
        expenseDateLabel.setHorizontalAlignment(4);
        expenseDateLabel.setHorizontalTextPosition(4);
        SimpleDateFormat formattedDate = new SimpleDateFormat(FORMAT_PATTERN);
        expenseDateLabel.setText(formattedDate.format(expense.getDate()));
        totalExpenseTemplatePanel.add(expenseDateLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        return i;
    }

    private void createUIComponents() {
        if (controller != null && trap != null)
            generateColorsValuesAndPieChart();
    }

    //TODO: нужно в pieChart1 разобраться со случаем, когда в категории еще нет никаких расходов
    private void generateColorsValuesAndPieChart() {
        int size = controller.getCategoryList().size();
        List<Double> values = controller.getCategoryValuesList();
        colorList = controller.getColorList(size);
        PieChart pieChart;
        if (values.isEmpty())
            pieChart = new PieChart(new ArrayList<>(Collections.singleton(100.0)), new ArrayList<>(Collections.singleton(new Color(140, 140, 140))));
        else
            pieChart = new PieChart(values, colorList);
        pieChart.setPreferredSize(new Dimension(150, 150));

        trap.removeAll();
        trap.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        trap.add(pieChart, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        trap.revalidate();
        trap.repaint();
        validate();
        repaint();
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

}
