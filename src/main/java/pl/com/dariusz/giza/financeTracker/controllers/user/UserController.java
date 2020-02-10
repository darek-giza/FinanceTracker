package pl.com.dariusz.giza.financeTracker.controllers.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.com.dariusz.giza.financeTracker.Jwt.JwtUtil;
import pl.com.dariusz.giza.financeTracker.domain.user.User;
import pl.com.dariusz.giza.financeTracker.service.security.AuthenticationFacade;
import pl.com.dariusz.giza.financeTracker.service.user.UserDetailsServiceImpl;
import pl.com.dariusz.giza.financeTracker.service.user.UserService;

import javax.servlet.ServletException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@CrossOrigin
public class UserController {

    private UserDetailsServiceImpl userDetailsServiceImpl;
    private JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationFacade authenticationFacade;

    public UserController(UserDetailsServiceImpl userDetailsServiceImpl, JwtUtil jwtUtil, UserService userService, AuthenticationFacade authenticationFacade) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.authenticationFacade = authenticationFacade;
    }


    @PostMapping("/login")
    public String login(@RequestBody User user) throws Exception {

        final UserDetails userDetails = userDetailsServiceImpl
                .loadUserByUsername(user.getUsername());

        if (userDetails == null) {
            throw new ServletException("User don't exist");
        }
        return jwtUtil.generateToken(userDetails);
    }

    @GetMapping("/api/users/all")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/api/users/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @GetMapping("/api/user")
    public User getCurrentUser() {
        return userService.findUserByUsername(getUserName());
    }

    @PostMapping("/api/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    private String getUserName() {
        return authenticationFacade.getAuthentication().getName();
    }
}
