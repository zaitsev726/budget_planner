package repository;

import entities.Income;

import javax.persistence.*;

public class IncomeRepository {
    EntityManagerFactory emf;

    public IncomeRepository(){
        emf = Persistence.createEntityManagerFactory("model");
    }

    public void saveIncome(Income income){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(income);
        em.getTransaction().commit();
    }

    public void deleteIncome(long id_income){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            Query query = em.createQuery("delete from Income i where i.idIncome= :id");
            query.setParameter("id", id_income);
            query.executeUpdate();
            em.getTransaction().commit();
            em.close();
        }catch (RollbackException e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public Income updateIncome(Income income){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            income = em.merge(income);
            em.getTransaction().commit();
            em.close();
        }catch (RollbackException e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        return income;
    }
}
