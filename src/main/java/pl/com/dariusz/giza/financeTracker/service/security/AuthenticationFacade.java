package pl.com.dariusz.giza.financeTracker.service.security;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {

    Authentication getAuthentication();


}

