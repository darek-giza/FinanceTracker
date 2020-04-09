package pl.com.dariusz.giza.financeTracker.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Expense;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Income;
import pl.com.dariusz.giza.financeTracker.domain.budgets.utils.ChartWeekly;
import pl.com.dariusz.giza.financeTracker.repositories.ExpenseRepository;
import pl.com.dariusz.giza.financeTracker.repositories.IncomeRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChartWeeklyServiceImpl implements ChartWeeklyService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    @Autowired
    public ChartWeeklyServiceImpl(IncomeRepository incomeRepository, ExpenseRepository expenseRepository) {
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
    }


    @Override
    public List<ChartWeekly> generateChartWeekly(Budget budget, int day) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");
        List<ChartWeekly> weeklyChart = new ArrayList<>();

        for (; day >= 0; day--) {
            LocalDateTime date = now().minusDays(day);
            final ChartWeekly chartWeekly = fillChart(budget, date);
            chartWeekly.setName(date.format(formatter));
            weeklyChart.add(chartWeekly);
        }
        return weeklyChart;
    }

    public ChartWeekly fillChart(Budget budget, LocalDateTime date) {
        final BigDecimal income = reduceIncomes(budget.getId(), date);
        final BigDecimal expense = reduceExpenses(budget.getId(), date);
        final BigDecimal amount = income.subtract(expense);
        return new ChartWeekly(null, income, expense, amount);
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
        return incomeRepository.findIncomesByBudget_IdAndDateBetween(id, date.minusDays(1), date);
    }

    public List<Expense> getExpenses(Long id, LocalDateTime date) {
        return expenseRepository.getByBudget_IdAndDateBetween(id, date.minusDays(1), date);
    }
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}