package pl.com.dariusz.giza.financeTracker.controllers.budget;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.user.User;
import pl.com.dariusz.giza.financeTracker.service.budget.BudgetService;

import java.util.List;

@RestController
@RequestMapping(BudgetController.BASE_URL)
public class BudgetController {

    public static final String BASE_URL = "/api/budget";

    private final BudgetService budgetService;

    @Autowired
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping
    public List<Budget> getAll() {
        return budgetService.findAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Budget saveBudget(@RequestBody Budget budget) {



        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String userId = ((User) principal).getId().toString();


        budgetService.createBudgets(budget);
        return budget;
    }

}

