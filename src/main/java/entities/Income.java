package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Income {
    @Id
    @GeneratedValue
    private Long idIncome;

    @Column
    private Long idCategory;

    @Column
    private int sum;

    @Column
    private Date date;

    public Income(){}
}
