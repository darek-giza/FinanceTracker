package pl.com.dariusz.giza.financeTracker.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Expense;
import pl.com.dariusz.giza.financeTracker.domain.budgets.ExpensesType;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Income;
import pl.com.dariusz.giza.financeTracker.domain.user.User;
import pl.com.dariusz.giza.financeTracker.repositories.BudgetsRepository;
import pl.com.dariusz.giza.financeTracker.repositories.ExpenseRepository;
import pl.com.dariusz.giza.financeTracker.repositories.IncomeRepository;
import pl.com.dariusz.giza.financeTracker.repositories.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class BootStrapData {

    private UserRepository userRepository;
    private BudgetsRepository budgetsRepository;
    private IncomeRepository incomeRepository;
    private ExpenseRepository expenseRepository;

    @Autowired
    public BootStrapData(UserRepository userRepository, BudgetsRepository budgetsRepository, IncomeRepository incomeRepository, ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.budgetsRepository = budgetsRepository;
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
    }


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void get() {
        Budget budget = new Budget(new BigDecimal(1250), null, null, null);
        budgetsRepository.save(budget);
        incomeRepository.saveAll(fillIncomes(budget));
        expenseRepository.saveAll(fillExpenses(budget));
        User user = new User("user", passwordEncoder().encode("user"), "ROLE_USER", budget);
        User admin = new User("admin", passwordEncoder().encode("admin"), "ROLE_ADMIN", null);
        userRepository.save(user);
        userRepository.save(admin);
    }

    public List<Income> fillIncomes(Budget budget) {
        List<Income> incomeList = new ArrayList<>();
        Income incomes1 = new Income(new BigDecimal(5000), "Salary1", LocalDate.now(), budget);
        Income incomes2 = new Income(new BigDecimal(3000), "Salary2", LocalDate.now(), budget);
        incomeList.add(incomes1);
        incomeList.add(incomes2);
        return incomeList;
    }

    public List<Expense> fillExpenses(Budget budget) {
        List<Expense> expenseList = new ArrayList<>();
        Expense expense1 = new Expense(ExpensesType.CHILDREN, new BigDecimal(200), "Books", LocalDate.now(), budget);
        Expense expense2 = new Expense(ExpensesType.EAT, new BigDecimal(100), "Diner", LocalDate.now(), budget);
        Expense expense3 = new Expense(ExpensesType.EAT, new BigDecimal(150), "Lunch", LocalDate.now(), budget);
        Expense expense4 = new Expense(ExpensesType.CLOTHES, new BigDecimal(100), "T-shirt", LocalDate.now(), budget);
        Expense expense5 = new Expense(ExpensesType.TRANSPORT, new BigDecimal(50), "Taxi", LocalDate.now(), budget);
        Expense expense6 = new Expense(ExpensesType.TRANSPORT, new BigDecimal(20), "Bus", LocalDate.now(), budget);
        Expense expense7 = new Expense(ExpensesType.ENTERTAINMENT, new BigDecimal(100), "Party", LocalDate.now(), budget);
        Expense expense8 = new Expense(ExpensesType.HEALTH, new BigDecimal(100), "Survey", LocalDate.now(), budget);
        Expense expense9 = new Expense(ExpensesType.FLAT, new BigDecimal(100), "Electricity", LocalDate.now(), budget);
        Expense expense10 = new Expense(ExpensesType.HYGIENE, new BigDecimal(20), "Cosmetics", LocalDate.now(), budget);
        Expense expense11 = new Expense(ExpensesType.OTHER, new BigDecimal(100), "Other", LocalDate.now(), budget);
        Expense expense12 = new Expense(ExpensesType.REPAYMENT, new BigDecimal(100), "Home loan", LocalDate.now(), budget);
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
