package pl.com.dariusz.giza.financeTracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Income;

import java.time.LocalDateTime;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findByBudget_Id(Long id);

    List<Income> findIncomesByBudget_IdAndDateBetween(Long id, LocalDateTime start, LocalDateTime end);
}

