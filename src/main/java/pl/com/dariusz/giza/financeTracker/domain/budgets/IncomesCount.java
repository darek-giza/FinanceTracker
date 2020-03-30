package pl.com.dariusz.giza.financeTracker.domain.budgets;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IncomesCount implements Serializable {

    private BigDecimal weekly = BigDecimal.ZERO;
    private BigDecimal monthly = BigDecimal.ZERO;
    private BigDecimal yearly = BigDecimal.ZERO;

    public IncomesCount(BigDecimal weekly, BigDecimal monthly, BigDecimal yearly) {
        this.weekly = weekly;
        this.monthly = monthly;
        this.yearly = yearly;
    }
}
