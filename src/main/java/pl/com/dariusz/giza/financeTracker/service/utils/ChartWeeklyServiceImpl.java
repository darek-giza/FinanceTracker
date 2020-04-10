package pl.com.dariusz.giza.financeTracker.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Expense;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Income;
import pl.com.dariusz.giza.financeTracker.domain.budgets.utils.ChartWeekly;
import pl.com.dariusz.giza.financeTracker.repositories.BudgetsRepository;
import pl.com.dariusz.giza.financeTracker.repositories.ExpenseRepository;
import pl.com.dariusz.giza.financeTracker.repositories.IncomeRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChartWeeklyServiceImpl implements ChartWeeklyService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final BudgetsRepository budgetsRepository;

    @Autowired
    public ChartWeeklyServiceImpl(IncomeRepository incomeRepository, ExpenseRepository expenseRepository, BudgetsRepository budgetsRepository) {
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
        this.budgetsRepository = budgetsRepository;
    }


    @Override
    public List<ChartWeekly> generateChartWeekly(Budget budget, int day) {
        List<ChartWeekly> weeklyChart = new ArrayList<>();
        BigDecimal amount;
        for (int i = 0; i <= day; i++) {
            LocalDateTime date = now().minusDays(i);
            if (i == 0) {
                amount = getTodayAmount(budget.getId());
            } else {
                amount = weeklyChart.get(i - 1).getBudget();
            }
            final ChartWeekly chartWeekly = fillChart(budget, date, amount);
            chartWeekly.setName(date.toLocalDate());
            weeklyChart.add(chartWeekly);
        }
        return weeklyChart.stream()
                .sorted(Comparator.comparing(ChartWeekly::getName))
                .collect(Collectors.toList());
    }

    public ChartWeekly fillChart(Budget budget, LocalDateTime date, BigDecimal amount) {
        final BigDecimal incomeToday = reduceIncomes(budget.getId(), date);
        final BigDecimal expenseToday = reduceExpenses(budget.getId(), date);
        final BigDecimal incomeTomorrow = reduceIncomes(budget.getId(), date.plusDays(1));
        final BigDecimal expenseTomorrow = reduceExpenses(budget.getId(), date.plusDays(1));
        final BigDecimal balance = amount.subtract(incomeTomorrow).add(expenseTomorrow);
        return new ChartWeekly(null, incomeToday, expenseToday.negate(), balance);
    }

    public BigDecimal reduceIncomes(Long id, LocalDateTime date) {
        return getIncomes(id, date).stream()
                .filter(e -> e.getDate().getDayOfYear() == date.getDayOfYear())
                .map(Income::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal reduceExpenses(Long id, LocalDateTime date) {
        return getExpenses(id, date).stream()
                .filter(e -> e.getDate().getDayOfYear() == date.getDayOfYear())
                .map(Expense::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public List<Income> getIncomes(Long id, LocalDateTime date) {
        return incomeRepository.findIncomesByBudget_IdAndDateBetween(id, date.minusDays(1), date.plusDays(1));
    }

    public List<Expense> getExpenses(Long id, LocalDateTime date) {
        return expenseRepository.getByBudget_IdAndDateBetween(id, date.minusDays(1), date.plusDays(1));
    }

    public BigDecimal getTodayAmount(Long id) {
        return budgetsRepository.findById(id).get().getBalance();
    }

    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}