package pl.com.dariusz.giza.warehouse.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.com.dariusz.giza.warehouse.domain.*;
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
        Budgets budget = new Budgets(new BigDecimal(1000),fillRevenues(),fillExpenses());
        budgetsRepository.save(budget);
    }

    public List<Revenues> fillRevenues() {
        List<Revenues> revenuesList = new ArrayList<>();
        Revenues revenue1 = new Revenues(new BigDecimal(5000), "Salary1", LocalDate.now());
        Revenues revenue2 = new Revenues(new BigDecimal(3000), "Salary2", LocalDate.now());
        revenuesList.add(revenue1);
        revenuesList.add(revenue2);
        return revenuesList;
    }

    public List<Expenses> fillExpenses() {
        List<Expenses> expensesList = new ArrayList<>();
        Expenses expenses1 = new Expenses(ExpensesType.CHILDREN, new BigDecimal(200), "Books", LocalDate.now());
        Expenses expenses2 = new Expenses(ExpensesType.EAT, new BigDecimal(100), "Diner", LocalDate.now());
        Expenses expenses3 = new Expenses(ExpensesType.EAT, new BigDecimal(150), "Lunch", LocalDate.now());
        Expenses expenses4 = new Expenses(ExpensesType.CLOTHES, new BigDecimal(100), "T-shirt", LocalDate.now());
        Expenses expenses5 = new Expenses(ExpensesType.TRANSPORT, new BigDecimal(50), "Taxi", LocalDate.now());
        Expenses expenses6 = new Expenses(ExpensesType.TRANSPORT, new BigDecimal(20), "Bus", LocalDate.now());
        Expenses expenses7 = new Expenses(ExpensesType.ENTERTAINMENT, new BigDecimal(100), "Party", LocalDate.now());
        Expenses expenses8 = new Expenses(ExpensesType.HEALTH, new BigDecimal(100), "Survey", LocalDate.now());
        Expenses expenses9 = new Expenses(ExpensesType.FLAT, new BigDecimal(100), "Electricity", LocalDate.now());
        Expenses expenses10 = new Expenses(ExpensesType.HYGIENE, new BigDecimal(20), "Cosmetics", LocalDate.now());
        Expenses expenses11 = new Expenses(ExpensesType.OTHER, new BigDecimal(100), "Other", LocalDate.now());
        Expenses expenses12 = new Expenses(ExpensesType.REPAYMENT, new BigDecimal(100), "Home loan", LocalDate.now());
        expensesList.add(expenses1);
        expensesList.add(expenses2);
        expensesList.add(expenses3);
        expensesList.add(expenses4);
        expensesList.add(expenses5);
        expensesList.add(expenses6);
        expensesList.add(expenses7);
        expensesList.add(expenses8);
        expensesList.add(expenses9);
        expensesList.add(expenses10);
        expensesList.add(expenses11);
        expensesList.add(expenses12);
        return expensesList;
    }

}
