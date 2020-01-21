package pl.com.dariusz.giza.financeTracker.service.Income;

import org.springframework.stereotype.Service;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Income;
import pl.com.dariusz.giza.financeTracker.repositories.IncomeRepository;

import java.util.List;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;

    public IncomeServiceImpl(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Override
    public Income createIncome(Income income, Budget budget) {
        income.setBudget(budget);
        return incomeRepository.save(income);
    }

    @Override
    public List<Income> getAll() {
        return incomeRepository.findAll();
    }
}
