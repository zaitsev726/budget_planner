import categories.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryTest {
    public Category category;

    @Before
    public void setCategory(){
        category = new Category("РандомИмя");
    }


    @Test
    public void testDefaultValues(){
        assertEquals(category.getCategoryName(), "РандомИмя");
        assertEquals(category.getCurrentSum(), 0);
        assertEquals(category.getExpenseHistory().size(), 0);
        assertEquals(category.getIncomeHistory().size(), 0);
    }


}
