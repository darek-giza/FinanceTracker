package pl.com.dariusz.giza.warehouse.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Budgets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budgets_id")
    private Long id;

    private BigDecimal balance;

    @OneToMany(mappedBy = "budgets", cascade = CascadeType.ALL)
    private List<Revenues> revenues;

    @OneToMany(mappedBy = "budgets", cascade = CascadeType.ALL)
    private List<Expenses> expenses;

    public Budgets() {
    }

    public Budgets(BigDecimal balance, List<Revenues> revenues, List<Expenses> expenses) {
        this.balance = balance;
        this.revenues = revenues;
        this.expenses = expenses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Revenues> getRevenues() {
        return revenues;
    }

    public void setRevenues(List<Revenues> revenues) {
        this.revenues = revenues;
    }

    public List<Expenses> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expenses> expenses) {
        this.expenses = expenses;
    }
}
