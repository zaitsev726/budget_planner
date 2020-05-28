package entityTest;

import entities.Expense;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

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
}
