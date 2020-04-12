package pl.com.dariusz.giza.financeTracker.controllers.utils;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.utils.ChartWeekly;
import pl.com.dariusz.giza.financeTracker.domain.budgets.utils.ChartYearly;
import pl.com.dariusz.giza.financeTracker.security.AuthenticationFacade;
import pl.com.dariusz.giza.financeTracker.service.user.UserService;
import pl.com.dariusz.giza.financeTracker.service.utils.ChartWeeklyService;
import pl.com.dariusz.giza.financeTracker.service.utils.ChartYearlyService;

import java.util.List;

@RestController
@CrossOrigin
public class ChartController {

    private ChartYearlyService chartYearlyService;
    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;
    private ChartWeeklyService chartWeeklyService;

    public ChartController(ChartYearlyService chartYearlyService, AuthenticationFacade authenticationFacade,
                           UserService userService, ChartWeeklyService chartWeekly) {
        this.chartYearlyService = chartYearlyService;
        this.authenticationFacade = authenticationFacade;
        this.userService = userService;
        this.chartWeeklyService = chartWeekly;
    }

    @GetMapping(path = "/api/chartYearly")
    public List<ChartYearly> fillChartYearly() {
        return chartYearlyService.generateYearlyChart(getBudget().getId());
    }

    @GetMapping(path = "/api/chartWeekly")
    public List<ChartWeekly> fillChartWeekly() {
        return chartWeeklyService.generateChartWeekly(getBudget().getId(), 6);
    }

    public Budget getBudget() {
        final String userName = authenticationFacade.getAuthentication().getName();
        return userService.findUserByUsername(userName).getBudget();
    }
}
