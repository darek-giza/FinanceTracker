package pl.com.dariusz.giza.warehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.dariusz.giza.warehouse.domain.Budget;

public interface BudgetsRepository extends JpaRepository<Budget,Long> {

}
