package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public Category(){
        //Do nothing because it's hibernate constructor
         }

    public long getIdCategory() { return idCategory; }

    public double getCurrentSum() { return currentSum; }

    public void setCurrentSum(double currentSum) { this.currentSum = currentSum; }

    public String getCategoryName() { return categoryName; }

    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public void setIdCategory(Long idCategory) { this.idCategory = idCategory; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Double.compare(category.currentSum, currentSum) == 0 &&
                Objects.equals(idCategory, category.idCategory) &&
                Objects.equals(categoryName, category.categoryName) &&
                Objects.equals(expenses, category.expenses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCategory, currentSum, categoryName, expenses);
    }

    @Override
    public String toString() {
        return "Category{" +
                "idCategory=" + idCategory +
                ", currentSum=" + currentSum +
                ", categoryName='" + categoryName + '\'' +
                ", expenses=" + expenses +
                '}';
    }
}
