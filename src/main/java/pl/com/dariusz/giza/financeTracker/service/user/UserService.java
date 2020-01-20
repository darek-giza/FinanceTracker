package pl.com.dariusz.giza.financeTracker.service.user;

import pl.com.dariusz.giza.financeTracker.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findUserById(Long id);

    List<User> findAllUsers();

    User saveUser(User user);

}
