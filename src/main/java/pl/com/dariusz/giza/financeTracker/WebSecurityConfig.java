package pl.com.dariusz.giza.financeTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.com.dariusz.giza.financeTracker.service.user.UserDetailsServiceImpl;

import javax.sql.DataSource;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsServiceimpl;

    private DataSource dataSource;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsServiceimpl, DataSource dataSource) {
        this.userDetailsServiceimpl = userDetailsServiceimpl;
        this.dataSource = dataSource;
    }

    @Value("SELECT username,password,enabled FROM user WHERE username=?")
    private String getUsersQuery;

    @Value("SELECT u.username,r.role FROM user u INNER JOIN user_roles ur on(u.id=ur.user_id) INNER JOIN role r on(ur.role_id=r.id) WHERE u.username=?")
    private String rolesQuery;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(getUsersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/budget").hasRole("USER")
                .antMatchers("/api/users", "/api/users/*").permitAll()
                .antMatchers("/login").permitAll()
                .and()
                .formLogin()
                .and().httpBasic()
                .and().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
