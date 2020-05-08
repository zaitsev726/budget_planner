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
}
