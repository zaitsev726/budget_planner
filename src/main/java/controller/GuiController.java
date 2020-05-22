package controller;

import com.sun.istack.NotNull;
import entities.Category;
import entities.Expense;
import entities.Income;
import repository.CategoryRepository;
import repository.ExpenseRepository;
import repository.IncomeRepository;
import service.BudgetPlannerService;

import java.awt.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.*;

public class GuiController {
    private BudgetPlannerService service;
    private CategoryController categoryController;
    private final Random rand = new Random();
    private Month currentMonth;
    private int currentYear;
    private List<Color> colorList;
    //test
    private List<Expense> testList;
    private List<Income> testIncomeList;
    private List<String> categoryList;

    //

    private CategoryRepository categoryRepository;
    private ExpenseRepository expenseRepository;
    private IncomeRepository incomeRepository;


    public GuiController(BudgetPlannerService budgetPlannerService, CategoryController categoryController) {
        this.categoryRepository = new CategoryRepository();
        this.expenseRepository = new ExpenseRepository();
        this.incomeRepository = new IncomeRepository();

        service = budgetPlannerService;
        this.categoryController = categoryController;
        LocalDate currentDate = LocalDate.now();
        currentMonth = currentDate.getMonth();
        currentYear = currentDate.getYear();
        generateColorList();
        //test
        categoryList = new ArrayList<>();
        categoryList.add("Продукты");//(new String("Продукты".getBytes(), UTF_8));
        categoryList.add("Транспорт");
        categoryList.add("Здоровье");
        categoryList.add("Рестораны");

        testIncomeList = new ArrayList<>();
        Income i1 = new Income();
        i1.setSum(200.0);
        i1.setDate(new GregorianCalendar(2020, Calendar.MARCH, 13).getTime());
        testIncomeList.add(i1);
        Income i2 = new Income();
        i2.setDate(new GregorianCalendar(2020, Calendar.MAY, 11).getTime());
        i2.setSum(12000.0);
        testIncomeList.add(i2);
        testList = new ArrayList<>();
        Expense e = new Expense();
        e.setSum(1800.0);
        e.setDate(new GregorianCalendar(2020, Calendar.FEBRUARY, 22).getTime());
        testList.add(e);
        Expense e2 = new Expense();
        e2.setSum(200.0);
        e2.setDate(new GregorianCalendar(2020, Calendar.MAY, 15).getTime());
        testList.add(e2);
        //
    }

    /**
     * Создает список рандомных неповторяющихся цветов
     *
     * @param size размер списка
     * @return список Color'ов
     */
    public List<Color> getColorList(int size) {
        return colorList.subList(0,size);
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

    /**
     * @return список, состоящий из названий категорий
     */
    public List<String> getCategoryList() {
        List<String> categoryNames = new ArrayList<>();
        for(Category category: categoryRepository.findAll()){
            categoryNames.add(category.getCategoryName());
        }
        return categoryNames;
        //return categoryList;
    }

    /**
     * Для всех категорий считаем, какой процент они вкладывают в расход по всем категориям
     *
     * @return список процентов, который соответствует списку категорий, возвращаемых getCategoryList()
     */
    public List<Double> getCategoryValuesList() {
        List<Double> categoryValue = new ArrayList<>();
        double totalSum = 0.0;
        List<Category> allCategories = categoryRepository.findAll();
        for(Category category : allCategories){
            totalSum += category.getCurrentSum();
        }
        for(Category category : allCategories){
            categoryValue.add(category.getCurrentSum() / totalSum);
        }
        return categoryValue;
        /*List<Double> testValues = new ArrayList<>();
        testValues.add(0.0);
        testValues.add(50.0);
        testValues.add(25.0);
        testValues.add(25.0);
        testValues.add(12.5);
        testValues.add(12.5);
        testValues.add(12.5);
        testValues.add(12.5);
        testValues.add(12.5);
        testValues.add(12.5);
        testValues.add(12.5);
        testValues.add(12.5);
        return testValues.subList(0, categoryList.size());*/
    }

    /**
     * Выводит список расходов по категории в упорядоченном порядке (desc) для текущего месяца
     *
     * @param categoryName название категории
     * @return лист расходов для категории с названием categoryName
     */
    public List<Expense> getExpenseListByCategoryName(String categoryName) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        Date prev = c.getTime();

        c = Calendar.getInstance();
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
        c.set(Calendar.DATE, c.getActualMinimum(Calendar.DATE));
        Date future = c.getTime();
        Category category = categoryRepository.findByNameCategory(categoryName);
        return expenseRepository.findExpensesByMonthAndCategory(prev,future,category.getIdCategory());
    }

    /**
     * Iзменение значения конкретного расхода по категории в текущем месяце
     *
     * @param expense изменяемый расход
     * @param sum     новое значение суммы
     * @param date    новая дата расхода
     */
    public void setNewCategoryExpense(Expense expense, double sum, Date date) {

        /* int index = testList.indexOf(expense);
        Expense updated = testList.get(index);
        updated.setDate(date);
        updated.setSum(sum);*/
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
        /*int index = testIncomeList.indexOf(income);
        Income updated = testIncomeList.get(index);
        updated.setSum(sum);
        updated.setDate(date);*/
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
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        Date prev = c.getTime();

        c = Calendar.getInstance();
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
        c.set(Calendar.DATE, c.getActualMinimum(Calendar.DATE));
        Date future = c.getTime();
        return incomeRepository.findByMonth(prev,future);
    }

    /**
     * @return сумму всех доходов за текущий месяц
     */
    public float getTotalIncome() {
        double totalSum = 0.0;
        for(Income income: getIncomeList()){
            totalSum+= income.getSum();
        }
        return (float) totalSum;
    }

    /**
     *
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
     * @param categoryName название категории
     * @param sum сумма расхода
     */
    public void addNewExpenseByCategory(String categoryName, double sum) {
       /* Expense expense = new Expense();
        expense.setSum(sum);
        expense.setDate(new Date());
        testList.add(expense);*/
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
     * @param sum сумма дохода
     */
    public void addNewIncome(float sum) {
        /*Income income = new Income();
        income.setSum(sum);
        income.setDate(new Date());
        testIncomeList.add(income);*/
        Income income = new Income();
        income.setSum(sum);
        income.setDate(new Date());
        incomeRepository.saveIncome(income);
    }

    /**
     *
     * @return список расходов по ВСЕМ категориям за текущий месяц
     */
    @NotNull
    public List<Expense> getExpenseList() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        Date prev = c.getTime();

        c = Calendar.getInstance();
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
        c.set(Calendar.DATE, c.getActualMinimum(Calendar.DATE));
        Date future = c.getTime();
        return expenseRepository.findExpensesByMonth(prev,future);
    }

    /**
     *
     * @return возвращает общую сумму расходов за текущий месяц
     */
    public double getTotalExpense() {
        double totalSum = 0.0;
        for (Expense expense : getExpenseList()){
            totalSum += expense.getSum();
        }
        return totalSum;
    }
}
