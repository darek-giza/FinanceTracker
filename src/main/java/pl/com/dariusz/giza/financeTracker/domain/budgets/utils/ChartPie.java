package pl.com.dariusz.giza.financeTracker.domain.budgets.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ChartPie {

    private String name;
    private BigDecimal value;

    public ChartPie(String name, BigDecimal value) {
        this.name = name;
        this.value = value;
    }
}