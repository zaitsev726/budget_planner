package repositoryTest;

import entities.Category;
import entities.Expense;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import repository.ExpenseRepository;

import javax.persistence.NoResultException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ExpenseRepositoryTest {
    @Mock
    private ExpenseRepository expenseRepository;
    private Expense expense1;
    private Expense expense2;
    private Expense expense3;
    private Expense expense4;
    private ArrayList<Expense> expenseList;

    @Before
    public void createExpenses() {
        expenseRepository = Mockito.mock(ExpenseRepository.class);
        expenseList = new ArrayList<>();

        Category category = new Category();
        category.setCategoryName("Категория1");
        category.setCurrentSum(386.26);
        category.setIdCategory(1L);

        expense1 = new Expense();
        expense1.setIdExpense(1L);
        expense1.setIdCategory(category.getIdCategory());
        expense1.setCategoryExpense(category);
        expense1.setSum(100);
        Date date = new Date();
        expense1.setDate(date);

        expense2 = new Expense();
        expense2.setIdExpense(2L);
        expense2.setIdCategory(category.getIdCategory());
        expense2.setSum(1032.23);
        expense2.setCategoryExpense(category);
        expense2.setDate(date);

        expense3 = new Expense();
        expense3.setIdExpense(3L);
        expense3.setIdCategory(category.getIdCategory());
        expense3.setSum(0.93);
        expense3.setCategoryExpense(category);
        expense3.setDate(date);

        expense4 = new Expense();
        expense4.setIdExpense(4L);
        expense4.setIdCategory(category.getIdCategory());
        expense4.setSum(32.78);
        expense4.setCategoryExpense(category);
        expense4.setDate(date);


        expenseList.add(expense1);
        expenseList.add(expense2);
        expenseList.add(expense3);
        expenseList.add(expense4);

        when(expenseRepository.updateExpense(argThat(Objects::isNull))).thenThrow(new IllegalArgumentException());
        when(expenseRepository.findByIdExpense(1L)).thenReturn(expense1);
        when(expenseRepository.findByIdExpense(3L)).thenThrow(new NoResultException());
        when(expenseRepository.findByIdExpense(-1L)).thenThrow(new NoResultException());
        when(expenseRepository.findExpensesByMonth(anyObject(), anyObject())).thenReturn(expenseList);
        when(expenseRepository.findExpensesByMonthAndCategory(anyObject(),anyObject(),anyLong())).thenReturn(expenseList);

    }

    @Test
    public void savingExpense() {
        assertEquals(expenseList.get(0), expense1);
        assertEquals(expenseList.get(3), expense4);
    }

    @Test
    public void updatingExpense() {
        Date newDate = new Date();
        expense3.setDate(newDate);
        expense3.setSum(1256.12);
        expenseRepository.updateExpense(expense3);

        verify(expenseRepository).updateExpense(expense3);

        Expense expense = null;
        try {
            expense = expenseRepository.updateExpense(expense);
        }catch (IllegalArgumentException e) {
            assertNull(expense);
        }
    }

    @Test
    public void findingTest() {
        Expense expense = expenseRepository.findByIdExpense(expense1.getIdExpense());
        assertEquals(expense, expense1);
    }

    @Test
    public void deletingTest() {
        long id = expense3.getIdExpense();
        expenseRepository.deleteExpense(expense3.getIdExpense());
        expenseList.remove(expense3);
        verify(expenseRepository).deleteExpense(3L);

        Expense expense = null;
        try {
            expense = expenseRepository.findByIdExpense(id);
        } catch (NoResultException ignored) {
            assertNull(expense);
        }
    }

    @Test
    public void badSaving() {
        Expense expense = new Expense();
        expense.setIdExpense((long) -1);
        expenseRepository.saveExpense(expense);
        verify(expenseRepository).saveExpense(expense);

        expense = null;
        try {
            expense = expenseRepository.findByIdExpense((long) -1);
        } catch (NoResultException ignored) {
            assertNull(expense);
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
        List<Expense> expenseList = expenseRepository.findExpensesByMonth(prev, future);
        assertEquals(expenseList.get(0).getDate().getMonth(), (new Date()).getMonth());

        expenseList = expenseRepository.findExpensesByMonthAndCategory(prev, future, expense1.getIdCategory());
        for (Expense expense : expenseList) {
            assertEquals(expense.getIdCategory(), expense1.getIdCategory());
        }

    }
}
