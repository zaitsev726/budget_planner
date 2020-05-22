package controller;

import com.sun.istack.NotNull;
import entities.Category;
import entities.Expense;
import entities.Income;
import repository.CategoryRepository;
import repository.ExpenseRepository;
import repository.IncomeRepository;

import java.awt.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.*;

public class GuiController {
    private final Random rand = new Random();
    private Month currentMonth;
    private int currentYear;
    private List<Color> colorList;

    private CategoryRepository categoryRepository;
    private ExpenseRepository expenseRepository;
    private IncomeRepository incomeRepository;


    public GuiController() {
        this.categoryRepository = new CategoryRepository();
        this.expenseRepository = new ExpenseRepository();
        this.incomeRepository = new IncomeRepository();

        LocalDate currentDate = LocalDate.now();
        currentMonth = currentDate.getMonth();
        currentYear = currentDate.getYear();
        generateColorList();
    }

    /**
     * Создает список рандомных неповторяющихся цветов
     *
     * @param size размер списка
     * @return список Color'ов
     */
    public List<Color> getColorList(int size) {
        if (size > 10000) {
            List<Color> bigColorList = new ArrayList<>(colorList);
            bigColorList.addAll(generateMoreColors(size - 10000));
            colorList = bigColorList;
            return colorList;
        }
        return colorList.subList(0, size);
    }

    private void generateColorList() {
        int size = 10000;
        colorList = new ArrayList<>();
        float r;
        float g;
        float b;
        for (int i = 0; i < size; ++i) {
            do {
                r = (float) (rand.nextFloat() / 2f + 0.5);
                g = (float) (rand.nextFloat() / 2f + 0.5);
                b = (float) (rand.nextFloat() / 2f + 0.5);
            } while (colorList.contains(new Color(r, g, b)));
            colorList.add(new Color(r, g, b));
        }
    }

    private List<Color> generateMoreColors(int size) {
        List<Color> moreColors = new ArrayList<>();
        float r;
        float g;
        float b;
        for (int i = 0; i < size; ++i) {
            do {
                r = (float) (rand.nextFloat() / 2f + 0.5);
                g = (float) (rand.nextFloat() / 2f + 0.5);
                b = (float) (rand.nextFloat() / 2f + 0.5);
            } while (colorList.contains(new Color(r, g, b)) || moreColors.contains(new Color(r, g, b)));
            moreColors.add(new Color(r, g, b));
        }
        return moreColors;
    }

    /**
     * @return список, состоящий из названий категорий
     */
    @NotNull
    public List<String> getCategoryList() {
        List<String> categoryNames = new ArrayList<>();
        for (Category category : categoryRepository.findAll()) {
            categoryNames.add(category.getCategoryName());
        }
        return categoryNames;
    }

    /**
     * Для всех категорий считаем, какой процент они вкладывают в расход по всем категориям
     *
     * @return список процентов, который соответствует списку категорий, возвращаемых getCategoryList()
     */
    @NotNull
    public List<Double> getCategoryValuesList() {
        List<Double> categoryValue = new ArrayList<>();
        double totalSum = 0.0;
        List<Category> allCategories = categoryRepository.findAll();
        for (Category category : allCategories) {
            totalSum += category.getCurrentSum();
        }
        if (totalSum == 0) return categoryValue;
        for (Category category : allCategories) {
            categoryValue.add(category.getCurrentSum() / totalSum * 100.0);
        }
        return categoryValue;
    }

    /**
     * Выводит список расходов по категории в упорядоченном порядке (desc) для текущего месяца
     *
     * @param categoryName название категории
     * @return лист расходов для категории с названием categoryName
     */
    public List<Expense> getExpenseListByCategoryName(String categoryName) {
        Date prev = getStartDate();
        Date future = getFinishDate();
        Category category = categoryRepository.findByNameCategory(categoryName);
        return expenseRepository.findExpensesByMonthAndCategory(prev, future, category.getIdCategory());
    }

