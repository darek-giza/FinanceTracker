package pl.com.dariusz.giza.financeTracker.controllers.income;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.com.dariusz.giza.financeTracker.controllers.security.AuthenticationFacade;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Income;
import pl.com.dariusz.giza.financeTracker.service.Income.IncomeService;
import pl.com.dariusz.giza.financeTracker.service.budget.BudgetService;
import pl.com.dariusz.giza.financeTracker.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping(IncomeController.BASE_URL)
public class IncomeController {

    public static final String BASE_URL = "/api/incomes";

    private final IncomeService incomeService;
    private final BudgetService budgetService;
    private final UserService userService;
    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public IncomeController(IncomeService incomeService, BudgetService budgetService, UserService userService, AuthenticationFacade authenticationFacade) {
        this.incomeService = incomeService;
        this.budgetService = budgetService;
        this.userService = userService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping
    public List<Income> getAll() {
        return incomeService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Income> saveIncome(@RequestBody List<Income> income) {
        final Budget budget = getBudget();
        budgetService.increaseBudget(budget, income);
        incomeService.createIncome(income, budget);
        return income;
    }


    public Budget getBudget() {
        final String userName = authenticationFacade.getAuthentication().getName();
        return userService.findUserByUsername(userName).getBudget();
    }

}
