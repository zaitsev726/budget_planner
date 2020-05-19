package repositoryTest;

import entities.Category;
import entities.Expense;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.CategoryRepository;
import repository.ExpenseRepository;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ExpenseRepositoryTest {
    private ExpenseRepository expenseRepository;
    private Expense expense1;
    private Expense expense2;
    private Expense expense3;

    @Before
    public void createExpenses() {
        expenseRepository = new ExpenseRepository();
        CategoryRepository categoryRepository = new CategoryRepository();

        Category category = new Category();
        category.setCategoryName("Категория1");
        category.setCurrentSum(386.26);
        categoryRepository.saveCategory(category);

        category = categoryRepository.findByNameCategory("Категория1");

        expense1 = new Expense();
        expense1.setIdCategory(category.getIdCategory());
        expense1.setSum(100);
        Date first = new Date();
        expense1.setDate(first);

        expense2 = new Expense();
        expense2.setIdCategory(category.getIdCategory());
        expense2.setSum(1032.23);
        Date second = new Date();
        expense2.setDate(second);

        expenseRepository.saveExpense(expense1);
        expenseRepository.saveExpense(expense2);
        List<Expense> expenseList = expenseRepository.findCategoryExpenses(expense1.getIdCategory());
        expense1 = expenseList.get(0);
        expense2 = expenseList.get(1);

        expense3 = new Expense();
        expense3.setIdCategory(category.getIdCategory());
        expense3.setSum(183.23);
        expense3.setDate(new Date());
    }

    @Test
    public void savingExpense() {
        expenseRepository.saveExpense(expense3);
        List<Expense> expenseList = expenseRepository.findCategoryExpenses(expense3.getIdCategory());
        assertEquals(expenseList.get(2).getSum(), 183.23, 0.01);
    }

    @Test
    public void updatingExpense(){
        Expense expense = expenseRepository.findByIdExpense(expense2.getIdExpense());
        expense.setSum(expense.getSum() + 102.32);
        expense = expenseRepository.updateExpense(expense);

        assertEquals(expense.getSum(), 1134.55, 0.01);
    }

    @Test
    public void deletingExpense(){
        Expense expense = null;
        long id = expense1.getIdExpense();
        expenseRepository.deleteExpense(id);
        try{
            expense = expenseRepository.findByIdExpense(id);
        }catch (NoResultException ignored){
            assertNull(expense);
        }
    }

    @After
    public void deleteExpenses() {
        CategoryRepository categoryRepository = new CategoryRepository();
        categoryRepository.deleteCategory("Категория1");
    }

}
