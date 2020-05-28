package repository;

import entities.Income;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IncomeRepository {
    EntityManagerFactory emf;
    private static Logger log = Logger.getLogger(CategoryRepository.class.getName());

    public IncomeRepository() {
        emf = Persistence.createEntityManagerFactory("model");
    }

    public void saveIncome(Income income) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(income);
        em.getTransaction().commit();
    }

    public void deleteIncome(long idIncome) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("delete from Income i where i.idIncome= :id");
        query.setParameter("id", idIncome);
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    public void deleteIncomeByDate(Date date) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("delete from Income i where i.date = :date");
        query.setParameter("date", date);
        query.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    public Income updateIncome(Income income) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            income = em.merge(income);
            em.getTransaction().commit();
            em.close();
        } catch (IllegalArgumentException e) {
            log.log(Level.SEVERE, "Exception: ", e);
            em.getTransaction().rollback();
            return null;
        }
        return income;
    }

    public Income findByIdIncome(Long idIncome) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select i from Income i where i.idIncome = :id", Income.class)
                .setParameter("id", idIncome)
                .getSingleResult();
    }

    public List<Income> findByDate(Date date) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select i from Income i where i.date = :date", Income.class)
                .setParameter("date", date)
                .getResultList();
    }

    public List<Income> findByMonth(Date prev, Date future){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select i from Income i where i.date > :prev and i.date < :future order by i.date desc, i.sum desc", Income.class)
                .setParameter("prev", prev)
                .setParameter("future", future)
                .getResultList();
    }
}
