package pl.com.dariusz.giza.financeTracker.controllers.user;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.com.dariusz.giza.financeTracker.domain.user.User;
import pl.com.dariusz.giza.financeTracker.service.user.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {

    public static final String BASE_URL = "/api/users";

    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }

}
