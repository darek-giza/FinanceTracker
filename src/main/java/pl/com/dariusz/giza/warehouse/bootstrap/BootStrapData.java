package pl.com.dariusz.giza.warehouse.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.com.dariusz.giza.warehouse.domain.budgets.Budget;
import pl.com.dariusz.giza.warehouse.domain.budgets.Expense;
import pl.com.dariusz.giza.warehouse.domain.budgets.ExpensesType;
import pl.com.dariusz.giza.warehouse.domain.budgets.Income;
import pl.com.dariusz.giza.warehouse.domain.user.User;
import pl.com.dariusz.giza.warehouse.repositories.BudgetsRepository;
import pl.com.dariusz.giza.warehouse.repositories.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class BootStrapData {

    private UserRepository userRepository;
    private BudgetsRepository budgetsRepository;

    public BootStrapData(UserRepository userRepository, BudgetsRepository budgetsRepository) {
        this.userRepository = userRepository;
        this.budgetsRepository = budgetsRepository;
    }

    @Autowired


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void get() {
        User user = new User("user", passwordEncoder().encode("user"), "ROLE_USER");
        User admin = new User("admin", passwordEncoder().encode("admin"), "ROLE_ADMIN");
        userRepository.save(user);
        userRepository.save(admin);
    }
    @EventListener(ApplicationReadyEvent.class)
    public void fillBudgets() {
        Budget budget = new Budget(new BigDecimal(1000), fillRevenues(), fillExpenses());
        budgetsRepository.save(budget);
    }

    public List<Income> fillRevenues() {
        List<Income> incomeList = new ArrayList<>();
        Income revenue1 = new Income(new BigDecimal(5000), "Salary1", LocalDate.now());
        Income revenue2 = new Income(new BigDecimal(3000), "Salary2", LocalDate.now());
        incomeList.add(revenue1);
        incomeList.add(revenue2);
        return incomeList;
    }

    public List<Expense> fillExpenses() {
        List<Expense> expenseList = new ArrayList<>();
        Expense expense1 = new Expense(ExpensesType.CHILDREN, new BigDecimal(200), "Books", LocalDate.now());
        Expense expense2 = new Expense(ExpensesType.EAT, new BigDecimal(100), "Diner", LocalDate.now());
        Expense expense3 = new Expense(ExpensesType.EAT, new BigDecimal(150), "Lunch", LocalDate.now());
        Expense expense4 = new Expense(ExpensesType.CLOTHES, new BigDecimal(100), "T-shirt", LocalDate.now());
        Expense expense5 = new Expense(ExpensesType.TRANSPORT, new BigDecimal(50), "Taxi", LocalDate.now());
        Expense expense6 = new Expense(ExpensesType.TRANSPORT, new BigDecimal(20), "Bus", LocalDate.now());
        Expense expense7 = new Expense(ExpensesType.ENTERTAINMENT, new BigDecimal(100), "Party", LocalDate.now());
        Expense expense8 = new Expense(ExpensesType.HEALTH, new BigDecimal(100), "Survey", LocalDate.now());
        Expense expense9 = new Expense(ExpensesType.FLAT, new BigDecimal(100), "Electricity", LocalDate.now());
        Expense expense10 = new Expense(ExpensesType.HYGIENE, new BigDecimal(20), "Cosmetics", LocalDate.now());
        Expense expense11 = new Expense(ExpensesType.OTHER, new BigDecimal(100), "Other", LocalDate.now());
        Expense expense12 = new Expense(ExpensesType.REPAYMENT, new BigDecimal(100), "Home loan", LocalDate.now());
        expenseList.add(expense1);
        expenseList.add(expense2);
        expenseList.add(expense3);
        expenseList.add(expense4);
        expenseList.add(expense5);
        expenseList.add(expense6);
        expenseList.add(expense7);
        expenseList.add(expense8);
        expenseList.add(expense9);
        expenseList.add(expense10);
        expenseList.add(expense11);
        expenseList.add(expense12);
        return expenseList;
    }

}
