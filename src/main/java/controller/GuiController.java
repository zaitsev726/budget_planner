package controller;

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
    //test
    private List<Expense> testList;
    List<String> categoryList;

    //
    public GuiController(BudgetPlannerService budgetPlannerService, CategoryController categoryController) {
        service = budgetPlannerService;
        this.categoryController = categoryController;
        LocalDate currentDate = LocalDate.now();
        currentMonth = currentDate.getMonth();
        currentYear = currentDate.getYear();
        //test
        categoryList = new ArrayList<>();
        categoryList.add("Продукты");
        categoryList.add("Транспорт");
        categoryList.add("Здоровье");
        categoryList.add("Рестораны");

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
        List<Color> colorList = new ArrayList<>();
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
        return colorList;
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
        /*List<Category> categoryList = new ArrayList<>(/*categoryController.getCategories().values());
        List<Double> categorySizeList = new ArrayList<>();
        categoryList.forEach(e -> categorySizeList.add(0.0 /*e.getExpenseHistory().size()));
        double total = categorySizeList.stream().reduce(0.0, Double::sum);
        List<Double> values = new ArrayList<>();
        categorySizeList.forEach(e -> values.add(e / total * 100));*/
        List<Double> testValues = new ArrayList<>();
        testValues.add(0.0);
        testValues.add(50.0);
        testValues.add(25.0);
        testValues.add(25.0);
        return testValues;
    }

    /**
     * Выводит список расходов по категории в упорядоченном порядке (desc)
     *
     * @param categoryName название категории
     * @return лист расходов для категории с названием categoryName
     */
    public List<Expense> getExpenseListByCategoryName(String categoryName) {

        return testList;
    }

    /**
     * Изменение значений расхода по категории
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
     * Добавление новой категории
     *
     * @param categoryName название категории
     */
    public void addNewCategory(String categoryName) {
        categoryList.add(categoryName);
    }

    /**
     * @return список доходов
     */
    public List<Income> getIncomeList() {
        return new ArrayList<>();
    }

    /**
     * @return сумму всех
     */
    public float getTotalIncome() {
        return 5000.0f;
    }

    public String getCurrentMonth() {
        return currentMonth.toString() + " " + currentYear;
    }

    public void setPreviousMonth() {
        currentMonth = currentMonth.minus(1L);
        if (currentMonth.equals(Month.DECEMBER))
            currentYear -= 1;
    }

    public void setNextMonth() {
        currentMonth = currentMonth.plus(1L);
        if (currentMonth.equals(Month.JANUARY))
            currentYear += 1;
    }
}
