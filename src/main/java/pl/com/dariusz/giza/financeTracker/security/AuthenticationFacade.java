package pl.com.dariusz.giza.financeTracker.security;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {

    Authentication getAuthentication();


}

