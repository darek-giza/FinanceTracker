package pl.com.dariusz.giza.financeTracker.service.expenseType;

import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Expense;
import pl.com.dariusz.giza.financeTracker.domain.budgets.ExpenseType;

import java.util.List;
import java.util.Optional;

public interface ExpenseTypeService {

    List<ExpenseType> getExpenseTypeForUser(Budget budget);

    Optional<Integer> getIdOfExpenseType(Budget budget, List<Expense> expenses);

    String getDescriptionById(Integer id);

    ExpenseType createExpenseType(ExpenseType expenseType, Budget budget);

    void deleteExpenseType(Integer id);
}