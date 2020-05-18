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
    public void setExpense(){
        expense = new Expense();
        expense.setIdCategory((long) 2);
        expense.setIdExpense((long) 3);
        expense.setSum(400);
        date = new Date();
        expense.setDate(date);
    }

    @Test
    public void testExpenseParameters(){
        assertEquals((long) expense.getIdCategory(), 2);
        assertEquals((long) expense.getIdExpense(), 3);
        assertEquals(expense.getSum(), 400);
        assertEquals(expense.getDate(), date);
    }
}
