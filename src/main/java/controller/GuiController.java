package controller;

import entities.Expense;
import service.BudgetPlannerService;

import java.awt.*;
import java.util.List;
import java.util.*;

public class GuiController {
    private BudgetPlannerService service;
    private CategoryController categoryController;
    private final Random rand = new Random();
    //test
    private List<Expense> testList;

    //
    public GuiController(BudgetPlannerService budgetPlannerService, CategoryController categoryController) {
        service = budgetPlannerService;
        this.categoryController = categoryController;
        //test
        testList = new ArrayList<>();
        Expense e = new Expense();
        e.setSum(1800.0);
        e.setDate(new GregorianCalendar(2020, GregorianCalendar.FEBRUARY, 22).getTime());
        testList.add(e);
        Expense e2 = new Expense();
        e2.setSum(200.0);
        e2.setDate(new GregorianCalendar(2020, GregorianCalendar.MAY, 15).getTime());
        testList.add(e2);
        //
    }

    /**
     * Создает список рандомных неповторяющихся цветов
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
        List<String> categoryList = new ArrayList<>();
        categoryList.add("Продукты");
        categoryList.add("Транспорт");
        categoryList.add("Здоровье");
        categoryList.add("Рестораны");
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
}
