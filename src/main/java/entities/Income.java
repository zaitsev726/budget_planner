package entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Income {
    @Id
    @GeneratedValue
    private Long idIncome;

    @Column
    private double sum;

    @Column
    private Date date;

    public Income(){}

    public Long getIdIncome() { return idIncome; }

    public void setIdIncome(Long idIncome) { this.idIncome = idIncome; }

    public double getSum() { return sum; }

    public void setSum(double sum) { this.sum = sum; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Income income = (Income) o;
        return Double.compare(income.sum, sum) == 0 &&
                Objects.equals(idIncome, income.idIncome) &&
                Objects.equals(date, income.date);
    }

    @Override
    public String toString() {
        return "Income{" +
                "idIncome=" + idIncome +
                ", sum=" + sum +
                ", date=" + date +
                '}';
    }
}
