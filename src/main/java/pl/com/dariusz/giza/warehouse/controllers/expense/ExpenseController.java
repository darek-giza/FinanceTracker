package pl.com.dariusz.giza.warehouse.controllers.expense;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.com.dariusz.giza.warehouse.domain.budgets.Expense;
import pl.com.dariusz.giza.warehouse.service.expense.ExpenseService;

import java.util.List;

@RestController
@RequestMapping(ExpenseController.BASE_URL)
public class ExpenseController {

    public static final String BASE_URL = "/api/expense";

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public List<Expense> getAll(){
        return expenseService.getAll();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Expense saveExpense(@RequestBody Expense expense){
        return expenseService.createExpense(expense);
    }

}
