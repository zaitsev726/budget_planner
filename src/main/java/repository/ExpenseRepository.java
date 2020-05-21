package repository;

import entities.Category;
import entities.Expense;

import javax.persistence.*;
import java.util.List;

public class ExpenseRepository {
    EntityManagerFactory emf;

    public ExpenseRepository() {
        emf = Persistence.createEntityManagerFactory("model");
    }

    public void saveExpense(Expense expense) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Category category;
        try {
            category = em.createQuery("select c from Category c where c.idCategory = :id", Category.class)
                    .setParameter("id", expense.getIdCategory())
                    .getSingleResult();
        } catch (NoResultException e) {
            return;
        }
        expense.setCategoryExpense(category);
        em.persist(expense);

        em.merge(category);
        em.getTransaction().commit();
    }

    public void deleteExpense(long id_expense) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("delete from Expense e where e.idExpense= :id");
        query.setParameter("id", id_expense);
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    public Expense updateExpense(Expense expense) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            expense = em.merge(expense);
            em.getTransaction().commit();
            em.close();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        return expense;
    }

    public List<Expense> findCategoryExpenses(long id_category) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select e from Expense e where e.idCategory = :id", Expense.class)
                .setParameter("id", id_category)
                .getResultList();
    }

    public Expense findByIdExpense(Long id_expense) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select e from Expense e where e.idExpense = :id", Expense.class)
                .setParameter("id", id_expense)
                .getSingleResult();
    }
}
