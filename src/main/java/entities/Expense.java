package entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idExpense;

    @Column
    private Long idCategory;

    @Column
    private int sum;

    @Column
    private Date date;

    public Expense(){}

    public Long getIdExpense() { return idExpense; }

    public void setIdExpense(Long idExpense) { this.idExpense = idExpense; }

    public Long getIdCategory() { return idCategory; }

    public void setIdCategory(Long idCategory) { this.idCategory = idCategory; }

    public int getSum() { return sum; }

    public void setSum(int sum) { this.sum = sum; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }
}
