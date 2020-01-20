package pl.com.dariusz.giza.warehouse.domain.budgets;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Budget implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    @OneToMany(mappedBy = "budget")
    private List<Income> incomes;

    @OneToMany(mappedBy = "budget")
    private List<Expense> expense;

    public Budget(BigDecimal balance, List<Income> incomes, List<Expense> expense) {
        this.balance = balance;
        this.incomes = incomes;
        this.expense = expense;
    }
}
