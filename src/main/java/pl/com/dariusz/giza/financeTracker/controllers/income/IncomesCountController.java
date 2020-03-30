package pl.com.dariusz.giza.financeTracker.controllers.income;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.IncomesCount;
import pl.com.dariusz.giza.financeTracker.security.AuthenticationFacade;
import pl.com.dariusz.giza.financeTracker.service.Income.IncomeService;
import pl.com.dariusz.giza.financeTracker.service.user.UserService;

@RestController
@CrossOrigin
public class IncomesCountController {

    private final IncomeService incomeService;
    private final UserService userService;
    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public IncomesCountController(IncomeService incomeService, UserService userService,
                                  AuthenticationFacade authenticationFacade) {
        this.incomeService = incomeService;
        this.userService = userService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping(path = "/api/incomes/incomesCount")
    public IncomesCount countIncomes() {
        return incomeService.countIncomes(getBudget());
    }

    public Budget getBudget() {
        final String userName = authenticationFacade.getAuthentication().getName();
        return userService.findUserByUsername(userName).getBudget();
    }


}
