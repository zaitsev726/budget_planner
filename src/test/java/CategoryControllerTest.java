import categories.Category;
import controller.CategoryController;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;

public class CategoryControllerTest {
    public CategoryController controller;

    @Before
    public void setController() {
        controller = new CategoryController();
    }

    @Test
    public void testContainsDefaultValues() {
        HashMap<String, Category> categories = controller.getCategories();

        assertTrue(categories.containsKey("Транспорт"));
        assertTrue(categories.containsKey("Продукты"));
        assertTrue(categories.containsKey("Кафе"));
        assertTrue(categories.containsKey("Досуг"));
        assertTrue(categories.containsKey("Здоровье"));
        assertTrue(categories.containsKey("Подарки"));
    }


}
