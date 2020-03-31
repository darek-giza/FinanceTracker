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
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChartYearlyServiceImpl implements ChartYearlyService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    @Autowired
    public ChartYearlyServiceImpl(IncomeRepository incomeRepository, ExpenseRepository expenseRepository) {
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<ChartYearly> generateChart(Budget budget, String year) {

        List<ChartYearly> yearlyList = new ArrayList<>();

        for (Integer month = 1; month < 13; month++) {

            final ChartYearly monthly = fillChartYearly(budget, month);

            yearlyList.add(monthly);
        }
        return yearlyList;
    }

    public ChartYearly fillChartYearly(Budget budget, Integer month) {

        BigDecimal incomes = reduceMonthlyIncomes(budget, month);
        BigDecimal expenses = reduceMonthlyExpenses(budget, month);
        BigDecimal budgetMonthly = incomes.subtract(expenses);

        return new ChartYearly(getMonth(month), incomes, expenses, budgetMonthly);
    }

    public BigDecimal reduceMonthlyIncomes(Budget budget, Integer month) {
        final List<Income> incomes = incomeRepository.findByBudget_Id(budget.getId());
        return incomes.stream().filter(e -> e.getDate().getYear() == now().getYear())
                .filter(e -> e.getDate().getMonth().getValue() == month)
                .map(Income::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal reduceMonthlyExpenses(Budget budget, Integer month) {
        final List<Expense> expense = expenseRepository.getByBudget_Id(budget.getId());
        return expense.stream().filter(e -> e.getDate().getYear() == now().getYear())
                .filter(e -> e.getDate().getMonth().getValue() == month)
                .map(e -> e.getAmount())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
    public LocalDate now() {
        return LocalDate.now();
    }
    public String getMonth(Integer month){
        return Month.of(month).name();
    }
}
