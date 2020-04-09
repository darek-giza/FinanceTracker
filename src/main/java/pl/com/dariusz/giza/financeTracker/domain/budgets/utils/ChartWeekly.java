package pl.com.dariusz.giza.financeTracker.domain.budgets.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ChartWeekly {

        private LocalDate name;
        private BigDecimal incomes;
        private BigDecimal expenses;
        private BigDecimal budget;

    public ChartWeekly(LocalDate name, BigDecimal incomes, BigDecimal expenses, BigDecimal budget) {
        this.name = name;
        this.incomes = incomes;
        this.expenses = expenses;
        this.budget = budget;
    }
}