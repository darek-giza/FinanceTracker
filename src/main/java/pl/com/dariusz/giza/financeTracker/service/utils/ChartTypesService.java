package pl.com.dariusz.giza.financeTracker.service.utils;

import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.utils.ChartPie;

import java.util.List;

public interface ChartTypesService {
    List<ChartPie> generateChartTypes(Budget budget);
}