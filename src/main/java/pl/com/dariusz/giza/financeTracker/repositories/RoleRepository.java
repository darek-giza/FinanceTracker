package pl.com.dariusz.giza.financeTracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.dariusz.giza.financeTracker.domain.user.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Override
    List<Role> findAll();
}
