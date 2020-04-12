package pl.com.dariusz.giza.financeTracker.domain.budgets.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ChartWeekly {

    private String name;
    private LocalDateTime date;
    private BigDecimal incomes;
    private BigDecimal expenses;
    private BigDecimal budget;

    public ChartWeekly(String name, LocalDateTime date, BigDecimal incomes, BigDecimal expenses, BigDecimal budget) {
        this.name = name;
        this.date = date;
        this.incomes = incomes;
        this.expenses = expenses;
        this.budget = budget;
    }
}