    /**
     * Iзменение значения конкретного расхода по категории в текущем месяце
     *
     * @param expense изменяемый расход
     * @param sum     новое значение суммы
     * @param date    новая дата расхода
     */
    public void setNewCategoryExpense(Expense expense, double sum, Date date) {
        Category category = categoryRepository.findByIdCategory(expense.getIdCategory());
        category.setCurrentSum(category.getCurrentSum() - expense.getSum() + sum);
        categoryRepository.updateCategory(category);
        expense.setSum(sum);
        expense.setDate(date);
        expenseRepository.updateExpense(expense);
    }

    /**
     * Iзменение значения конкретного дохода в текущем месяце
     *
     * @param income изменяемый доход
     * @param sum    новое значение суммы
     * @param date   новая дата дохода
     */
    public void setNewIncome(Income income, double sum, Date date) {
        income.setSum(sum);
        income.setDate(date);
        incomeRepository.updateIncome(income);
    }

    /**
     * Добавление новой категории во все месяца (?)
     *
     * @param categoryName название категории
     */
    public void addNewCategory(String categoryName) {
        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setCurrentSum(0.0);
        categoryRepository.saveCategory(category);
        //categoryList.add(categoryName);
    }

    /**
     * @return список доходов за текущий месяц
     */
    public List<Income> getIncomeList() {
        Date prev = getStartDate();
        Date future = getFinishDate();
        return incomeRepository.findByMonth(prev, future);
    }

    /**
     * @return сумму всех доходов за текущий месяц
     */
    public float getTotalIncome() {
        double totalSum = 0.0;
        for (Income income : getIncomeList()) {
            totalSum += income.getSum();
        }
        return (float) totalSum;
    }

    /**
     * @return текущий месяц
     */
    public String getCurrentMonth() {
        return currentMonth.toString() + " " + currentYear;
    }

    /**
     * Устанавливает текущий месяц меньше на 1
     */
    public void setPreviousMonth() {
        currentMonth = currentMonth.minus(1L);
        if (currentMonth.equals(Month.DECEMBER))
            currentYear -= 1;
    }

    /**
     * Устанавливает текущий месяц больше на 1
     */
    public void setNextMonth() {
        currentMonth = currentMonth.plus(1L);
        if (currentMonth.equals(Month.JANUARY))
            currentYear += 1;
    }

    /**
     * Добавляется новый расход по категории (дата - текущий день)
     *
     * @param categoryName название категории
     * @param sum          сумма расхода
     */
    public void addNewExpenseByCategory(String categoryName, double sum) {
        Expense expense = new Expense();
        Category category = categoryRepository.findByNameCategory(categoryName);
        expense.setIdCategory(category.getIdCategory());
        expense.setSum(sum);
        expense.setDate(new Date());
        expenseRepository.saveExpense(expense);
        category.setCurrentSum(category.getCurrentSum() + sum);
        categoryRepository.updateCategory(category);
    }

    /**
     * Добавляется новый доход (дата - текущий день)
     *
     * @param sum сумма дохода
     */
    public void addNewIncome(float sum) {
        Income income = new Income();
        income.setSum(sum);
        income.setDate(new Date());
        incomeRepository.saveIncome(income);
    }

    /**
     * @return список расходов по ВСЕМ категориям за текущий месяц
     */
    @NotNull
    public List<Expense> getExpenseList() {
        Date prev = getStartDate();
        Date future = getFinishDate();
        return expenseRepository.findExpensesByMonth(prev, future);
    }

    private Date getStartDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, currentYear);
        c.set(Calendar.MONTH, currentMonth.getValue() - 2); //-1 because of different types, -1 because previous month
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        return c.getTime();
    }

    private Date getFinishDate() {
        Calendar c;
        c = Calendar.getInstance();
        c.set(Calendar.MONTH, currentMonth.getValue()); //+2-2 = 0
        c.set(Calendar.YEAR, currentYear + ((currentMonth.getValue() == 12) ? 1 : 0)); //if month is december then year = year+1
        c.set(Calendar.DATE, c.getActualMinimum(Calendar.DATE));
        return c.getTime();
    }

    /**
     * @return возвращает общую сумму расходов за текущий месяц
     */
    public double getTotalExpense() {
        double totalSum = 0.0;
        for (Expense expense : getExpenseList()) {
            totalSum += expense.getSum();
        }
        return totalSum;
    }
}
