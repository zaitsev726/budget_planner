package repositoryTest;

import entities.Category;
import org.junit.Before;
import org.junit.Test;
import repository.CategoryRepository;

public class CategoryRepositoryTest {
    CategoryRepository categoryRepository;

    @Before
    public void createCategories(){
        categoryRepository = new CategoryRepository();

        Category transport = new Category();
        transport.setCategoryName("Транспорт");
        transport.setCurrentSum(0);

        Category products = new Category();
        products.setCategoryName("Продукты");
        products.setCurrentSum(100);

        Category presents = new Category();
        presents.setCategoryName("Подарки");
        presents.setCurrentSum(200);

        Category salary = new Category();
        salary.setCategoryName("Зарплата");
        salary.setCurrentSum(1000);

        categoryRepository.saveCategory(transport);
        categoryRepository.saveCategory(products);
        categoryRepository.saveCategory(presents);
        categoryRepository.saveCategory(salary);
    }

    @Test
    public void updatingTest(){
        
    }
}
