package repositoryTest;

import entities.Category;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import repository.CategoryRepository;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoryRepositoryTest {
    @Mock
    private CategoryRepository categoryRepository;

    private List<Category> categories;

    @Before
    public void createCategories() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categories = new ArrayList<>();

        Category category1 = new Category();
        category1.setCategoryName("Категория1");
        category1.setCurrentSum(0);

        Category category2 = new Category();
        category2.setIdCategory(1L);
        category2.setCategoryName("Категория2");
        category2.setCurrentSum(102.134);

        Category category2Update = new Category();
        category2Update.setIdCategory(1L);
        category2Update.setCategoryName("Категория2");
        category2Update.setCurrentSum(302.134);

        Category category3 = new Category();
        category3.setCategoryName("Категория3");
        category3.setCurrentSum(200.96);

        Category category4 = new Category();
        category4.setCategoryName("Категория4");
        category4.setCurrentSum(1000.02);

        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        categories.add(category4);

        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryRepository.findByNameCategory("Категория4")).thenReturn(category4);
        when(categoryRepository.findByNameCategory("Категория3")).thenThrow(new NoResultException());
        when(categoryRepository.findByNameCategory("Категория2")).thenReturn(category2);
        when(categoryRepository.findByNameCategory("Категория1")).thenThrow(new NoResultException());
        when(categoryRepository.updateCategory(argThat(Objects::isNull))).thenThrow(new IllegalArgumentException());
        when(categoryRepository.findByIdCategory(1L)).thenReturn(category2Update);
    }

    @Test
    public void findingTest() {
        Category category = categoryRepository.findByNameCategory("Категория4");
        assertEquals("Категория4", category.getCategoryName());
        assertEquals(1000.02, category.getCurrentSum(), 0.01);
    }

    @Test
    public void savingTest() {
        Category category = new Category();
        category.setCategoryName("RandomName");
        category.setCurrentSum(375.56);
        categoryRepository.saveCategory(category);

        verify(categoryRepository).saveCategory(category);
    }

    @Test
    public void updatingTest() {
        Category category = categoryRepository.findByNameCategory("Категория2");
        category.setCurrentSum(category.getCurrentSum() + 200);
        categoryRepository.updateCategory(category);

        verify(categoryRepository).updateCategory(category);

        assertNotNull(categoryRepository.findByIdCategory(category.getIdCategory()));
        assertEquals(302.134, category.getCurrentSum(), 0.01);

        category = null;
        try {
            category = categoryRepository.updateCategory(category);
        }catch (IllegalArgumentException e) {
            assertNull(category);
        }
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


        category = null;
        categoryRepository.deleteCategory(0L);

        try {
            category = categoryRepository.findByNameCategory("Категория3");
        } catch (NoResultException ignored) {
            assertNull(category);
        }

    }


    @Test
    public void findingAllTest() {
        assertEquals(4, categoryRepository.findAll().size());
        assertEquals(200.96, categories.get(2).getCurrentSum(), 0.01);
    }
}
