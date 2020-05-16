package repository;

import categories.Category;
import repository.CategoryRepository;
import sun.awt.geom.AreaOp;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CategoryRepository{
    EntityManagerFactory emf;
    public CategoryRepositoryImpl(){
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
