package pl.com.dariusz.giza.financeTracker.controllers.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.utils.ExpenseCount;
import pl.com.dariusz.giza.financeTracker.security.AuthenticationFacade;
import pl.com.dariusz.giza.financeTracker.service.user.UserService;
import pl.com.dariusz.giza.financeTracker.service.utils.CountExpenseService;

@RestController
@CrossOrigin
public class ExpenseUtilsController {

    private final CountExpenseService countExpenseService;
    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;

    @Autowired
    public ExpenseUtilsController(CountExpenseService countExpenseService, AuthenticationFacade authenticationFacade,
                                  UserService userService) {
        this.countExpenseService = countExpenseService;
        this.authenticationFacade = authenticationFacade;
        this.userService = userService;
    }

    @GetMapping(path = "/api/expenses/expenseCount")
    public ExpenseCount countExpenses() {
        return countExpenseService.countExpense(getBudget());
    }

    public Budget getBudget() {
        final String userName = authenticationFacade.getAuthentication().getName();
        return userService.findUserByUsername(userName).getBudget();
    }
}
