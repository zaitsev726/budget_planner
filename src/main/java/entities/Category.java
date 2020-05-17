package entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCategory;

    @Column
    private int currentSum;

    @Column
    private String categoryName;

    @OneToMany(mappedBy = "categoryExpense", fetch = FetchType.EAGER)
    private Collection<Expense> expenses;

    @OneToMany(mappedBy = "categoryIncome", fetch = FetchType.EAGER)
    private Collection<Income> incomes;

    public Category(){}

    public Long getIdCategory() { return idCategory; }

    public void setIdCategory(Long idCategory) { this.idCategory = idCategory; }

    public int getCurrentSum() { return currentSum; }

    public void setCurrentSum(int currentSum) { this.currentSum = currentSum; }

    public String getCategoryName() { return categoryName; }

    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}
