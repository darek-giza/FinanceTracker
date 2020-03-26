package pl.com.dariusz.giza.financeTracker.service.Income;

import org.springframework.stereotype.Service;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Income;
import pl.com.dariusz.giza.financeTracker.repositories.IncomeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;

    public IncomeServiceImpl(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Override
    public List<Income> createIncome(List<Income> income, Budget budget) {
        final List<Income> incomeList = income;
        incomeList.stream().forEach(i -> i.setBudget(budget));
        return incomeRepository.saveAll(incomeList);
    }

    @Override
    public List<Income> getUserIncomes(Budget budget) {
        final Long idUser = budget.getId();

        final List<Income> userIncome = incomeRepository.findByBudget_Id(idUser);

        return userIncome.stream()
                .sorted((o1,o2)->o2.getDate().compareTo(o1.getDate()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteIncome(Long id) {
        incomeRepository.deleteById(id);
    }
}
