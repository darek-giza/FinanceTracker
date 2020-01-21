package pl.com.dariusz.giza.financeTracker.service.budget;

import org.springframework.stereotype.Service;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Income;
import pl.com.dariusz.giza.financeTracker.repositories.BudgetsRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetsRepository budgetsRepository;

    public BudgetServiceImpl(BudgetsRepository budgetsRepository) {
        this.budgetsRepository = budgetsRepository;
    }

    @Override
    public Budget findById(Long id) {
        return budgetsRepository.findById(id).get();
    }

    @Override
    public Budget createBudgets(Budget budget) {
        return budgetsRepository.save(budget);

    }

    @Override
    public List<Budget> findAll() {
        return budgetsRepository.findAll();
    }


    @Override
    public Budget increaseBudget(Budget budget, Income income) {
        Optional<Budget> currentBudget = budgetsRepository.findById(budget.getId());
        BigDecimal increase = currentBudget.get().getBalance().add(income.getAmount());
        currentBudget.get().setBalance(increase);
        return budgetsRepository.save(currentBudget.get());


    }
}
