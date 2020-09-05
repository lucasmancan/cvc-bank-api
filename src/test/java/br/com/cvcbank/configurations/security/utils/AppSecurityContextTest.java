package br.com.cvcbank.configurations.security.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {AppSecurityContext.class})
class AppSecurityContextTest {

    @Autowired
    AppSecurityContext appSecurityContext;

    @Test
    void shouldGetActiveAccountId() {
        long accountId = 1L;
        Authentication authentication = mock(Authentication.class);
        org.springframework.security.core.context.SecurityContext securityContext = mock(org.springframework.security.core.context.SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(accountId);

        long currentAccount = appSecurityContext.getCurrentAccountId();

        assertEquals(currentAccount, accountId);
    }
}