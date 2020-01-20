package pl.com.dariusz.giza.warehouse.service.expense;

import org.springframework.stereotype.Service;
import pl.com.dariusz.giza.warehouse.domain.budgets.Expense;
import pl.com.dariusz.giza.warehouse.repositories.ExpenseRepository;

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
    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }
}
