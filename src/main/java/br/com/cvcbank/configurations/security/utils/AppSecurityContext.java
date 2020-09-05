package br.com.cvcbank.configurations.security.utils;

import br.com.cvcbank.configurations.security.exceptions.AuthenticationNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class AppSecurityContext {
    public Long getCurrentAccountId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (Long) authentication.getPrincipal();
        } catch (Exception e) {
            log.error("Account id is not set in the application context {0}", e);
            throw new AuthenticationNotFoundException();
        }
    }
}
