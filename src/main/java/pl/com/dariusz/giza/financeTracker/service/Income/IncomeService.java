package pl.com.dariusz.giza.financeTracker.service.Income;

import pl.com.dariusz.giza.financeTracker.domain.budgets.Income;

import java.util.List;

public interface IncomeService {

    Income createIncome(Income income);

    List<Income> getAll();
}
