package pl.com.dariusz.giza.warehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.dariusz.giza.warehouse.domain.budgets.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
