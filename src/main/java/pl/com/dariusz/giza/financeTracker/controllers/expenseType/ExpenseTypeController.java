package pl.com.dariusz.giza.financeTracker.controllers.expenseType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.ExpenseType;
import pl.com.dariusz.giza.financeTracker.security.AuthenticationFacade;
import pl.com.dariusz.giza.financeTracker.service.expenseType.ExpenseTypeService;
import pl.com.dariusz.giza.financeTracker.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping(ExpenseTypeController.BASE_URL)
@CrossOrigin
public class ExpenseTypeController {

    public static final String BASE_URL = "/api/type";

    private final ExpenseTypeService expenseTypeService;
    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;

    @Autowired
    public ExpenseTypeController(ExpenseTypeService expenseTypeService, AuthenticationFacade authenticationFacade, UserService userService) {
        this.expenseTypeService = expenseTypeService;
        this.authenticationFacade = authenticationFacade;
        this.userService = userService;
    }

    @GetMapping
    public List<ExpenseType> getUserExpenseType() {
        return expenseTypeService.getExpenseTypeForUser(getBudget());
    }

    public Budget getBudget() {
        final String userName = authenticationFacade.getAuthentication().getName();
        return userService.findUserByUsername(userName).getBudget();
    }
}