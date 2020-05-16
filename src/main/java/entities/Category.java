package entities;

import javax.persistence.*;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCategory;

    @Column
    private int currentSum;

    @Column
    private String categoryName;

    public Category(){}

    public Long getIdCategory() { return idCategory; }

    public void setIdCategory(Long idCategory) { this.idCategory = idCategory; }

    public int getCurrentSum() { return currentSum; }

    public void setCurrentSum(int currentSum) { this.currentSum = currentSum; }

    public String getCategoryName() { return categoryName; }

    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}
