package pl.com.dariusz.giza.financeTracker.service.expenseType;

import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.ExpenseType;

import java.util.List;

public interface ExpenseTypeService {

    List<ExpenseType> getExpenseTypeForUser(Budget budget);

    ExpenseType createExpenseType(ExpenseType expenseType);
}