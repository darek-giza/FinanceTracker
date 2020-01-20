package pl.com.dariusz.giza.financeTracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Income;

public interface IncomeRepository extends JpaRepository<Income, Long> {
}

