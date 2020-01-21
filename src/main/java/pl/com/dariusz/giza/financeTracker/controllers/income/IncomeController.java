package pl.com.dariusz.giza.financeTracker.controllers.income;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Income;
import pl.com.dariusz.giza.financeTracker.service.Income.IncomeService;
import pl.com.dariusz.giza.financeTracker.service.budget.BudgetService;
import pl.com.dariusz.giza.financeTracker.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping(IncomeController.BASE_URL)
public class IncomeController {

    public static final String BASE_URL = "/api/income";

    private final IncomeService incomeService;
    private final BudgetService budgetService;
    private final UserService userService;

    @Autowired
    public IncomeController(IncomeService incomeService, BudgetService budgetService, UserService userService) {
        this.incomeService = incomeService;
        this.budgetService = budgetService;
        this.userService = userService;
    }

    @GetMapping
    public List<Income> getAll() {
        return incomeService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Income saveIncome(@RequestBody Income income) {

        final Budget budgetById = budgetService.findById(1L);

        budgetService.increaseBudget(budgetById,income);

        incomeService.createIncome(income,budgetById);

        return income;
    }

}
