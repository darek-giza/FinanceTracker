package pl.com.dariusz.giza.financeTracker.service.budget;

import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Expense;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Income;

import java.util.List;

public interface BudgetService {

    Budget findById(Long id);

    Budget createBudgets();

    List<Budget> findAll();

    Budget increaseBudget(Budget budget, List<Income> income);

    Budget reduceBudget(Budget budget, List<Expense> expenses);

    Budget deleteExpense(Budget budget, Expense expense);

    Budget deleteIncome(Budget budget, Income income);
}
