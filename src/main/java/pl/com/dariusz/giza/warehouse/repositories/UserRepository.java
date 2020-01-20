package pl.com.dariusz.giza.warehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.dariusz.giza.warehouse.domain.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
