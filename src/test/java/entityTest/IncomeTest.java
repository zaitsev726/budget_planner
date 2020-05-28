package entityTest;

import entities.Category;
import entities.Income;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class IncomeTest {

    private Income income;
    private Date date;

    @Before
    public void setIncome() {
        income = new Income();
        income.setIdIncome((long) 5);
        income.setSum(300);
        date = new Date();
        income.setDate(date);
    }

    @Test
    public void testIncomeParameters() {
        assertEquals(5L, (long) income.getIdIncome());
        assertEquals(300.0, income.getSum(), 1e-15);
        assertEquals(income.getDate(), date);
    }

    @Test
    public void stringTest() {
        assertEquals(income.toString(), "Income{" +
                "idIncome=" + income.getIdIncome() +
                ", sum=" + income.getSum() +
                ", date=" + income.getDate() +
                '}');
    }

    @Test
    public void equalsTest() {
        assertEquals(income, income);
        assertNotEquals(null, income);
        assertNotEquals(new Category(), income);
        Category category = null;
        assertNotEquals(income, category);

        Income I = new Income();
        I.setIdIncome((long) 5);
        I.setSum(300);
        I.setDate(date);

        assertEquals(income, I);
        assertEquals(I.hashCode(), income.hashCode());

        I.setIdIncome(-1L);
        assertNotEquals(I, income);
        I.setIdIncome(income.getIdIncome());

        I.setSum(0);
        assertNotEquals(I, income);
        I.setSum(income.getSum());

        I.setDate(new Date(1111111L));
        assertNotEquals(I, income);

    }
}
