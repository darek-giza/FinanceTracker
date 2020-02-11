package pl.com.dariusz.giza.financeTracker.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.com.dariusz.giza.financeTracker.Jwt.JwtUtil;
import pl.com.dariusz.giza.financeTracker.domain.jwt.JwtRequest;
import pl.com.dariusz.giza.financeTracker.domain.jwt.JwtResponse;
import pl.com.dariusz.giza.financeTracker.domain.user.User;
import pl.com.dariusz.giza.financeTracker.security.AuthenticationFacade;
import pl.com.dariusz.giza.financeTracker.service.user.UserDetailsServiceImpl;
import pl.com.dariusz.giza.financeTracker.service.user.UserService;

import javax.servlet.ServletException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@CrossOrigin
public class UserController {


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private final UserService userService;
    @Autowired
    private final AuthenticationFacade authenticationFacade;

    public UserController(UserDetailsServiceImpl userDetailsServiceImpl, JwtUtil jwtUtil, UserService userService, AuthenticationFacade authenticationFacade) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.authenticationFacade = authenticationFacade;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception{

        authenticate(authenticationRequest.getUsername(),authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));

    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> seveUser(@RequestBody User user) throws Exception{

        return ResponseEntity.ok(userService.saveUser(user));
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
