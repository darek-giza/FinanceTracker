package pl.com.dariusz.giza.financeTracker.controllers.expense;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Expense;
import pl.com.dariusz.giza.financeTracker.security.AuthenticationFacade;
import pl.com.dariusz.giza.financeTracker.service.budget.BudgetService;
import pl.com.dariusz.giza.financeTracker.service.expense.ExpenseService;
import pl.com.dariusz.giza.financeTracker.service.expenseType.ExpenseTypeService;
import pl.com.dariusz.giza.financeTracker.service.user.UserService;

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
    private final ExpenseTypeService expenseTypeService;

    @Autowired
    public ExpenseController(ExpenseService expenseService, AuthenticationFacade authenticationFacade, UserService userService, BudgetService budgetService, ExpenseTypeService expenseTypeService) {
        this.expenseService = expenseService;
        this.authenticationFacade = authenticationFacade;
        this.userService = userService;
        this.budgetService = budgetService;
        this.expenseTypeService = expenseTypeService;
    }

    @GetMapping
    public List<Expense> getUserExpenses() {
        return expenseService.getUserExpenses(getBudget());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Expense> saveExpense(@RequestBody List<Expense> expense) {
        final Budget budget = getBudget();

        budgetService.reduceBudget(budget, expense);
        expenseService.createExpense(expense, budget);
        expenseTypeService.getIdOfExpenseType(budget, expense);

        return expense;
    }
    @DeleteMapping
    public Expense deleteExpense(@RequestBody Expense expense){
        expenseService.deleteExpense(expense.getId());
        return expense;
    }

    public Budget getBudget() {
        final String userName = authenticationFacade.getAuthentication().getName();
        return userService.findUserByUsername(userName).getBudget();
    }

}
