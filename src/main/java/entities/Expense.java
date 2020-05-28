package entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idExpense;

    @Column
    private double sum;

    @Column
    private Date date;

    @Column
    private Long idCategory;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Category categoryExpense;

    public void setCategoryExpense(Category categoryExpense) {
        setCategoryExpense(categoryExpense,true);
    }

    void setCategoryExpense(Category category, boolean add){
        this.categoryExpense = category;
        if(category!= null && add){
            category.addExpense(this,false);
        }
    }
    public Category getCategoryExpense() {
        return categoryExpense;
    }

    public Expense(){
        //Do nothing because it's hibernate constructor
    }

    public Long getIdExpense() { return idExpense; }

    public Long getIdCategory() { return idCategory; }

    public void setIdCategory(Long idCategory) { this.idCategory = idCategory; }

    public double getSum() { return sum; }

    public void setSum(double sum) { this.sum = sum; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public void setIdExpense(Long idExpense) { this.idExpense = idExpense; }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Expense))
            return false;

        final Expense expense = (Expense) obj;

        if (expense.getIdExpense() != null && expense.getIdCategory() != null
                && expense.getSum() != 0   && expense.getDate() != null) {
            return expense.getIdExpense().equals(this.idExpense) && expense.getIdCategory().equals(this.idCategory) &&
                    expense.getSum() == this.sum && expense.getDate().equals(date);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idExpense, sum, date, idCategory);
    }

    @Override
    public String toString() {
        return "Expense{" +
                "idExpense=" + idExpense +
                ", sum=" + sum +
                ", date=" + date +
                ", idCategory=" + idCategory +
                '}';
    }
}
