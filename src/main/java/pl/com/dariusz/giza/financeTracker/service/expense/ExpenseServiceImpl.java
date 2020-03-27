package pl.com.dariusz.giza.financeTracker.service.expense;

import org.springframework.stereotype.Service;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Expense;
import pl.com.dariusz.giza.financeTracker.domain.budgets.ExpenseCount;
import pl.com.dariusz.giza.financeTracker.repositories.ExpenseRepository;
import pl.com.dariusz.giza.financeTracker.service.expenseType.ExpenseTypeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseTypeService expenseTypeService;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, ExpenseTypeService expenseTypeService) {
        this.expenseRepository = expenseRepository;

        this.expenseTypeService = expenseTypeService;
    }

    @Override
    public List<Expense> getUserExpenses(Budget budget) {
        final Long idUser = budget.getId();
        List<Expense> userExpense = expenseRepository.getByBudget_Id(idUser);

        return userExpense.stream()
                .sorted((o1,o2)->o2.getDate().compareTo(o1.getDate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Expense> createExpense(List<Expense> expense, Budget budget) {

        Integer idOfExpenseType = expenseTypeService.getIdOfExpenseType(budget, expense).get();

        expense.stream().forEach(e -> {
            e.setBudget(budget);
            e.getExpenseType().setId(idOfExpenseType);
        });
        return expenseRepository.saveAll(expense);
    }

    @Override
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    @Override
    public ExpenseCount countExpense(Budget budget) {
        final LocalDate now = LocalDate.now();

        final List<Expense> byBudget_id = expenseRepository.getByBudget_Id(budget.getId());

        final Optional<BigDecimal> daily = byBudget_id.stream()
                .filter(e -> e.getDate().compareTo(now) == 0)
                .map(e -> e.getAmount())
                .reduce(BigDecimal::add);

        final Optional<BigDecimal> weekly = byBudget_id.stream()
                .filter(e -> e.getDate().plusDays(7).compareTo(now) > 0)
                .map(e -> e.getAmount())
                .reduce(BigDecimal::add);

        final Optional<BigDecimal> monthly = byBudget_id.stream()
                .filter(e -> e.getDate().plusDays(30).compareTo(now) > 0)
                .map(e -> e.getAmount())
                .reduce(BigDecimal::add);

        final Optional<BigDecimal> yearly = byBudget_id.stream()
                .map(e -> e.getAmount())
                .reduce(BigDecimal::add);

        return new ExpenseCount(daily, weekly, monthly, yearly);


    }
}
