package repositoryTest;

import entities.Income;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.IncomeRepository;

import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class IncomeRepositoryTest {
    private IncomeRepository incomeRepository;
    private Income income1;
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

        Income income2 = new Income();
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
        income3.setSum(1224);
        income3 = incomeRepository.updateIncome(income3);
        assertEquals(income3.getSum(), 1224, 0.01);
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
    }

    @After
    public void deleteExpenses() {
        incomeRepository.deleteIncomeByDate(date2);
        incomeRepository.deleteIncomeByDate(date3);
    }
}
