package entityTest;

import entities.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryTest {
    private Category category;

    @Before
    public void setCategory(){
        category = new Category();
        category.setCategoryName("RandomName");
        category.setIdCategory((long) 1);
        category.setCurrentSum(100);
    }

    @Test
    public void testParameters(){
        assertEquals(category.getCategoryName(), "RandomName");
        assertEquals((long) category.getIdCategory(),  1);
        assertEquals(category.getCurrentSum(), 100);
    }
}
