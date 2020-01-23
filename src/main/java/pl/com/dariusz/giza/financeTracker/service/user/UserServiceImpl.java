package pl.com.dariusz.giza.financeTracker.service.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.com.dariusz.giza.financeTracker.domain.user.User;
import pl.com.dariusz.giza.financeTracker.repositories.UserRepository;
import pl.com.dariusz.giza.financeTracker.service.budget.BudgetService;
import pl.com.dariusz.giza.financeTracker.service.role.RoleService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BudgetService budgetService;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, BudgetService budgetService, RoleService roleService) {
        this.userRepository = userRepository;
        this.budgetService = budgetService;
        this.roleService = roleService;
    }


    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    @Override
    public User saveUser(User user) {
        setPassword(user);
        user.setRoles(roleService.setUserRole());
        user.setBudget(budgetService.createBudgets());
        return userRepository.save(user);
    }

    @Override
    public User addUser(User user) {
        String password=user.getPassword();
        user.setPassword(password);
        return userRepository.save(user);
    }


    private void setPassword(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    }

}
