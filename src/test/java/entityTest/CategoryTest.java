package entityTest;

import entities.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CategoryTest {
    private Category category;

    @Before
    public void setCategory() {
        category = new Category();
        category.setCategoryName("RandomName");
        category.setCurrentSum(100);
    }

    @Test
    public void testCategoryParameters() {
        assertEquals("RandomName", category.getCategoryName());
        assertNull(category.getIdCategory());
        assertEquals(100.0, category.getCurrentSum(), 1e-15);
    }
}
