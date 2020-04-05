package pl.com.dariusz.giza.financeTracker.service.expense;

import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Expense;

import java.util.List;

public interface ExpenseService {

    List<Expense> getUserExpenses(Budget budget);
    List<Expense> createExpense(List<Expense> expense, Budget budget);
    void deleteExpense(Expense expense, Budget budget);
}
