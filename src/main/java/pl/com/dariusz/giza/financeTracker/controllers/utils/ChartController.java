package pl.com.dariusz.giza.financeTracker.controllers.utils;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.dariusz.giza.financeTracker.domain.budgets.Budget;
import pl.com.dariusz.giza.financeTracker.domain.budgets.utils.ChartYearly;
import pl.com.dariusz.giza.financeTracker.security.AuthenticationFacade;
import pl.com.dariusz.giza.financeTracker.service.user.UserService;
import pl.com.dariusz.giza.financeTracker.service.utils.ChartYearlyService;

import java.util.Map;

@RestController
@CrossOrigin
public class ChartController {

    private ChartYearlyService chartYearlyService;
    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;

    public ChartController(ChartYearlyService chartYearlyService, AuthenticationFacade authenticationFacade,
                           UserService userService) {
        this.chartYearlyService = chartYearlyService;
        this.authenticationFacade = authenticationFacade;
        this.userService = userService;
    }

    @GetMapping(path = "/api/chartYearly")
    public Map<Integer, ChartYearly> fillChartYearly() {
        return chartYearlyService.generateChart(getBudget(), "2020");
    }

    public Budget getBudget() {
        final String userName = authenticationFacade.getAuthentication().getName();
        return userService.findUserByUsername(userName).getBudget();
    }
}
