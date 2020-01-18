package pl.com.dariusz.giza.warehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.dariusz.giza.warehouse.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
