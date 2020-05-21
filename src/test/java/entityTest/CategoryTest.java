package entityTest;

import entities.Category;
import entities.Expense;
import org.junit.Before;
import org.junit.Test;

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
        assertEquals(category.getIdCategory(), 2);
        assertEquals(100.0, category.getCurrentSum(), 1e-15);

        assertEquals(category.getExpenses().size(), 1);
        Expense expense = category.getExpenses().get(0);
        assertEquals(expense.getSum(), 1238.233,0.01 );
        assertEquals(expense.getDate(), date);
        assertEquals(expense.getIdExpense().longValue(),2);
        assertEquals(expense.getCategoryExpense(), category);
    }


    @Test
    public void addingRepetitiveExpense(){
        Expense expense = new Expense();
        expense.setSum(1238.233);
        date = new Date();
        expense.setDate(date);
        expense.setIdExpense((long) 2);
        expense.setIdCategory((long) 2);

        category.addExpense(expense);
        category.addExpense(expense);

        assertEquals(category.getExpenses().size(), 1);
    }

    @Test
    public void removingExpense(){
        Expense expense = category.getExpenses().get(0);
        category.removeExpense(expense);
        assertEquals(category.getExpenses().size(), 0);
        assertNull(expense.getCategoryExpense());

        category.addExpense(expense);
        assertEquals(category.getExpenses().size(), 1);
        assertNotNull(expense.getCategoryExpense());

    }

    @Test
    public void stringTest(){
        assertEquals(category.toString(), "Category{" +
                "idCategory=" + category.getIdCategory() +
                ", currentSum=" + category.getCurrentSum() +
                ", categoryName='" + category.getCategoryName() + '\'' +
                ", expenses=" + category.getExpenses() +
                '}');
    }
}
