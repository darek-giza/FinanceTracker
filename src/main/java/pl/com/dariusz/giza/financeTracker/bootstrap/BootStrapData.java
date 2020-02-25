package pl.com.dariusz.giza.financeTracker.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Expense;
import pl.com.dariusz.giza.financeTracker.domain.budgets.ExpenseType;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Income;
import pl.com.dariusz.giza.financeTracker.domain.user.Role;
import pl.com.dariusz.giza.financeTracker.domain.user.User;
import pl.com.dariusz.giza.financeTracker.repositories.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class BootStrapData {

    private UserRepository userRepository;
    private BudgetsRepository budgetsRepository;
    private IncomeRepository incomeRepository;
    private ExpenseRepository expenseRepository;
    private RoleRepository roleRepository;
    private ExpenseTypeRepository expenseTypeRepository;

    @Autowired
    public BootStrapData(UserRepository userRepository, BudgetsRepository budgetsRepository,
                         IncomeRepository incomeRepository, ExpenseRepository expenseRepository,
                         RoleRepository roleRepository, ExpenseTypeRepository expenseTypeRepository) {
        this.userRepository = userRepository;
        this.budgetsRepository = budgetsRepository;
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
        this.roleRepository = roleRepository;
        this.expenseTypeRepository = expenseTypeRepository;
    }


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @org.springframework.context.event.EventListener(ApplicationReadyEvent.class)

//    @EventListener(ApplicationReadyEvent.class)

    public void get() {
        Budget budget = new Budget("MyBudget", new BigDecimal(1250), null, null, null);
        budgetsRepository.save(budget);
        incomeRepository.saveAll(fillIncomes(budget));
        User admin = new User("admin", passwordEncoder().encode("admin"), "admin@admin.pl", 1, null, null);
        User user = new User("user", passwordEncoder().encode("user"), "dg@op.pl", 1, null, budget);
        admin.setRoles(new ArrayList<>(Arrays.asList(new Role("ROLE_ADMIN"))));
        user.setRoles(new ArrayList<>(Arrays.asList(new Role("ROLE_USER"))));

        userRepository.save(admin);
        userRepository.save(user);

        expenseTypeRepository.saveAll(fillExpenseType());
        expenseRepository.saveAll(fillExpenses(budget));


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
        final List<ExpenseType> type = expenseTypeRepository.findAll();

        List<Expense> expenseList = new ArrayList<>();
        Expense expense1 = new Expense(type.get(0), new BigDecimal(200), "Books", LocalDate.now(), budget);
        Expense expense2 = new Expense(type.get(1), new BigDecimal(100), "Diner", LocalDate.now(), budget);
        Expense expense3 = new Expense(type.get(1), new BigDecimal(150), "Lunch", LocalDate.now(), budget);
        Expense expense4 = new Expense(type.get(2), new BigDecimal(100), "T-shirt", LocalDate.now(), budget);
        Expense expense5 = new Expense(type.get(3), new BigDecimal(50), "Taxi", LocalDate.now(), budget);
        Expense expense6 = new Expense(type.get(3), new BigDecimal(20), "Bus", LocalDate.now(), budget);
        Expense expense7 = new Expense(type.get(4), new BigDecimal(100), "Party", LocalDate.now(), budget);
        Expense expense8 = new Expense(type.get(5), new BigDecimal(100), "Survey", LocalDate.now(), budget);
        Expense expense9 = new Expense(type.get(6), new BigDecimal(100), "Electricity", LocalDate.now(), budget);
        Expense expense10 = new Expense(type.get(7), new BigDecimal(20), "Cosmetics", LocalDate.now(), budget);
        Expense expense11 = new Expense(type.get(9), new BigDecimal(100), "Other", LocalDate.now(), budget);
        Expense expense12 = new Expense(type.get(8), new BigDecimal(100), "Home loan", LocalDate.now(), budget);
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

    public List<ExpenseType> fillExpenseType() {
        List<ExpenseType> expenseTypesList = new ArrayList<>();
        ExpenseType type0 = new ExpenseType("Children");
        ExpenseType type1 = new ExpenseType("Eat");
        ExpenseType type2 = new ExpenseType("Clothes");
        ExpenseType type3 = new ExpenseType("Transport");
        ExpenseType type4 = new ExpenseType("Entertainment");
        ExpenseType type5 = new ExpenseType("Health");
        ExpenseType type6 = new ExpenseType("Flat");
        ExpenseType type7 = new ExpenseType("Hygiene");
        ExpenseType type8 = new ExpenseType("Repayment");
        ExpenseType type9 = new ExpenseType("Other");
        expenseTypesList.add(type0);
        expenseTypesList.add(type1);
        expenseTypesList.add(type2);
        expenseTypesList.add(type3);
        expenseTypesList.add(type4);
        expenseTypesList.add(type5);
        expenseTypesList.add(type6);
        expenseTypesList.add(type7);
        expenseTypesList.add(type8);
        expenseTypesList.add(type9);
        return expenseTypesList;
    }
}
