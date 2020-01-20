package pl.com.dariusz.giza.warehouse.controllers.budget;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.com.dariusz.giza.warehouse.domain.budgets.Budget;
import pl.com.dariusz.giza.warehouse.service.budget.BudgetService;

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
        return budgetService.createBudgets(budget);
    }

}
