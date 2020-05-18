package entities;

import javax.persistence.*;
import java.util.Date;

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

    public Expense(){}

    public Long getIdExpense() { return idExpense; }

  //  public void setIdExpense(Long idExpense) { this.idExpense = idExpense; }

    public Long getIdCategory() { return idCategory; }

    public void setIdCategory(Long idCategory) { this.idCategory = idCategory; }

    public double getSum() { return sum; }

    public void setSum(double sum) { this.sum = sum; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }
}
