package repository;

import categories.Category;
import repository.CategoryRepository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CategoryRepository{
    EntityManagerFactory emf;
    public CategoryRepository(){
        emf = Persistence.createEntityManagerFactory("model");
    }

    public void deleteCategory(Category category) {
        EntityManager manager = emf.createEntityManager();
        manager.getTransaction().begin();

    }

    public void saveCategory(Category category) {

    }

    public void editCategory(Category category) {

    }
}
