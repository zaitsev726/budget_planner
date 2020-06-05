package repositoryTest;

import entities.Income;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import repository.IncomeRepository;

import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class IncomeRepositoryTest {
    @Mock
    private IncomeRepository incomeRepository;
    private Income income1;
    private Income income2;
    private Income income3;
    private Date date1;
    private Date date2;
    private Date date3;

    private ArrayList<Income> incomeList;

    @Before
    public void createExpenses() {
        incomeRepository = Mockito.mock(IncomeRepository.class);
        incomeList = new ArrayList<>();

        income1 = new Income();
        income1.setIdIncome(1L);
        income1.setSum(111);
        date1 = new Date();
        income1.setDate(date1);

        income2 = new Income();
        income2.setIdIncome(2L);
        income2.setSum(9398.72);
        date2 = new Date();
        income2.setDate(date2);

        income3 = new Income();
        income3.setIdIncome(3L);
        income3.setSum(183.23);
        date3 = new Date();
        income3.setDate(date3);

        incomeList.add(income1);
        incomeList.add(income2);
        incomeList.add(income3);

        doThrow(new RollbackException()).when(incomeRepository).saveIncome(income3);
        when(incomeRepository.findByDate(anyObject())).thenReturn(incomeList);
        when(incomeRepository.findByIdIncome(3L)).thenReturn(income3);
        when(incomeRepository.findByIdIncome(1L)).thenThrow(new NoResultException());
        when(incomeRepository.findByIdIncome(4L)).thenThrow(new NoResultException());

        doThrow(new NoResultException()).when(incomeRepository).saveIncome(null);
        when(incomeRepository.findByMonth(anyObject(),anyObject())).thenReturn(incomeList);
    }

    @Test
    public void savingExpense() {
        try {
            incomeRepository.saveIncome(income3);
        } catch (RollbackException e) {
            return;
        }
        assertNotNull(income1);
    }

    @Test
    public void updatingExpense() {
        income2.setSum(1224);
        incomeRepository.updateIncome(income2);
        verify(incomeRepository).updateIncome(income2);
    }

    @Test
    public void searchingIncome() {
        List<Income> incomes = incomeRepository.findByDate(date2);
        assertEquals(9398.72, incomes.get(1).getSum(), 0.01);

        Income income = incomeRepository.findByIdIncome(income3.getIdIncome());
        assertEquals(income, income3);
    }

    @Test
    public void deletingExpense() {
        Income income = null;
        long id = incomeRepository.findByDate(date1).get(0).getIdIncome();
        incomeRepository.deleteIncomeByDate(date1);
        verify(incomeRepository).deleteIncomeByDate(date1);
        try {
            income = incomeRepository.findByIdIncome(id);
        } catch (NoResultException ignored) {
            assertNull(income);
        }


        income = new Income();
        income.setIdIncome(4L);
        income.setSum(1569.1);
        Date newDate = new Date();
        income.setDate(newDate);
        incomeRepository.saveIncome(income);
        verify(incomeRepository).saveIncome(income);

        income = null;
        incomeRepository.deleteIncome(4L);
        try {
            income = incomeRepository.findByIdIncome(4L);
        } catch (NoResultException ignored) {
            assertNull(income);
        }
    }

    @Test
    public void findingByMonthTest() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        Date prev = c.getTime();

        c = Calendar.getInstance();
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
        c.set(Calendar.DATE, c.getActualMinimum(Calendar.DATE));
        Date future = c.getTime();
        List<Income> incomes = incomeRepository.findByMonth(prev, future);
        assertEquals(incomes.get(0).getDate().getMonth(), (new Date()).getMonth());
    }

    @Test
    public void badSavingTest(){
        Income income = null;
        try {
            income = incomeRepository.updateIncome(income);
        }catch (NoResultException ignored) {
            assertNull(income);
        }
    }
}
