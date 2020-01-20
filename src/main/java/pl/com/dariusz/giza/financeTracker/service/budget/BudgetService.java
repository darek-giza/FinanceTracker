package pl.com.dariusz.giza.financeTracker.service.budget;

import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;

import java.util.List;

public interface BudgetService {

    Budget findById(Integer id);

    Budget createBudgets(Budget budget);

    List<Budget> findAll();
}
