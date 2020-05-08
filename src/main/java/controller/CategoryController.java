package controller;

import categories.Category;
import service.BudgetPlannerService;

import java.util.HashMap;

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
}
