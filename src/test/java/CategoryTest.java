import categories.Category;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CategoryTest {
    public Category category;

    @Before
    public void setCategory(){
        category = new Category("РандомИмя");
    }


    @Test
    public void testDefaultValues(){
        assertEquals(category.getCategoryName(), "РандомИмя");
        assertEquals(category.getCurrentSum(), 0);
        assertEquals(category.getExpenseHistory().size(), 0);
        assertEquals(category.getIncomeHistory().size(), 0);
    }

    @Test
    public void testExpense(){
        category.addNewExpense(100);
        assertEquals(category.getExpenseHistory().size(), 1);
        Date date = new Date();
        category.addNewExpense(500, date);
        assertEquals(category.getExpenseHistory().size(), 2);
        assertEquals(category.getCurrentSum(), -600);
        assertTrue(category.getExpenseHistory().containsKey("Расход: -500 " + date));
        assertEquals(category.getExpenseHistory().get("Расход: -500 " + date),date);
    }

    @Test
    public void testIncome(){
        category.addNewIncome(200);
        assertEquals(category.getCurrentSum(), 200);
        Date date = new Date();
        category.addNewIncome(700, date);
        assertEquals(category.getIncomeHistory().size(), 2);
        assertEquals(category.getCurrentSum(), 900);
        assertTrue(category.getIncomeHistory().containsKey(("Доход: +700 " + date)));
        assertEquals(category.getIncomeHistory().get("Доход: +7 00 " + date),date);
    }
}
