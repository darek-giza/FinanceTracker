package pl.com.dariusz.giza.warehouse.service.budget;

import pl.com.dariusz.giza.warehouse.domain.budgets.Budget;

import java.util.List;

public interface BudgetService {

    Budget findById(Integer id);

    Budget createBudgets(Budget budget);

    List<Budget> findAll();
}
