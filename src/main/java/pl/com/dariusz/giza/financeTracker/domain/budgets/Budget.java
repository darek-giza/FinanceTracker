package pl.com.dariusz.giza.financeTracker.domain.budgets;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.com.dariusz.giza.financeTracker.domain.user.User;

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

    private String name;

    private BigDecimal balance;

    @OneToMany(mappedBy = "budget")
    private List<Income> incomes;

    @OneToMany(mappedBy = "budget")
    private List<Expense> expense;

    @JsonIgnore
    @OneToOne(mappedBy = "budget")
    private User user;

    public Budget(String name, BigDecimal balance, List<Income> incomes, List<Expense> expense, User user) {
        this.name = name;
        this.balance = balance;
        this.incomes = incomes;
        this.expense = expense;
        this.user = user;
    }
}
