package controller;

import categories.Category;
import service.BudgetPlannerService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CategoryController {
    private HashMap<String, Category> categories;
    public CategoryController(){
        categories = new HashMap<>();
        initializationDefaultCategory();
    }

    private void initializationDefaultCategory() {
        categories.put("Транспорт", new Category("Транспорт"));
        categories.put("Продукты", new Category("Продукты"));
        categories.put("Кафе", new Category("Кафе"));
        categories.put("Досуг", new Category("Досуг"));
        categories.put("Здоровье", new Category("Здоровье"));
        categories.put("Подарки", new Category("Подарки"));
    }

    public HashMap<String, Category> getCategories() {
        return categories;
    }

    public void addNewCategory(String name){
        categories.put(name, new Category(name));
    }

    public void deleteCategory(String name){
        categories.remove(name);
    }

    public String[] getCategoryExpenseHistory(String name){
        Category curCategory = categories.get(name);
        String[] array = new String[curCategory.getExpenseHistory().size()];
        int iter = 0;
        for(Map.Entry<String, Date> entry : curCategory.getExpenseHistory().entrySet()){
            array[iter] = entry.getKey();
            iter++;
        }
        return array;
    }

    public String[] getCategoryIncomeHistory(String name){
        Category curCategory = categories.get(name);
        String[] array = new String[curCategory.getIncomeHistory().size()];
        int iter = 0;
        for(Map.Entry<String, Date> entry : curCategory.getIncomeHistory().entrySet()){
            array[iter] = entry.getKey();
            iter++;
        }
        return array;
    }

    public boolean editCategoryHistory(String categoryName, String editStr){
        Category curCategory = categories.get(categoryName);

        return false;
    }


}
