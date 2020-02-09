package pl.com.dariusz.giza.financeTracker.controllers.user;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.com.dariusz.giza.financeTracker.controllers.security.AuthenticationFacade;
import pl.com.dariusz.giza.financeTracker.domain.user.User;
import pl.com.dariusz.giza.financeTracker.service.user.UserService;

import javax.servlet.ServletException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final AuthenticationFacade authenticationFacade;

    public UserController(UserService userService, AuthenticationFacade authenticationFacade) {
        this.userService = userService;
        this.authenticationFacade = authenticationFacade;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) throws ServletException {

        Long currentTimeMillis = System.currentTimeMillis();

        final User userByUsername = userService.findUserByUsername(user.getUsername());
        final String usernameFromDB = userByUsername.getUsername();
        final String passwordFromDB = userByUsername.getPassword();

        final String usernameFromRequest = user.getUsername();
        final String passwordFromRequest = user.getPassword();

        if (userByUsername == null) {
            throw new ServletException("User don't exist");
        }

        if (usernameFromDB != usernameFromRequest || passwordFromDB != passwordFromRequest) {

            throw new ServletException("Incorrect login or password");
        } else {
            return Jwts.builder()
                    .setSubject(user.getUsername())
                    .claim("roles", "user")
                    .setIssuedAt(new Date(currentTimeMillis))
                    .setExpiration(new Date(currentTimeMillis + 60000))
                    .signWith(SignatureAlgorithm.HS512, user.getPassword())
                    .compact();
        }
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
