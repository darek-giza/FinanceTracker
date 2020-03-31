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

    public LocalDate now() {
        return LocalDate.now();
    }

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
    public List<ChartYearly> generateChart(Budget budget, String year) {

        List<ChartYearly> yearlyList = new ArrayList<>();

        for (Integer month = 1; month < 13; month++) {

            final ChartYearly monthly = fillChartYearly(budget, month);

            yearlyList.add(monthly);
        }
        return yearlyList;
    }

    public ChartYearly fillChartYearly(Budget budget, Integer month) {

        final List<Income> incomes = incomeRepository.findByBudget_Id(budget.getId());
        BigDecimal incomeMonthly = incomes.stream().filter(e -> e.getDate().getYear() == now().getYear())
                .filter(e -> e.getDate().getMonth().getValue() == month)
                .map(e -> e.getAmount())
                .reduce(BigDecimal::add)
                .get();

        if (incomeMonthly == null) {
            incomeMonthly = BigDecimal.ZERO;
        }


        final List<Expense> expense = expenseRepository.getByBudget_Id(budget.getId());
        BigDecimal expenseMonthly = expense.stream().filter(e -> e.getDate().getYear() == now().getYear())
                .filter(e -> e.getDate().getMonth().getValue() == month)
                .map(e -> e.getAmount())
                .reduce(BigDecimal::add)
                .get();

        if (expenseMonthly == null) {
            expenseMonthly = BigDecimal.ZERO;
        }
        final String monthName = Month.of(month).name();

        final BigDecimal budgetMonthly = incomeMonthly.subtract(expenseMonthly);

        return new ChartYearly(monthName, incomeMonthly, expenseMonthly, budgetMonthly);
    }
}
