package repositoryTest;

import entities.Category;
import entities.Expense;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.CategoryRepository;
import repository.ExpenseRepository;

import java.util.Date;

public class ExpenseRepositoryTest {
    private ExpenseRepository expenseRepository;
    private Date first;
    private Expense expense1;
    private Date second;
    private Expense expense2;

    @Before
    public void createExpenses(){
        expenseRepository = new ExpenseRepository();

        Category category = new Category();
        CategoryRepository categoryRepository = new CategoryRepository();

        category.setCategoryName("Категория1");
        category.setCurrentSum(386.26);
        categoryRepository.saveCategory(category);
        category = categoryRepository.findByNameCategory("Категория1");


       // System.out.println("*****************" + category.getIdCategory());
        expense1 = new Expense();
          expense1.setIdCategory(category.getIdCategory());
        expense1.setSum(100);
        //expense1.setCategoryExpense(category);
        first = new Date();
        expense1.setDate(first);
        //categoryRepository.updateCategory(category);

        expense2 = new Expense();
        expense2.setIdCategory(category.getIdCategory());
        expense2.setSum(1032.23);
        expense2.setDate(second);
        //categoryRepository.updateCategory(category);
        expense2.setCategoryExpense(category);
    }

    @Test
    public void savingExpense(){
        expenseRepository.saveExpense(expense1);
        expenseRepository.saveExpense(expense2);
      //  List<Expense> expenseList = expenseRepository.findCategoryExpenses(expense1.getIdCategory());



    }

    @After
    public void deleteExpenses(){
        CategoryRepository categoryRepository = new CategoryRepository();
        categoryRepository.deleteCategory("Категория1");
    }

}
