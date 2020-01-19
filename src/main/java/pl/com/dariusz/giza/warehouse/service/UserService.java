package pl.com.dariusz.giza.warehouse.service;

import pl.com.dariusz.giza.warehouse.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findUserById(Long id);

    List<User> findAllUsers();

    User saveUser(User user);

}
