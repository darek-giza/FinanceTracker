package pl.com.dariusz.giza.financeTracker.service.utils;

import org.springframework.stereotype.Service;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Income;
import pl.com.dariusz.giza.financeTracker.domain.budgets.IncomesCount;
import pl.com.dariusz.giza.financeTracker.repositories.IncomeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class CountIncomesServiceImpl implements CountIncomesService {

    private final IncomeRepository incomeRepository;

    public CountIncomesServiceImpl(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Override
    public IncomesCount countIncomes(Budget budget) {
        final LocalDate now = LocalDate.now();

        final List<Income> byBudget_id = incomeRepository.findByBudget_Id(budget.getId());

        BigDecimal weekly = byBudget_id.stream()
                .filter(e -> e.getDate().plusDays(7).compareTo(now) >= 0)
                .filter(e -> e.getDate().getYear() == now.getYear())
                .filter(e -> e.getDate().getMonth() == now.getMonth())
                .map(e -> e.getAmount())
                .reduce(BigDecimal::add).get();

        if (weekly == null) {
            weekly = BigDecimal.ZERO;
        }

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
