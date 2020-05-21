package entityTest;

import entities.Income;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class IncomeTest {

    private Income income;
    private Date date;

    @Before
    public void setIncome(){
        income = new Income();
        income.setIdIncome((long) 5);
        income.setSum(300);
        date = new Date();
        income.setDate(date);
    }

    @Test
    public void testIncomeParameters(){
        assertEquals(5L, (long) income.getIdIncome());
        assertEquals(300.0, income.getSum(), 1e-15);
        assertEquals(income.getDate(), date);
    }

    @Test
    public void equalsTest(){
        Income income = new Income();
        income.setIdIncome((long) 5);
        income.setSum(300);
        income.setDate(date);

        assertEquals(this.income, income);

    }
    @Test
    public void stringTest(){
        assertEquals(income.toString(), "Income{" +
                "idIncome=" + income.getIdIncome() +
                ", sum=" + income.getSum() +
                ", date=" + income.getDate() +
                '}');
    }
}
