package categories;

import java.util.Date;
import java.util.HashMap;

public class Category {
    private String categoryName = "";
    private int currentSum;
    private HashMap<String, Date> incomeHistory;
    private HashMap<String, Date> expenseHistory;

    public Category(String categoryName) {
        this.categoryName = categoryName;
        currentSum = 0;
        incomeHistory = new HashMap<>();
        expenseHistory = new HashMap<>();
    }

    public int getCurrentSum() {
        return currentSum;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public HashMap<String, Date> getExpenseHistory() {
        return expenseHistory;
    }

    public HashMap<String, Date> getIncomeHistory() {
        return incomeHistory;
    }

    public void addNewIncome(int income) {
        currentSum += income;
        Date date = new Date();
        String newIncome = "Доход: +" + income + " " + date;
        incomeHistory.put(newIncome, date);
    }

    public void addNewIncome(int income, Date date) {
        currentSum += income;
        String newIncome = "Доход: +" + income + " " + date;
        incomeHistory.put(newIncome, date);
    }

    public void addNewExpense(int expense) {
        currentSum -= expense;
        Date date = new Date();
        String newExpense = "Расход: -" + expense + " " + date;
        expenseHistory.put(newExpense, date);
    }

    public void addNewExpense(int expense, Date date) {
        currentSum -= expense;
        String newExpense = "Расход: -" + expense + " " + date;
        expenseHistory.put(newExpense, date);
    }
}
