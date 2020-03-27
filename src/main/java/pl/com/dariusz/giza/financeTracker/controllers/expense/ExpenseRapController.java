package pl.com.dariusz.giza.financeTracker.controllers.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.ExpenseCount;
import pl.com.dariusz.giza.financeTracker.security.AuthenticationFacade;
import pl.com.dariusz.giza.financeTracker.service.budget.BudgetService;
import pl.com.dariusz.giza.financeTracker.service.expense.ExpenseService;
import pl.com.dariusz.giza.financeTracker.service.expenseType.ExpenseTypeService;
import pl.com.dariusz.giza.financeTracker.service.user.UserService;

@RestController
@CrossOrigin
public class ExpenseRapController {

    private final ExpenseService expenseService;
    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;
    private final BudgetService budgetService;
    private final ExpenseTypeService expenseTypeService;

    @Autowired
    public ExpenseRapController(ExpenseService expenseService, AuthenticationFacade authenticationFacade, UserService userService, BudgetService budgetService, ExpenseTypeService expenseTypeService) {
        this.expenseService = expenseService;
        this.authenticationFacade = authenticationFacade;
        this.userService = userService;
        this.budgetService = budgetService;
        this.expenseTypeService = expenseTypeService;
    }

    @GetMapping(path = "/api/expenses/expenseCount")
    public ExpenseCount countExpenses() {
        return expenseService.countExpense(getBudget());
    }

    public Budget getBudget() {
        final String userName = authenticationFacade.getAuthentication().getName();
        return userService.findUserByUsername(userName).getBudget();
    }
}
