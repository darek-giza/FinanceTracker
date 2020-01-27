package pl.com.dariusz.giza.financeTracker.service.expense;

import org.springframework.stereotype.Service;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Expense;
import pl.com.dariusz.giza.financeTracker.repositories.ExpenseRepository;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<Expense> getAll() {
        return expenseRepository.findAll();
    }

    @Override
    public List<Expense> createExpense(List<Expense> expense, Budget budget) {

        final List<Expense> listExpenses = expense;
        listExpenses.stream().forEach(e -> e.setBudget(budget));

        return expenseRepository.saveAll(listExpenses);
    }
}
