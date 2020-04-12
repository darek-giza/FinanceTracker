package pl.com.dariusz.giza.financeTracker.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public List<ChartYearly> generateYearlyChart(Long id) {
        List<ChartYearly> yearlyList = new ArrayList<>();
        BigDecimal amount;
        LocalDateTime date;
        ChartYearly monthly;

        for (int i = 0; i < 12; i++) {
            date = now().minusMonths(i);

            if (i == 0) {
                amount = getTodayAmount(id);
            } else {
                amount = yearlyList.get(i - 1).getBudget();
            }
            monthly = fillChartYearly(id, date, amount);

            yearlyList.add(monthly);
        }
        return yearlyList.stream()
                .sorted(Comparator.comparing(ChartYearly::getDate))
                .collect(Collectors.toList());
    }

    public ChartYearly fillChartYearly(Long id, LocalDateTime date, BigDecimal amount) {
        final BigDecimal incomes = reduceMonthlyIncomes(id, date);
        final BigDecimal expenses = reduceMonthlyExpenses(id, date);
        final BigDecimal lastMonthIncomes = reduceMonthlyIncomes(id, date.plusMonths(1));
        final BigDecimal lastMonthExpenses = reduceMonthlyExpenses(id, date.plusMonths(1));
        final BigDecimal balance = amount.subtract(lastMonthIncomes).add(lastMonthExpenses);
        return new ChartYearly(setName(date), date, incomes, expenses.negate(), balance);
    }

    public BigDecimal reduceMonthlyIncomes(Long id, LocalDateTime date) {
        return getIncomes(id, date).stream().filter(e -> e.getDate().getMonth() == date.getMonth())
                .map(Income::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal reduceMonthlyExpenses(Long id, LocalDateTime date) {
        return getExpenses(id, date).stream().filter(e -> e.getDate().getMonth() == date.getMonth())
                .map(e -> e.getAmount())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public List<Expense> getExpenses(Long id, LocalDateTime date) {
        return expenseRepository.getByBudget_IdAndDateBetween(id, date.minusMonths(1), date.plusMonths(1));
    }

    public List<Income> getIncomes(Long id, LocalDateTime date) {
        return incomeRepository.findIncomesByBudget_IdAndDateBetween(id, date.minusMonths(1), date.plusMonths(1));
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
