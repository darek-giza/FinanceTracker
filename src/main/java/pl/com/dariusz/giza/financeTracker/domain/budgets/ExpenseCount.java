package pl.com.dariusz.giza.financeTracker.domain.budgets;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseCount implements Serializable {

    private Optional<BigDecimal> daily;
    private Optional<BigDecimal> weekly;
    private Optional<BigDecimal> monthly;
    private Optional<BigDecimal> yearly;

    public ExpenseCount(Optional<BigDecimal> daily, Optional<BigDecimal> weekly,
                        Optional<BigDecimal> monthly, Optional<BigDecimal> yearly) {
        this.daily = daily;
        this.weekly = weekly;
        this.monthly = monthly;
        this.yearly = yearly;
    }
}
