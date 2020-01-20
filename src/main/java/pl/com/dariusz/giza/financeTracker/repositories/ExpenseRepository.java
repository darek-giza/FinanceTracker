package pl.com.dariusz.giza.financeTracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
