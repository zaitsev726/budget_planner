package repository;

import entities.Category;

import javax.persistence.*;

public class CategoryRepository {
    EntityManagerFactory emf;

    public CategoryRepository() {
        emf = Persistence.createEntityManagerFactory("model");
    }

    public void saveCategory(Category category) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(category);
        em.getTransaction().commit();
    }

    public void deleteCategory(long id_category) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("delete from Category c where c.idCategory= :id");
        query.setParameter("id", id_category);
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
            e.printStackTrace();
            em.getTransaction().rollback();
            return null;
        }
        return category;
    }

    public Category findByIdCategory(long id_category) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select c from Category c where c.idCategory = :id", Category.class)
                .setParameter("id", id_category)
                .getSingleResult();
    }

    public Category findByNameCategory(String nameCategory) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select c from Category c where c.categoryName = :name", Category.class)
                .setParameter("name", nameCategory)
                .getSingleResult();
    }
}
