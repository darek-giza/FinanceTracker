package pl.com.dariusz.giza.warehouse.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Enum<ExpensesType> expensesType;

    private BigDecimal amount;

    private String description;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "budgets_id")
    private Budgets budgets;

    public Expenses() {
    }

    public Expenses(Enum<ExpensesType> expensesType, BigDecimal amount, String description, LocalDate date) {
        this.expensesType = expensesType;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Enum<ExpensesType> getExpensesType() {
        return expensesType;
    }

    public void setExpensesType(Enum<ExpensesType> expensesType) {
        this.expensesType = expensesType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Budgets getBudgets() {
        return budgets;
    }

    public void setBudgets(Budgets budgets) {
        this.budgets = budgets;
    }
}
