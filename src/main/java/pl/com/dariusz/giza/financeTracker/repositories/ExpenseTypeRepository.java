package pl.com.dariusz.giza.financeTracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.dariusz.giza.financeTracker.domain.budgets.ExpenseType;

@Repository
public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Integer> {
}