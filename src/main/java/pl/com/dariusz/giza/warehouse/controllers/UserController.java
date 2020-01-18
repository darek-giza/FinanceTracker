package pl.com.dariusz.giza.warehouse.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.dariusz.giza.warehouse.domain.User;
import pl.com.dariusz.giza.warehouse.repositories.UserRepository;
import pl.com.dariusz.giza.warehouse.service.UserService;

import java.util.List;

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
    List<User> getAllUsers() {
        return userService.findAllUsers();
    }

}
