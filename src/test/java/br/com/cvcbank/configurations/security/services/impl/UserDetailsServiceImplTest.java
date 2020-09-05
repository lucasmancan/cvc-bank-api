package br.com.cvcbank.configurations.security.services.impl;

import br.com.cvcbank.entities.Account;
import br.com.cvcbank.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {UserDetailsServiceImpl.class})
class UserDetailsServiceImplTest {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    AccountRepository accountRepository;

    @Test
    void shouldFindAccountByDocument() {

        var account = new Account();
        account.setDocument("123123123123");
        account.setId(1L);
        account.setNumber("123123");

        when(accountRepository.findByDocument(any())).thenReturn(Optional.of(account));

        UserDetails userDetails = userDetailsService.loadUserByUsername("123123123123");

        assertEquals(account.getNumber(), userDetails.getUsername());
    }

    @Test
    void shouldthrowExceptionWhenAccountIsNotFound() {

        var account = new Account();
        account.setDocument("123123123123");
        account.setId(1L);
        account.setNumber("123123");

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("123123123123");
        });

        String expectedMessage = "Invalid Credentials";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}