package pl.com.dariusz.giza.financeTracker.domain.budgets.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseCount implements Serializable {

    private BigDecimal daily;
    private BigDecimal weekly;
    private BigDecimal monthly;
    private BigDecimal yearly;

    public ExpenseCount(BigDecimal daily, BigDecimal weekly,
                        BigDecimal monthly, BigDecimal yearly) {
        this.daily = daily;
        this.weekly = weekly;
        this.monthly = monthly;
        this.yearly = yearly;
    }
}
