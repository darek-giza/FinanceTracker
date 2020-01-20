package pl.com.dariusz.giza.warehouse.service.budget;

import org.springframework.stereotype.Service;
import pl.com.dariusz.giza.warehouse.domain.budgets.Budget;
import pl.com.dariusz.giza.warehouse.repositories.BudgetsRepository;

import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetsRepository budgetsRepository;

    public BudgetServiceImpl(BudgetsRepository budgetsRepository) {
        this.budgetsRepository = budgetsRepository;
    }

    @Override
    public Budget findById(Integer id) {
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
}
