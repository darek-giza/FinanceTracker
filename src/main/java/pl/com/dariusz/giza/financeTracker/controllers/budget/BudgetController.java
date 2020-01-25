package pl.com.dariusz.giza.financeTracker.controllers.budget;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.dariusz.giza.financeTracker.controllers.security.AuthenticationFacade;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.user.User;
import pl.com.dariusz.giza.financeTracker.service.budget.BudgetService;
import pl.com.dariusz.giza.financeTracker.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping(BudgetController.BASE_URL)
public class BudgetController {

    public static final String BASE_URL = "/api/budget";

    private final BudgetService budgetService;
    private final UserService userService;
    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public BudgetController(BudgetService budgetService, UserService userService, AuthenticationFacade authenticationFacade) {
        this.budgetService = budgetService;
        this.userService = userService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping("/all")
    public List<Budget> getAll() {
        return budgetService.findAll();
    }

    @GetMapping
    public Budget getOne() {

        return budgetService.findById(getUserId());
    }


    public Long getUserId(){

        final String userName = authenticationFacade.getAuthentication().getName();
        final User userByUsername = userService.findUserByUsername(userName);
        return userByUsername.getBudget().getId();


    }

}

