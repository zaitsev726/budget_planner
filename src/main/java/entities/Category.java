package entities;

import javax.persistence.*;
import java.util.Collection;

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
    private Collection<Expense> expenses;

    @OneToMany(mappedBy = "categoryIncome", fetch = FetchType.LAZY)
    private Collection<Income> incomes;

    public Category(){}

    public Long getIdCategory() { return idCategory; }

    public void setIdCategory(Long idCategory) { this.idCategory = idCategory; }

    public double getCurrentSum() { return currentSum; }

    public void setCurrentSum(double currentSum) { this.currentSum = currentSum; }

    public String getCategoryName() { return categoryName; }

    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}
