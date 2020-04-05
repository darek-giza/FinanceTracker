package pl.com.dariusz.giza.financeTracker.service.utils;

import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.utils.ChartYearly;

import java.util.List;

public interface ChartYearlyService {
    List<ChartYearly> generateYearlyChart(Budget budget);
}