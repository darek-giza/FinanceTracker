package pl.com.dariusz.giza.financeTracker.domain.budgets;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ExpenseType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "expenseType")
    private List<Expense> expense;

    public ExpenseType(String description) {
        this.description = description;
    }
}