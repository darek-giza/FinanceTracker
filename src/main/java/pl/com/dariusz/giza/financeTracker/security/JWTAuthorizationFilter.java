package pl.com.dariusz.giza.financeTracker.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pl.com.dariusz.giza.financeTracker.domain.jwt.JwtUtil;
import pl.com.dariusz.giza.financeTracker.service.user.UserDetailsServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public static final String SECRET = "java";

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authMenager, JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        super(authMenager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        String header = req.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) throws ExpiredJwtException {

        String reqToken = req.getHeader("Authorization");

        String usernameFromToken = null;
        String jwtToken = null;

        if (reqToken != null && reqToken.startsWith("Bearer ")) {

            jwtToken = reqToken.substring(7);
            try {
                usernameFromToken = jwtUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                throw new ExpiredJwtException(e.getHeader(), e.getClaims(), "JWT Token has expired");
            }

            if (usernameFromToken != null) {

                final UserDetails userDetails = userDetailsService.loadUserByUsername(usernameFromToken);

                if (jwtUtil.validateToken(jwtToken, userDetails)) {

                    return new UsernamePasswordAuthenticationToken(userDetails, null, new ArrayList<>());
                }
            }
        }
        return null;

    }
}
