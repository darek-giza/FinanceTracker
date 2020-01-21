package pl.com.dariusz.giza.financeTracker.service.budget;

import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Income;

import java.util.List;

public interface BudgetService {

    Budget findById(Long id);

    Budget createBudgets(Budget budget);

    List<Budget> findAll();

    Budget increaseBudget(Budget budget, Income income);
}
