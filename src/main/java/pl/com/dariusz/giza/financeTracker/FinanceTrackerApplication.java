package pl.com.dariusz.giza.financeTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import pl.com.dariusz.giza.financeTracker.security.JwtFilter;

import java.util.Collections;

@SpringBootApplication
public class FinanceTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceTrackerApplication.class, args);
	}


	@Bean
	public FilterRegistrationBean FilterRegistrationBean(){

		FilterRegistrationBean filterRegistrationBean= new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new JwtFilter());
		filterRegistrationBean.setUrlPatterns(Collections.singleton("/api/incomes"));

		return filterRegistrationBean;


	}
}
