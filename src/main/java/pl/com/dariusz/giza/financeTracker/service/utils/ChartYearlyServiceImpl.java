package pl.com.dariusz.giza.financeTracker.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Expense;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Income;
import pl.com.dariusz.giza.financeTracker.domain.budgets.utils.ChartYearly;
import pl.com.dariusz.giza.financeTracker.repositories.BudgetsRepository;
import pl.com.dariusz.giza.financeTracker.repositories.ExpenseRepository;
import pl.com.dariusz.giza.financeTracker.repositories.IncomeRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChartYearlyServiceImpl implements ChartYearlyService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final BudgetsRepository budgetsRepository;

    @Autowired
    public ChartYearlyServiceImpl(IncomeRepository incomeRepository, ExpenseRepository expenseRepository, BudgetsRepository budgetsRepository) {
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
        this.budgetsRepository = budgetsRepository;
    }

    @Override
    public List<ChartYearly> generateYearlyChart(Budget budget) {

        List<ChartYearly> yearlyList = new ArrayList<>();

        BigDecimal amount;

        for (int i = 0; i < 12; i++) {
            final LocalDateTime date = now().minusMonths(i);

            if (i == 0) {
                amount = getTodayAmount(budget.getId());
            } else {
                amount = yearlyList.get(i - 1).getBudget();
            }

            final ChartYearly monthly = fillChartYearly(budget, date, amount);

            yearlyList.add(monthly);
        }
        return yearlyList.stream()
                .sorted(Comparator.comparing(ChartYearly::getDate))
                .collect(Collectors.toList());
    }

    public ChartYearly fillChartYearly(Budget budget, LocalDateTime date, BigDecimal amount) {
        BigDecimal incomes = reduceMonthlyIncomes(budget, date);
        BigDecimal expenses = reduceMonthlyExpenses(budget, date);
        BigDecimal lastMonthIncomes = reduceMonthlyIncomes(budget, date.plusMonths(1));
        BigDecimal lastMonthExpenses = reduceMonthlyExpenses(budget, date.plusMonths(1));
        final BigDecimal balance = amount.subtract(lastMonthIncomes).add(lastMonthExpenses);


        return new ChartYearly(setName(date), date, incomes, expenses.negate(), balance);
    }

    public BigDecimal reduceMonthlyIncomes(Budget budget, LocalDateTime date) {
        return getIncomes(budget, date).stream().filter(e -> e.getDate().getMonth() == date.getMonth())
                .map(Income::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal reduceMonthlyExpenses(Budget budget, LocalDateTime date) {
        return getExpenses(budget, date).stream().filter(e -> e.getDate().getMonth() == date.getMonth())
                .map(e -> e.getAmount())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public List<Expense> getExpenses(Budget budget, LocalDateTime date) {
        return expenseRepository.getByBudget_IdAndDateBetween(budget.getId(), date.minusMonths(1), date.plusMonths(1));
    }

    public List<Income> getIncomes(Budget budget, LocalDateTime date) {
        return incomeRepository.findIncomesByBudget_IdAndDateBetween(budget.getId(), date.minusMonths(1), date.plusMonths(1));
    }

    public BigDecimal getTodayAmount(Long id) {
        return budgetsRepository.findById(id).get().getBalance();
    }

    public String setName(LocalDateTime date) {
        final String month = String.valueOf(date.getMonth()).substring(0, 3);
        return month + " '" + (date.getYear() - 2000);
    }

    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
