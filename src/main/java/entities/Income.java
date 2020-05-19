package entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Income {
    @Id
    @GeneratedValue
    private Long idIncome;

    @Column
    private Long idCategory;

    @Column
    private double sum;

    @Column
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    private Category categoryIncome;


    public void setCategoryIncome(Category categoryIncome) {
        setCategoryIncome(categoryIncome,true);
    }

    void setCategoryIncome(Category category, boolean add){
        this.categoryIncome = category;
        if(category!= null && add){
            category.addIncome(this,false);
        }
    }
    public Category getCategoryIncome() {
        return categoryIncome;
    }

    public Income(){}

    public Long getIdIncome() { return idIncome; }

    public void setIdIncome(Long idIncome) { this.idIncome = idIncome; }

    public Long getIdCategory() { return idCategory; }

    public void setIdCategory(Long idCategory) { this.idCategory = idCategory; }

    public double getSum() { return sum; }

    public void setSum(double sum) { this.sum = sum; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }
}
