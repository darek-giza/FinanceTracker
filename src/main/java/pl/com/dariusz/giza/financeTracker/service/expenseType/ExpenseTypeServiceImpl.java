package pl.com.dariusz.giza.financeTracker.service.expenseType;

import org.springframework.stereotype.Service;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Expense;
import pl.com.dariusz.giza.financeTracker.domain.budgets.ExpenseType;
import pl.com.dariusz.giza.financeTracker.repositories.ExpenseRepository;
import pl.com.dariusz.giza.financeTracker.repositories.ExpenseTypeRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseTypeServiceImpl implements ExpenseTypeService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseTypeRepository expenseTypeRepository;

    public ExpenseTypeServiceImpl(ExpenseRepository expenseRepository, ExpenseTypeRepository expenseTypeRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseTypeRepository = expenseTypeRepository;
    }

    @Override
    public List<ExpenseType> getExpenseTypeForUser(Budget budget) {

        return expenseTypeRepository.findExpenseTypeByBudgetId(budget.getId())
                .stream()
                .distinct()
                .sorted(Comparator.comparing(ExpenseType::getDescription))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Integer> getIdOfExpenseType(Budget budget, List<Expense> expenses) {

        String description = expenses.stream()
                .map(e -> e.getExpenseType()
                        .getDescription())
                        .findFirst()
                        .get();

        final List<ExpenseType> expenseTypeForUser = getExpenseTypeForUser(budget);

        return expenseTypeForUser.stream()
                .filter(e -> e.getDescription().equals(description))
                .map(e -> e.getId())
                .findFirst();
    }

    @Override
    public ExpenseType createExpenseType(ExpenseType expenseType, Budget budget) {
        expenseType.setBudgetId(budget.getId());
        return expenseTypeRepository.save(expenseType);
    }

    @Override
    public void deleteExpenseType(Integer id) {
        expenseTypeRepository.deleteById(id);
    }
}