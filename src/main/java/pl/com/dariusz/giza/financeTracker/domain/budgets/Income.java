package pl.com.dariusz.giza.financeTracker.domain.budgets;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Income implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private String description;

    private LocalDateTime date;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private Budget budget;

    public Income(BigDecimal amount, String description, LocalDateTime date, Budget budget) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.budget = budget;
    }
}
