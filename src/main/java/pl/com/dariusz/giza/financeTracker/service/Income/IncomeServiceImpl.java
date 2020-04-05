package pl.com.dariusz.giza.financeTracker.service.Income;

import org.springframework.stereotype.Service;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Income;
import pl.com.dariusz.giza.financeTracker.repositories.IncomeRepository;
import pl.com.dariusz.giza.financeTracker.service.budget.BudgetService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final BudgetService budgetService;

    public IncomeServiceImpl(IncomeRepository incomeRepository, BudgetService budgetService) {
        this.incomeRepository = incomeRepository;
        this.budgetService = budgetService;
    }

    @Override
    public List<Income> createIncome(List<Income> income, Budget budget) {
        income.stream().forEach(i -> i.setBudget(budget));
        return incomeRepository.saveAll(income);
    }

    @Override
    public List<Income> getUserIncomes(Budget budget) {
        final Long idUser = budget.getId();

        final List<Income> userIncome = incomeRepository.findByBudget_Id(idUser);

        return userIncome.stream()
                .sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteIncome(Income income, Budget budget) {
        budgetService.deleteIncome(budget, income);
        incomeRepository.deleteById(income.getId());
    }

}
