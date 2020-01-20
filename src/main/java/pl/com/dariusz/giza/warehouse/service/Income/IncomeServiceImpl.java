package pl.com.dariusz.giza.warehouse.service.Income;

import org.springframework.stereotype.Service;
import pl.com.dariusz.giza.warehouse.domain.budgets.Income;
import pl.com.dariusz.giza.warehouse.repositories.IncomeRepository;

import java.util.List;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;

    public IncomeServiceImpl(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Override
    public Income createIncome(Income income) {
        return incomeRepository.save(income);
    }

    @Override
    public List<Income> getAll() {
        return incomeRepository.findAll();
    }
}
