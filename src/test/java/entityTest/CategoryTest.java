package entityTest;

import entities.Category;
import entities.Expense;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class CategoryTest {
    private Category category;
    private Date date;

    @Before
    public void setCategory() {
        category = new Category();
        category.setIdCategory((long) 2);
        category.setCategoryName("RandomName");
        category.setCurrentSum(100);
        Expense expense = new Expense();

        expense.setSum(1238.233);
        date = new Date();
        expense.setDate(date);
        expense.setIdExpense((long) 2);
        expense.setIdCategory((long) 2);

        category.addExpense(expense);
    }

    @Test
    public void testCategoryParameters() {
        assertEquals("RandomName", category.getCategoryName());
        assertEquals(2, category.getIdCategory());
        assertEquals(100.0, category.getCurrentSum(), 1e-15);

        assertEquals(1, category.getExpenses().size());
        Expense expense = category.getExpenses().get(0);
        assertEquals(1238.233, expense.getSum(), 0.01);
        assertEquals(date, expense.getDate());
        assertEquals(2, expense.getIdExpense().longValue());
        assertEquals(category, expense.getCategoryExpense());
    }


    @Test
    public void addingRepetitiveExpense() {
        Expense expense = new Expense();
        expense.setSum(1238.233);
        date = new Date();
        expense.setDate(date);
        expense.setIdExpense((long) 2);
        expense.setIdCategory((long) 2);

        category.addExpense(expense);
        category.addExpense(expense);

        assertEquals(1, category.getExpenses().size());
        category.addExpense(null);
        assertEquals(1, category.getExpenses().size());
    }

    @Test
    public void removingExpense() {
        Expense expense = category.getExpenses().get(0);
        category.removeExpense(expense);
        assertEquals(0, category.getExpenses().size());
        assertNull(expense.getCategoryExpense());

        category.addExpense(expense);
        assertEquals(1, category.getExpenses().size());
        assertNotNull(expense.getCategoryExpense());

    }

    @Test
    public void stringTest() {
        assertEquals("Category{" +
                "idCategory=" + category.getIdCategory() +
                ", currentSum=" + category.getCurrentSum() +
                ", categoryName='" + category.getCategoryName() + '\'' +
                ", expenses=" + category.getExpenses() +
                '}', category.toString());
    }

    @Test
    public void equalsAndHashCodeTest(){
        assertNotEquals(null, category);
        assertNotEquals(category, new Expense());
        Expense expense = null;
        assertNotEquals(category, expense);
        Category C = new Category();
        C.setIdCategory(category.getIdCategory());
        C.setCurrentSum(category.getCurrentSum());
        C.setCategoryName(category.getCategoryName());
        C.setExpenses(category.getExpenses());

        assertEquals(category.getExpenses().size(), C.getExpenses().size());

        assertEquals(category, C);
        assertEquals(C.hashCode(), category.hashCode());

        C.setCategoryName("rAnDomNaMe2");
        assertNotEquals(category, C);
        C.setCategoryName(category.getCategoryName());

        C.setExpenses(new ArrayList<>());
        assertNotEquals(category, C);
        C.setExpenses(category.getExpenses());

        C.setCurrentSum(0);
        assertNotEquals(category, C);
        C.setCurrentSum(category.getCurrentSum());

        C.setIdCategory(-1L);
        assertNotEquals(category, C);

    }
}
