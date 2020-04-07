package pl.com.dariusz.giza.financeTracker.service.utils;

import org.springframework.stereotype.Service;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Expense;
import pl.com.dariusz.giza.financeTracker.domain.budgets.utils.ExpenseCount;
import pl.com.dariusz.giza.financeTracker.repositories.ExpenseRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CountExpenseServiceImpl implements CountExpenseService {

    private final ExpenseRepository expenseRepository;

    public CountExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public ExpenseCount countExpense(Budget budget) {
        final LocalDateTime now = LocalDateTime.now();

        final List<Expense> byBudget_id = expenseRepository.getByBudget_Id(budget.getId());

        final BigDecimal daily = byBudget_id.stream()
                .filter(e -> e.getDate().compareTo(now) == 0)
                .map(e -> e.getAmount())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        final BigDecimal weekly = byBudget_id.stream()
                .filter(e -> e.getDate().plusDays(7).compareTo(now) > 0)
                .map(e -> e.getAmount())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        final BigDecimal monthly = byBudget_id.stream()
                .filter(e -> e.getDate().plusDays(30).compareTo(now) > 0)
                .map(e -> e.getAmount())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        final BigDecimal yearly = byBudget_id.stream()
                .map(e -> e.getAmount())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        return new ExpenseCount(daily, weekly, monthly, yearly);


    }
}
