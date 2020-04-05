package pl.com.dariusz.giza.financeTracker.service.utils;

import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.utils.IncomesCount;

public interface CountIncomesService {
    IncomesCount countIncomes(Budget budget);
}
