package pl.com.dariusz.giza.financeTracker.service.expenseType;

import org.springframework.stereotype.Service;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Expense;
import pl.com.dariusz.giza.financeTracker.domain.budgets.ExpenseType;
import pl.com.dariusz.giza.financeTracker.repositories.ExpenseRepository;
import pl.com.dariusz.giza.financeTracker.repositories.ExpenseTypeRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseTypeServiceImpl implements ExpenseTypeService {

    private final ExpenseTypeRepository expenseTypeRepository;
    private final ExpenseRepository expenseRepository;

    public ExpenseTypeServiceImpl(ExpenseTypeRepository expenseTypeRepository, ExpenseRepository expenseRepository) {
        this.expenseTypeRepository = expenseTypeRepository;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<ExpenseType> getExpenseTypeForUser(Budget budget) {
        final List<Expense> allExpenses = expenseRepository.findAll();

        return allExpenses.stream()
                .filter(e -> e.getBudget().getId() == budget.getId())
                .map(e -> e.getExpenseType())
                .distinct()
                .sorted(Comparator.comparing(ExpenseType::getDescription))
                .collect(Collectors.toList());
    }

    @Override
    public ExpenseType createExpenseType(ExpenseType expenseType) {
        return expenseTypeRepository.save(expenseType);
    }
}