package repositoryTest;

import entities.Category;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.CategoryRepository;

import javax.persistence.NoResultException;

import static org.junit.Assert.*;

public class CategoryRepositoryTest {
    CategoryRepository categoryRepository;

    @Before
    public void createCategories() {
        categoryRepository = new CategoryRepository();

        Category category1 = new Category();
        category1.setCategoryName("Категория1");
        category1.setCurrentSum(0);

        Category category2 = new Category();
        category2.setCategoryName("Категория2");
        category2.setCurrentSum(102.134);

        Category category3 = new Category();
        category3.setCategoryName("Категория3");
        category3.setCurrentSum(200.96);

        Category category4 = new Category();
        category4.setCategoryName("Категория4");
        category4.setCurrentSum(1000.02);

        categoryRepository.saveCategory(category1);
        categoryRepository.saveCategory(category2);
        categoryRepository.saveCategory(category3);
        categoryRepository.saveCategory(category4);
    }

    @Test
    public void findingTest() {
        Category category = categoryRepository.findByNameCategory("Категория4");
        assertEquals(category.getCategoryName(), "Категория4");
        assertEquals(category.getCurrentSum(), 1000.02, 0.01);

        category = categoryRepository.findByIdCategory(category.getIdCategory() - 1);
        assertEquals(category.getCategoryName(), "Категория3");
        assertEquals(category.getCurrentSum(), 200.96, 0.01);
    }

    @Test
    public void savingTest() {
        Category category = new Category();
        category.setCategoryName("RandomName");
        category.setCurrentSum(375.56);
        categoryRepository.saveCategory(category);

        assertNotNull(categoryRepository.findByNameCategory("RandomName"));
    }

    @Test
    public void updatingTest() {
        Category category = categoryRepository.findByNameCategory("Категория2");
        category.setCurrentSum(category.getCurrentSum() + 200);
        category = categoryRepository.updateCategory(category);

        assertNotNull(categoryRepository.findByIdCategory(category.getIdCategory()));
        assertEquals(category.getCurrentSum(), 302.134, 0.01);
    }

    @Test
    public void deletingTest() {
        categoryRepository.deleteCategory("Категория1");
        Category category = null;
        try {
            category = categoryRepository.findByNameCategory("Категория1");
        } catch (NoResultException ignored) {
            assertNull(category);
        }
    }

    @After
    public void deleteCategories() {
        categoryRepository.deleteCategory("RandomName");
        categoryRepository.deleteCategory("Категория1");
        categoryRepository.deleteCategory("Категория2");
        categoryRepository.deleteCategory("Категория3");
        categoryRepository.deleteCategory("Категория4");
    }
}
