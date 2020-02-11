package pl.com.dariusz.giza.financeTracker.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtFilter implements javax.servlet.Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        final String header = httpServletRequest.getHeader("Authorization");


        if (httpServletRequest == null || !header.startsWith("Bearer ")) {
            throw new ServletException("Wrong or empty header");
        } else {
            try {
                final String token = header.substring(7);
                Claims claims = Jwts.parser().setSigningKey("user").parseClaimsJws(token).getBody();
                servletRequest.setAttribute("claims", claims);
            } catch (Exception e) {
                throw new ServletException("Wrong key");
            }

            filterChain.doFilter(servletRequest, servletResponse);
        }

    }
}

