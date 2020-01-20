package pl.com.dariusz.giza.warehouse.domain.budgets;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Expense implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ExpensesType expensesType;

    private BigDecimal amount;

    private String description;

    private LocalDate date;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private Budget budget;

    public Expense(ExpensesType expensesType, BigDecimal amount, String description, LocalDate date, Budget budget) {
        this.expensesType = expensesType;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.budget = budget;
    }
}
