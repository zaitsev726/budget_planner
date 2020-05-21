package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCategory;

    @Column
    private double currentSum;

    @Column
    private String categoryName;

    @OneToMany(mappedBy = "categoryExpense", fetch = FetchType.LAZY)
    private List<Expense> expenses = new ArrayList<>();

    public List<Expense> getExpenses() { return expenses; }

    public void addExpense(Expense expense){
        addExpense(expense,true);
    }

    void addExpense(Expense expense, boolean set){
        if(expense!= null){
            if(getExpenses().contains(expense)){
                getExpenses().set(getExpenses().indexOf(expense), expense);
            }else{
                getExpenses().add(expense);
            }
            if(set){
                expense.setCategoryExpense(this,false);
            }
        }
    }

    public void setExpenses(List<Expense> expenses) { this.expenses = expenses; }

    public void removeExpense(Expense expense){
        getExpenses().remove(expense);
        expense.setCategoryExpense(null);
    }

    public Category(){}

    public long getIdCategory() { return idCategory; }

    public double getCurrentSum() { return currentSum; }

    public void setCurrentSum(double currentSum) { this.currentSum = currentSum; }

    public String getCategoryName() { return categoryName; }

    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public void setIdCategory(Long idCategory) { this.idCategory = idCategory; }
}
