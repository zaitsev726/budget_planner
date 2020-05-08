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
}
