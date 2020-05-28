package repositoryTest;

import entities.Category;
import entities.Expense;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.CategoryRepository;
import repository.ExpenseRepository;

import javax.persistence.NoResultException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ExpenseRepositoryTest {
    private ExpenseRepository expenseRepository;
    private Expense expense1;
    private Expense expense2;
    private Expense expense3;
    private Expense expense4;
    private ArrayList<Expense> expenseList;

    @Before
    public void createExpenses() {
        expenseRepository = new ExpenseRepository();

        Category category = new Category();
        CategoryRepository categoryRepository = new CategoryRepository();

        category.setCategoryName("Категория1");
        category.setCurrentSum(386.26);
        categoryRepository.saveCategory(category);
        category = categoryRepository.findByNameCategory("Категория1");

        expense1 = new Expense();
        expense1.setIdCategory(category.getIdCategory());
        expense1.setCategoryExpense(category);
        expense1.setSum(100);
        Date date = new Date();
        expense1.setDate(date);

        expense2 = new Expense();
        expense2.setIdCategory(category.getIdCategory());
        expense2.setSum(1032.23);
        expense2.setCategoryExpense(category);
        expense2.setDate(date);

        expense3 = new Expense();
        expense3.setIdCategory(category.getIdCategory());
        expense3.setSum(0.93);
        expense3.setCategoryExpense(category);
        expense3.setDate(date);

        expense4 = new Expense();
        expense4.setIdCategory(category.getIdCategory());
        expense4.setSum(32.78);
        expense4.setCategoryExpense(category);
        expense4.setDate(date);


        expenseRepository.saveExpense(expense1);
        expenseRepository.saveExpense(expense2);
        expenseRepository.saveExpense(expense3);
        expenseRepository.saveExpense(expense4);

        expenseList = (ArrayList<Expense>) expenseRepository.findCategoryExpenses(expense1.getIdCategory());
    }

    @Test
    public void savingExpense() {
        assertEquals(expenseList.get(0), expense1);
        assertEquals(expenseList.get(3), expense4);
    }

    @Test
    public void updatingExpense() {
        Date newDate = new Date();
        expense3.setDate(newDate);
        expense3.setSum(1256.12);
        expense3 = expenseRepository.updateExpense(expense3);

        assertEquals(expense3.getDate(), newDate);
        assertEquals(1256.12, expense3.getSum() , 0.01);
        assertEquals(expense3.getIdCategory(), expense1.getIdCategory());

        Expense expense = null;
        expense = expenseRepository.updateExpense(expense);
        assertNull(expense);
    }

    @Test
    public void findingTest() {
        Expense expense = expenseRepository.findByIdExpense(expense1.getIdExpense());
        assertEquals(expense, expense1);
    }

    @Test
    public void deletingTest() {
        long id = expense3.getIdExpense();
        expenseRepository.deleteExpense(expense3.getIdExpense());
        Expense expense = null;
        try {
            expense = expenseRepository.findByIdExpense(id);
        } catch (NoResultException ignored) {
            assertNull(expense);
        }
    }

    @Test
    public void badSaving() {
        Expense expense = new Expense();
        expense.setIdExpense((long) -1);
        expenseRepository.saveExpense(expense);

        expense = null;
        try {
            expense = expenseRepository.findByIdExpense((long) -1);
        } catch (NoResultException ignored) {
            assertNull(expense);
        }

    }

    @Test
    public void findingByMonthTest() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        Date prev = c.getTime();

        c = Calendar.getInstance();
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
        c.set(Calendar.DATE, c.getActualMinimum(Calendar.DATE));
        Date future = c.getTime();
        List<Expense> expenseList = expenseRepository.findExpensesByMonth(prev, future);
        assertEquals(expenseList.get(0).getDate().getMonth(), (new Date()).getMonth());

        expenseList = expenseRepository.findExpensesByMonthAndCategory(prev, future, expense1.getIdCategory());
        for (Expense expense : expenseList) {
            assertEquals(expense.getIdCategory(), expense1.getIdCategory());
        }

    }

    @After
    public void deleteExpenses() {
        CategoryRepository categoryRepository = new CategoryRepository();
        expenseRepository.deleteExpense(expense1.getIdExpense());
        expenseRepository.deleteExpense(expense2.getIdExpense());
        expenseRepository.deleteExpense(expense3.getIdExpense());
        expenseRepository.deleteExpense(expense4.getIdExpense());
        categoryRepository.deleteCategory("Категория1");
    }

}
