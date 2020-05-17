package repository;

import entities.Expense;

import javax.persistence.*;

public class ExpenseRepository {
    EntityManagerFactory emf;

    public ExpenseRepository(){
        emf = Persistence.createEntityManagerFactory("model");
    }

    public void saveExpense(Expense expense){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(expense);
        em.getTransaction().commit();
    }

    public void deleteExpense(long id_expense){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            Query query = em.createQuery("delete from Expense e where e.idCategory= :id");
            query.setParameter("id", id_expense);
            query.executeUpdate();
            em.getTransaction().commit();
            em.close();
        }catch (RollbackException e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public Expense updateExpense(Expense expense){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            expense = em.merge(expense);
            em.getTransaction().commit();
            em.close();
        }catch (RollbackException e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        return expense;
    }
}
