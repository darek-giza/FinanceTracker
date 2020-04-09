package pl.com.dariusz.giza.financeTracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Expense;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> getByBudget_Id(Long budget_id);
    List<Expense> getByBudget_IdAndDateBetween(Long id, LocalDateTime start, LocalDateTime end);
}
