package Controller;

import controller.GuiController;
import entities.Expense;
import entities.Income;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import repository.CategoryRepository;
import repository.ExpenseRepository;
import repository.IncomeRepository;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MockitoGuiControllerTest {
    @Mock
    private GuiController mockController;

    private GuiController controller;

    @Rule
    public MockitoRule initRule = MockitoJUnit.rule();

    String newCategory = "Подарки";

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new GuiController();
    }

    @Test
    public void basicTest() {
        Assert.assertNotNull(mockController);
        List<Color> spyOnColorList = Mockito.spy(mockController.getColorList(0));
        Mockito.when(spyOnColorList).thenReturn(Mockito.eq(new ArrayList<>()));

        int[] colorListSizeValues = new int[]{10, 10001, 256 * 256 * 256};
        for (int i : colorListSizeValues) {
            spyOnColorList = Mockito.spy(mockController.getColorList(i));
            Mockito.when(spyOnColorList.size()).thenReturn(i);
            Assert.assertEquals(i, spyOnColorList.size());
        }
    }

    @Test
    public void colorTest() {
        Assert.assertEquals(0, controller.getColorList(-1).size());
        Assert.assertEquals(0, controller.getColorList(0).size());
        Assert.assertEquals(10, controller.getColorList(10).size());
        Assert.assertEquals(10001, controller.getColorList(10001).size());
    }

    @Test
    public void categoryTest() {
        List<String> categoryList = controller.getCategoryList();
        controller.addNewCategory(newCategory);
        Assert.assertEquals(1, controller.getCategoryList().size());
        Assert.assertTrue(controller.getCategoryList().contains(newCategory));
        new CategoryRepository().deleteCategory(newCategory);
    }

    @Test
    public void categoryValuesTest() {
        controller.addNewCategory(newCategory);
        Assert.assertEquals(0, controller.getCategoryValuesList().size());
        controller.addNewExpenseByCategory(newCategory, 190.58);
        Assert.assertEquals(1, controller.getCategoryValuesList().size());
        Assert.assertEquals(100.0, controller.getCategoryValuesList().get(0), 1e-15);
        List<Expense> expenseList = controller.getExpenseListByCategoryName(newCategory);
        Assert.assertEquals(1, expenseList.size());
        new ExpenseRepository().deleteExpense(expenseList.get(0).getIdExpense());
        new CategoryRepository().deleteCategory(newCategory);
    }

    @Test
    public void expenseTest() {
        controller.addNewCategory(newCategory);
        Assert.assertEquals(0, controller.getExpenseListByCategoryName(newCategory).size());
        controller.addNewExpenseByCategory(newCategory, 190.58);
        Expense expected = new Expense();
        expected.setDate(new Date());
        expected.setCategoryExpense(new CategoryRepository().findByNameCategory(newCategory));
        expected.setSum(190.58);
        Assert.assertEquals(1, controller.getExpenseListByCategoryName(newCategory).size());
        Expense actual = controller.getExpenseListByCategoryName(newCategory).get(0);
        Assert.assertEquals(expected.getSum(), actual.getSum(), 1e-15);
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Assert.assertEquals(formatter.format(expected.getDate()), formatter.format(actual.getDate()));
        Date now = new Date();
        controller.setNewCategoryExpense(actual, 200.0, now);
        expected.setDate(now);
        expected.setSum(200.0);
        List<Expense> expenseList = controller.getExpenseListByCategoryName(newCategory);
        Assert.assertEquals(1, expenseList.size());
        actual = controller.getExpenseListByCategoryName(newCategory).get(0);
        Assert.assertEquals(expected.getSum(), actual.getSum(), 1e-15);
        Assert.assertEquals(formatter.format(expected.getDate()), formatter.format(actual.getDate()));
        new ExpenseRepository().deleteExpense(expenseList.get(0).getIdExpense());
        new CategoryRepository().deleteCategory(newCategory);
    }

    @Test
    public void incomeTest() {
        Assert.assertEquals(0, controller.getIncomeList().size());
        controller.addNewIncome(1000.0);
        Income expected = new Income();
        expected.setDate(new Date());
        expected.setSum(1000.0);
        Assert.assertEquals(1, controller.getIncomeList().size());
        Income actual = controller.getIncomeList().get(0);
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Assert.assertEquals(expected.getSum(), actual.getSum(), 1e-15);
        Assert.assertEquals(formatter.format(expected.getDate()), formatter.format(actual.getDate()));
        Date now = new Date();
        controller.setNewIncome(actual, 500, now);
        expected.setSum(500.0);
        expected.setDate(now);
        Assert.assertEquals(1, controller.getIncomeList().size());
        actual = controller.getIncomeList().get(0);
        Assert.assertEquals(expected.getSum(), actual.getSum(), 1e-15);
        Assert.assertEquals(formatter.format(expected.getDate()), formatter.format(actual.getDate()));
        new IncomeRepository().deleteIncome(actual.getIdIncome());
    }

    @Test
    public void totalTest() {
        Assert.assertEquals(0.0, controller.getTotalIncome(), 1e-15);
        Assert.assertEquals(0.0, controller.getTotalExpense(), 1e-15);
        controller.addNewIncome(500);
        Assert.assertEquals(500, controller.getTotalIncome(), 1e-15);
        Assert.assertEquals(1, controller.getIncomeList().size());
        Income income = controller.getIncomeList().get(0);
        new IncomeRepository().deleteIncome(income.getIdIncome());

        controller.addNewCategory(newCategory);

        controller.addNewExpenseByCategory(newCategory, 500);
        Assert.assertEquals(500, controller.getTotalExpense(), 1e-15);
        Assert.assertEquals(1, controller.getExpenseListByCategoryName(newCategory).size());
        Expense expense = controller.getExpenseListByCategoryName(newCategory).get(0);
        new ExpenseRepository().deleteExpense(expense.getIdExpense());
        new CategoryRepository().deleteCategory(newCategory);
    }

    @Test
    public void changeMonthTest() {
        controller.setPreviousMonth();
        Assert.assertEquals(0.0, controller.getTotalIncome(), 1e-15);
        Assert.assertEquals(0.0, controller.getTotalExpense(), 1e-15);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, c.get(Calendar.YEAR) + (c.get(Calendar.MONTH) == Calendar.DECEMBER ? -1 : 0));
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
        c.set(Calendar.DATE, c.getActualMinimum(Calendar.DATE));
        {
            controller.addNewIncome(500.0);
            Income income = controller.getIncomeList().get(0);
            Income expected = new Income();
            expected.setSum(500.0);
            expected.setDate(c.getTime());
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Assert.assertEquals(expected.getSum(), income.getSum(), 1e-15);
            Assert.assertEquals(formatter.format(expected.getDate()), formatter.format(income.getDate()));
            new IncomeRepository().deleteIncome(income.getIdIncome());
        }
        {
            controller.addNewCategory(newCategory);
            controller.addNewExpenseByCategory(newCategory, 500.0);
            Expense expense = controller.getExpenseListByCategoryName(newCategory).get(0);
            Expense expected = new Expense();
            expected.setSum(500.0);
            expected.setDate(c.getTime());
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Assert.assertEquals(expected.getSum(), expense.getSum(), 1e-15);
            Assert.assertEquals(formatter.format(expected.getDate()), formatter.format(expense.getDate()));
            new ExpenseRepository().deleteExpense(expense.getIdExpense());
            new CategoryRepository().deleteCategory(newCategory);
        }
        controller.setNextMonth();
        Assert.assertEquals(0, controller.getExpenseList().size());
        Assert.assertEquals(0, controller.getIncomeList().size());
    }
}


