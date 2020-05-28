package repository;

import entities.Category;
import entities.Expense;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExpenseRepository {
    EntityManagerFactory emf;
    private static Logger log = Logger.getLogger(CategoryRepository.class.getName());

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

    public void deleteExpense(long idExpense) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("delete from Expense e where e.idExpense= :id");
        query.setParameter("id", idExpense);
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
            log.log(Level.SEVERE, "Exception: ", e);
            em.getTransaction().rollback();
        }
        return expense;
    }

    public List<Expense> findCategoryExpenses(long idCategory) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select e from Expense e where e.idCategory = :id", Expense.class)
                .setParameter("id", idCategory)
                .getResultList();
    }

    public Expense findByIdExpense(Long idExpense) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select e from Expense e where e.idExpense = :id", Expense.class)
                .setParameter("id", idExpense)
                .getSingleResult();
    }

    public List<Expense> findExpensesByMonth(Date prevMonth, Date futureMonth){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select e from Expense e where e.date > :prev and e.date < :future", Expense.class)
                .setParameter("prev", prevMonth)
                .setParameter("future", futureMonth)
                .getResultList();
    }

    public List<Expense> findExpensesByMonthAndCategory(Date prevMonth, Date futureMonth, long idCategory){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select e from Expense e where e.date > :prev and e.date < :future and e.idCategory = :id_category order by e.idCategory desc ", Expense.class)
                .setParameter("prev", prevMonth)
                .setParameter("future", futureMonth)
                .setParameter("id_category", idCategory)
                .getResultList();
    }
}
