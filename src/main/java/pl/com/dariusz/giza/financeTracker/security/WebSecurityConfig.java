package pl.com.dariusz.giza.financeTracker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.com.dariusz.giza.financeTracker.domain.jwt.JwtUtil;
import pl.com.dariusz.giza.financeTracker.service.user.UserDetailsServiceImpl;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private UserDetailsServiceImpl userDetailsService;

    private DataSource dataSource;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, UserDetailsServiceImpl userDetailsService, DataSource dataSource, JwtUtil jwtUtil) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.dataSource = dataSource;
        this.jwtUtil = jwtUtil;
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

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private static final String[] AUTH_WHITELIST = {
            "/authenticate",
            "/login",
            "/register",
            "/v2/api-docs",
            "/swagger-ui.html",
            "/spring-security/swagger-ui.html",
            "/spring-security/api/swagger-ui.html",
            "favicon.ico",
            "/swagger-resources",
            "/swagger-resources/**",
            "/webjars/**"

    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService))
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                .httpBasic().and()
                .csrf().disable()
                .cors();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