/*

    @Before
    public void initialize() {
        controller = new GuiController();
    }

    @Test
    public void basicTest() {
        Assert.assertNotNull(controller);
        Assert.assertNotNull(controller.getColorList(0));
        Assert.assertEquals(10, controller.getColorList(10).size());
        Assert.assertEquals(10001, controller.getColorList(10001).size());

    }

    @Test
    public void categoryTest() {
        Assert.assertEquals(0, controller.getCategoryList().size());
        controller.addNewCategory(newCategory);
        Assert.assertEquals(1, controller.getCategoryList().size());
        Assert.assertTrue(controller.getCategoryList().contains(newCategory));
        new CategoryRepository().deleteCategory(newCategory);
    }

    @Test
    public void categoryValuesTest() {
        controller.addNewCategory(newCategory);
        Assert.assertEquals(0, controller.getCategoryValuesList().size());
        controller.addNewExpenseByCategory(newCategory, 190.58);
        Assert.assertEquals(1, controller.getCategoryValuesList().size());
        Assert.assertEquals(100.0, controller.getCategoryValuesList().get(0), 1e-15);
        List<Expense> expenseList = controller.getExpenseListByCategoryName(newCategory);
        Assert.assertEquals(1, expenseList.size());
        new ExpenseRepository().deleteExpense(expenseList.get(0).getIdExpense());
        new CategoryRepository().deleteCategory(newCategory);
    }

    @Test
    public void expenseTest() {
        controller.addNewCategory(newCategory);
        Assert.assertEquals(0, controller.getExpenseListByCategoryName(newCategory).size());
        controller.addNewExpenseByCategory(newCategory, 190.58);
        Expense expected = new Expense();
        expected.setDate(new Date());
        expected.setCategoryExpense(new CategoryRepository().findByNameCategory(newCategory));
        expected.setSum(190.58);
        Assert.assertEquals(1, controller.getExpenseListByCategoryName(newCategory).size());
        Assert.assertEquals(expected, controller.getExpenseListByCategoryName(newCategory).get(0));
        controller.setNewCategoryExpense(expected, 200.0, new Date());
        expected.setSum(200.0);
        List<Expense> expenseList = controller.getExpenseListByCategoryName(newCategory);
        Assert.assertEquals(1, expenseList.size());
        Assert.assertTrue(controller.getExpenseListByCategoryName(newCategory).contains(expected));
        new ExpenseRepository().deleteExpense(expenseList.get(0).getIdExpense());
        new CategoryRepository().deleteCategory(newCategory);

    }

    @After
    public void clean() {
        CategoryRepository categoryRepository = new CategoryRepository();
        categoryRepository.deleteCategory(newCategory);
        ExpenseRepository expenseRepository = new ExpenseRepository();
        expenseRepository.deleteExpense(1);
    }

 */