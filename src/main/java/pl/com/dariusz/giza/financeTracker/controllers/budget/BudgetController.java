package pl.com.dariusz.giza.financeTracker.controllers.budget;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.user.User;
import pl.com.dariusz.giza.financeTracker.service.budget.BudgetService;
import pl.com.dariusz.giza.financeTracker.service.user.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(BudgetController.BASE_URL)
public class BudgetController {

    public static final String BASE_URL = "/api/budget";

    private final BudgetService budgetService;
    private final UserService userService;

    @Autowired
    public BudgetController(BudgetService budgetService, UserService userService) {
        this.budgetService = budgetService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<Budget> getAll() {
        return budgetService.findAll();
    }

    @GetMapping
    public Budget getOne(Principal principal) {
        String username = principal.getName();

        final User userByUsername = userService.findUserByUsername(username);
        final Long id = userByUsername.getBudget().getId();

        return budgetService.findById(id);
    }

}

