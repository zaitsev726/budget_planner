package repository;

import entities.Category;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryRepository {
    EntityManagerFactory emf;

    private static Logger log = Logger.getLogger(CategoryRepository.class.getName());

    public CategoryRepository() {
        emf = Persistence.createEntityManagerFactory("model");
    }

    public void saveCategory(Category category) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(category);
        em.getTransaction().commit();
    }

    public void deleteCategory(long idCategory) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("delete from Category c where c.idCategory= :id");
        query.setParameter("id", idCategory);
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();

    }

    public void deleteCategory(String categoryName) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("delete from Category c where c.categoryName= :name");
        query.setParameter("name", categoryName);
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    public Category updateCategory(Category category) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            category = em.merge(category);
            em.getTransaction().commit();
            em.close();
        } catch (IllegalArgumentException e) {
            log.log(Level.SEVERE, "Exception: ", e);
            em.getTransaction().rollback();
            return null;
        }
        return category;
    }

    public Category findByIdCategory(long idCategory) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select c from Category c where c.idCategory = :id", Category.class)
                .setParameter("id", idCategory)
                .getSingleResult();
    }

    public Category findByNameCategory(String nameCategory) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select c from Category c where c.categoryName = :name", Category.class)
                .setParameter("name", nameCategory)
                .getSingleResult();
    }

    public List<Category> findAll(){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select c from Category c", Category.class)
                .getResultList();
    }
}
