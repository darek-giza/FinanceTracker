package pl.com.dariusz.giza.financeTracker.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Expense;
import pl.com.dariusz.giza.financeTracker.domain.budgets.utils.ChartPie;
import pl.com.dariusz.giza.financeTracker.service.expense.ExpenseService;
import pl.com.dariusz.giza.financeTracker.service.expenseType.ExpenseTypeService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChartTypesServiceImpl implements ChartTypesService {

    private final ExpenseService expenseService;
    private final ExpenseTypeService expenseTypeService;

    @Autowired
    public ChartTypesServiceImpl(ExpenseService expenseService, ExpenseTypeService expenseTypeService) {
        this.expenseService = expenseService;
        this.expenseTypeService = expenseTypeService;
    }

    @Override
    public List<ChartPie> generateChartTypes(Budget budget) {
        List<ChartPie> chartPieList = new ArrayList<>();
        List<Integer> listId = getListExpenseTypeId(budget);

        for (Integer idExpense : listId) {
            final BigDecimal value = reduceExpenseAmount(budget, idExpense);
            final String name = getDescription(idExpense);
            if (!value.equals(BigDecimal.ZERO) && name != null) {
                chartPieList.add(new ChartPie(name, value));
            }
        }
        return chartPieList.stream()
                .sorted(Comparator.comparing(ChartPie::getValue).reversed())
                .collect(Collectors.toList());
    }

    public List<Integer> getListExpenseTypeId(Budget budget) {
        return expenseTypeService.getExpenseTypeForUser(budget)
                .stream()
                .map(e -> e.getId()).distinct()
                .collect(Collectors.toList());
    }

    public String getDescription(Integer idExpense) {
        return expenseTypeService.getDescriptionById(idExpense);
    }

    public BigDecimal reduceExpenseAmount(Budget budget, Integer idExpense) {
        final List<Expense> userExpenses = expenseService.getUserExpenses(budget);
        return userExpenses.stream().filter(e -> e.getExpenseType().getId() == idExpense)
                .map(e -> e.getAmount())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}