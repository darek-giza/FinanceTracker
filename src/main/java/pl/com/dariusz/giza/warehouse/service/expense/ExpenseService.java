package pl.com.dariusz.giza.warehouse.service.expense;

import pl.com.dariusz.giza.warehouse.domain.budgets.Expense;

import java.util.List;

public interface ExpenseService {

    List<Expense> getAll();

    Expense createExpense(Expense expense);
}
