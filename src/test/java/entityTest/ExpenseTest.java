package entityTest;

import entities.Expense;
import entities.Income;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ExpenseTest {
    private Expense expense;
    private Date date;

    @Before
    public void setExpense() {
        expense = new Expense();
        expense.setIdCategory((long) 2);
        expense.setIdExpense((long) 3);
        expense.setSum(400);
        date = new Date();
        expense.setDate(date);
    }

    @Test
    public void testExpenseParameters() {
        assertEquals(2, (long) expense.getIdCategory());
        assertEquals(3, (long) expense.getIdExpense());
        assertEquals(400.0, expense.getSum(), 1e-15);
        assertEquals(expense.getDate(), date);
    }

    @Test
    public void stringTest(){
        assertEquals(expense.toString(), "Expense{" +
                "idExpense=" + expense.getIdExpense() +
                ", sum=" + expense.getSum() +
                ", date=" + expense.getDate() +
                ", idCategory=" + expense.getIdCategory() +
                '}');
    }

    @Test
    public void equalsAndHashCodeTest(){
        assertEquals(expense,expense);
        assertNotEquals(null, expense);
        assertNotEquals(new Income(), expense);
        Income income = null;
        assertNotEquals(expense,income);

        Expense E = new Expense();
        E.setIdExpense(expense.getIdExpense());
        E.setDate(expense.getDate());
        E.setSum(expense.getSum());
        E.setIdCategory(expense.getIdCategory());

        assertEquals(E,expense);

        E.setIdExpense(-1L);
        assertNotEquals(E,expense);
        E.setIdExpense(expense.getIdExpense());

        E.setSum(0);
        assertNotEquals(E,expense);
        E.setSum(expense.getSum());

        E.setIdCategory(-1L);
        assertNotEquals(E,expense);
        E.setIdCategory(expense.getIdCategory());

        E.setDate(new Date(1111111L));
        assertNotEquals(E,expense);
    }
}
