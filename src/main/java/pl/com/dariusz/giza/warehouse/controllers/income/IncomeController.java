package pl.com.dariusz.giza.warehouse.controllers.income;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.com.dariusz.giza.warehouse.domain.budgets.Income;
import pl.com.dariusz.giza.warehouse.service.Income.IncomeService;

import java.util.List;

@RestController
@RequestMapping(IncomeController.BASE_URL)
public class IncomeController {

    public static final String BASE_URL = "/api/income";

    private final IncomeService incomeService;

    @Autowired
    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping
    public List<Income> getAll() {
        return incomeService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Income saveIncome(@RequestBody Income income) {
        return incomeService.createIncome(income);
    }


}
