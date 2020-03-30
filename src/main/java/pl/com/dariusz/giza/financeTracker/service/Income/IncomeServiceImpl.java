package pl.com.dariusz.giza.financeTracker.service.Income;

import org.springframework.stereotype.Service;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Income;
import pl.com.dariusz.giza.financeTracker.domain.budgets.IncomesCount;
import pl.com.dariusz.giza.financeTracker.repositories.IncomeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    public void deleteIncome(Long id) {
        incomeRepository.deleteById(id);
    }

    @Override
    public IncomesCount countIncomes(Budget budget) {
        final LocalDate now = LocalDate.now();

        final List<Income> byBudget_id = incomeRepository.findByBudget_Id(budget.getId());

        final BigDecimal weekly = byBudget_id.stream()
                .filter(e -> e.getDate().plusDays(7).compareTo(now) >= 0)
                .filter(e -> e.getDate().getYear() == now.getYear())
                .filter(e -> e.getDate().getMonth() == now.getMonth())
                .map(e -> e.getAmount())
                .reduce(BigDecimal::add).get();

        final BigDecimal monthly = byBudget_id.stream()
                .filter(e -> e.getDate().getYear() == now.getYear())
                .filter(e -> e.getDate().getMonth() == now.getMonth())
                .map(e -> e.getAmount())
                .reduce(BigDecimal::add).get();

        final BigDecimal yearly = byBudget_id.stream()
                .filter(e -> e.getDate().getYear() == now.getYear())
                .map(e -> e.getAmount())
                .reduce(BigDecimal::add).get();

        return new IncomesCount(weekly, monthly, yearly);
    }
}
