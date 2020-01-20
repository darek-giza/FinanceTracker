package pl.com.dariusz.giza.warehouse.service.Income;

import pl.com.dariusz.giza.warehouse.domain.budgets.Income;

import java.util.List;

public interface IncomeService {

    Income createIncome(Income income);

    List<Income> getAll();
}
