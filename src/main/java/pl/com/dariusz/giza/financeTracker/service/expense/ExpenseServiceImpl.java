package pl.com.dariusz.giza.financeTracker.service.expense;

import org.springframework.stereotype.Service;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Expense;
import pl.com.dariusz.giza.financeTracker.domain.user.User;
import pl.com.dariusz.giza.financeTracker.repositories.ExpenseRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<Expense> getUserExpenses(Budget budget) {
        final List<Expense> allExpenses = expenseRepository.findAll();
        final Long idUser = budget.getId();

        return allExpenses.stream()
                .filter(e -> e.getBudget().getId() == idUser)
                .sorted((o1, o2) ->o2.getDate().compareTo(o1.getDate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Expense> createExpense(List<Expense> expense, Budget budget) {

        final List<Expense> listExpenses = expense;
        listExpenses.stream().forEach(e -> e.setBudget(budget));

        return expenseRepository.saveAll(listExpenses);
    }
}
