package pl.com.dariusz.giza.financeTracker.controllers.security;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {

    Authentication getAuthentication();


}

