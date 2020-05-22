package repositoryTest;

import entities.Income;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.IncomeRepository;

import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class IncomeRepositoryTest {
    private IncomeRepository incomeRepository;
    private Income income1;
    private Income income2;
    private Income income3;
    private Date date1;
    private Date date2;
    private Date date3;

    @Before
    public void createExpenses() {
        incomeRepository = new IncomeRepository();

        income1 = new Income();
        income1.setSum(111);
        date1 = new Date();
        income1.setDate(date1);

        income2 = new Income();
        income2.setSum(9398.72);
        date2 = new Date();
        income2.setDate(date2);

        income3 = new Income();
        income3.setSum(183.23);
        date3 = new Date();
        income3.setDate(date3);
        incomeRepository.saveIncome(income1);
        incomeRepository.saveIncome(income2);
    }

    @Test
    public void savingExpense() {
        try{
            incomeRepository.saveIncome(income3);
        }catch (RollbackException e){
            return;
        }
        assertNotNull(income1);
    }

    @Test
    public void updatingExpense(){
        income2.setSum(1224);
        income2 = incomeRepository.updateIncome(income2);
        assertEquals(income2.getSum(), 1224, 0.01);
    }

    @Test
    public void searchingIncome(){
        List<Income> incomes = incomeRepository.findByDate(date2);
        assertEquals(incomes.get(1).getSum(), 9398.72, 0.01);
    }
    @Test
    public void deletingExpense(){
        Income income = null;
        long id = incomeRepository.findByDate(date1).get(0).getIdIncome();
        incomeRepository.deleteIncomeByDate(date1);
        try{
            income = incomeRepository.findByIdIncome(id);
        }catch (NoResultException ignored){
            assertNull(income);
        }


        income = new Income();
        income.setSum(1569.1);
        Date newDate = new Date();
        income.setDate(newDate);
        incomeRepository.saveIncome(income);

        income = null;
        id = incomeRepository.findByDate(newDate).get(0).getIdIncome();
        incomeRepository.deleteIncome(id);
        try{
            income = incomeRepository.findByIdIncome(id);
        }catch (NoResultException ignored){
            assertNull(income);
        }
    }

    public void findingByMonthTest() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        Date prev = c.getTime();

        c = Calendar.getInstance();
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
        c.set(Calendar.DATE, c.getActualMinimum(Calendar.DATE));
        Date future = c.getTime();
        List<Income> incomes = incomeRepository.findByMonth(prev,future);
        assertEquals(incomes.get(0).getDate().getMonth(), (new Date()).getMonth());
    }


    @After
    public void deleteExpenses() {
        incomeRepository.deleteIncomeByDate(date2);
        incomeRepository.deleteIncomeByDate(date3);
    }
}
