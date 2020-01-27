package pl.com.dariusz.giza.financeTracker.service.expense;

import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Expense;

import java.util.List;

public interface ExpenseService {

    List<Expense> getAll();

    List<Expense> createExpense(List<Expense> expense, Budget budget);
}
