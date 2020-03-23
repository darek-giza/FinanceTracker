package pl.com.dariusz.giza.financeTracker.service.Income;

import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Income;

import java.util.List;

public interface IncomeService {

    List<Income> createIncome(List<Income> income, Budget budget);

    List<Income> getUserIncomes(Budget budget);

    void deleteIncome(Long id);
}
