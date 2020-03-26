package pl.com.dariusz.giza.financeTracker.service.expense;

import org.springframework.stereotype.Service;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Expense;
import pl.com.dariusz.giza.financeTracker.repositories.ExpenseRepository;
import pl.com.dariusz.giza.financeTracker.service.expenseType.ExpenseTypeService;

import java.util.List;
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

        expense.stream().forEach(e->{
            e.setBudget(budget);
            e.getExpenseType().setId(idOfExpenseType);
        });
        return expenseRepository.saveAll(expense);
    }

}
