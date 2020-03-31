package pl.com.dariusz.giza.financeTracker.service.utils;

import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.utils.ChartYearly;

import java.util.Map;

public interface ChartYearlyService {
    Map<Integer, ChartYearly> generateChart(Budget budget, String year);
}