package pl.com.dariusz.giza.warehouse.domain.budgets;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Budget implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
    private List<Income> revenues;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
    private List<Expense> expense;

    public Budget(BigDecimal balance, List<Income> revenues, List<Expense> expense) {
        this.balance = balance;
        this.revenues = revenues;
        this.expense = expense;
    }
}
