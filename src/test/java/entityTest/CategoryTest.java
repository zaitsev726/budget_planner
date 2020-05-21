package entityTest;

import entities.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryTest {
    private Category category;

    @Before
    public void setCategory() {
        category = new Category();
        category.setIdCategory((long) 2);
        category.setCategoryName("RandomName");
        category.setCurrentSum(100);
    }

    @Test
    public void testCategoryParameters() {
        assertEquals("RandomName", category.getCategoryName());
        assertEquals(category.getIdCategory(), 2);
        assertEquals(100.0, category.getCurrentSum(), 1e-15);
    }
}
