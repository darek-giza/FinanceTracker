package pl.com.dariusz.giza.financeTracker.service.utils;

import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.utils.ChartWeekly;

import java.util.List;

public interface ChartWeeklyService {

    List<ChartWeekly> generateChartWeekly(Budget budget, int day);
}