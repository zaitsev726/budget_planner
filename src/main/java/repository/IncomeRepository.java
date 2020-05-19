package repository;

import entities.Category;
import entities.Income;

import javax.persistence.*;
import java.util.List;

public class IncomeRepository {
    EntityManagerFactory emf;

    public IncomeRepository(){
        emf = Persistence.createEntityManagerFactory("model");
    }

    public void saveIncome(Income income){

            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            Category category = null;
            try {
                category = em.createQuery("select c from Category c where c.idCategory = :id", Category.class)
                        .setParameter("id", income.getIdCategory())
                        .getSingleResult();
            }catch (NoResultException e){
                return;
            }
            income.setCategoryIncome(category);
            em.persist(income);
            em.merge(category);

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

    public List<Income> findCategoryIncomes(long id_category){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select i from Income i where i.idCategory = :id", Income.class)
                .setParameter("id", id_category)
                .getResultList();
    }

    public Income findByIdIncome(Long id_income){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select i from Income i where i.idIncome = :id", Income.class)
                .setParameter("id", id_income)
                .getSingleResult();
    }

}
