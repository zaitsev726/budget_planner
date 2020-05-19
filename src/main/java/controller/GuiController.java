package controller;

import com.sun.istack.NotNull;
import entities.Expense;
import entities.Income;
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
    public GuiController(BudgetPlannerService budgetPlannerService, CategoryController categoryController) {
        service = budgetPlannerService;
        this.categoryController = categoryController;
        LocalDate currentDate = LocalDate.now();
        currentMonth = currentDate.getMonth();
        currentYear = currentDate.getYear();
        generateColorList();
        //test
        categoryList = new ArrayList<>();
        categoryList.add("Продукты");
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
        return categoryList;
    }

    /**
     * Для всех категорий считаем, какой процент они вкладывают в расход по всем категориям
     *
     * @return список процентов, который соответствует списку категорий, возвращаемых getCategoryList()
     */
    public List<Double> getCategoryValuesList() {
        List<Double> testValues = new ArrayList<>();
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
        return testValues.subList(0, categoryList.size());
    }

    /**
     * Выводит список расходов по категории в упорядоченном порядке (desc) для текущего месяца
     *
     * @param categoryName название категории
     * @return лист расходов для категории с названием categoryName
     */
    public List<Expense> getExpenseListByCategoryName(String categoryName) {
        return testList;
    }

    /**
     * Изменение значения конкретного расхода по категории в текущем месяце
     *
     * @param expense изменяемый расход
     * @param sum     новое значение суммы
     * @param date    новая дата расхода
     */
    public void setNewCategoryExpense(Expense expense, double sum, Date date) {
        int index = testList.indexOf(expense);
        Expense updated = testList.get(index);
        updated.setDate(date);
        updated.setSum(sum);
    }

    /**
     * Изменение значения конкретного дохода в текущем месяце
     *
     * @param income изменяемый доход
     * @param sum    новое значение суммы
     * @param date   новая дата дохода
     */
    public void setNewIncome(Income income, double sum, Date date) {
        int index = testIncomeList.indexOf(income);
        Income updated = testIncomeList.get(index);
        updated.setSum(sum);
        updated.setDate(date);
    }

    /**
     * Добавление новой категории во все месяца (?)
     *
     * @param categoryName название категории
     */
    public void addNewCategory(String categoryName) {
        categoryList.add(categoryName);
    }

    /**
     * @return список доходов за текущий месяц
     */
    public List<Income> getIncomeList() {
        return testIncomeList;
    }

    /**
     * @return сумму всех доходов за текущий месяц
     */
    public float getTotalIncome() {
        return 5000.0f;
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
        Expense expense = new Expense();
        expense.setSum(sum);
        expense.setDate(new Date());
        testList.add(expense);
    }

    /**
     * Добавляется новый доход (дата - текущий день)
     * @param sum сумма дохода
     */
    public void addNewIncome(float sum) {
        Income income = new Income();
        income.setSum(sum);
        income.setDate(new Date());
        testIncomeList.add(income);
    }

    /**
     *
     * @return список расходов по ВСЕМ категориям за текущий месяц
     */
    @NotNull
    public List<Expense> getExpenseList() {
        return new ArrayList<>();
    }

    /**
     *
     * @return возвращает общую сумму расходов за текущий месяц
     */
    public double getTotalExpense() {
        return 1800.0;
    }
}
