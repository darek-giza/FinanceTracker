package pl.com.dariusz.giza.financeTracker.controllers.expense;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.com.dariusz.giza.financeTracker.security.AuthenticationFacade;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Expense;
import pl.com.dariusz.giza.financeTracker.service.budget.BudgetService;
import pl.com.dariusz.giza.financeTracker.service.expense.ExpenseService;
import pl.com.dariusz.giza.financeTracker.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(ExpenseController.BASE_URL)
@CrossOrigin
public class ExpenseController {

    public static final String BASE_URL = "/api/expenses";

    private final ExpenseService expenseService;
    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;
    private final BudgetService budgetService;

    @Autowired
    public ExpenseController(ExpenseService expenseService, AuthenticationFacade authenticationFacade, UserService userService, BudgetService budgetService) {
        this.expenseService = expenseService;
        this.authenticationFacade = authenticationFacade;
        this.userService = userService;
        this.budgetService = budgetService;
    }

    @GetMapping
    public List<Expense> getAll() {
        return expenseService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Expense saveExpense(@RequestBody Expense expense) {
        final Budget budget = getBudget();
        List<Expense> expenseList=new ArrayList<>();
        expenseList.add(expense);
        budgetService.reduceBudget(budget, expenseList);

        expenseService.createExpense(expenseList, budget);

        return expense;
    }

    public Budget getBudget() {
        final String userName = authenticationFacade.getAuthentication().getName();
        return userService.findUserByUsername(userName).getBudget();
    }

}
