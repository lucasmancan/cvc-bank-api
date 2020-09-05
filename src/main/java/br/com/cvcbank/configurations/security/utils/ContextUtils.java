package br.com.cvcbank.configurations.security.utils;

import br.com.cvcbank.configurations.security.exceptions.AuthenticationNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ContextUtils {
    public static Long currentAccountId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (Long) authentication.getPrincipal();
        } catch (Exception e) {
            throw new AuthenticationNotFoundException();
        }
    }
}
