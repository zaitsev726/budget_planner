package repository;

public interface CategoryRepository {
    public void deleteCategory(String categoryName);
    public void saveCategory(String categoryName);
    public void editCategory(String categoryName);

}
