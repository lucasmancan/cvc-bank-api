package br.com.app.services.impl;

import br.com.app.entities.Account;
import br.com.app.entities.AccountType;
import br.com.app.exceptions.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = AccountValidationServiceImpl.class)
class AccountValidationServiceImplTest {

    @Autowired
    AccountValidationServiceImpl accountValidationService;

    @Test
    void shouldDoNothingDealingWithValidIndividualAccount() {
        accountValidationService.validate(mockValidIndividualAccount());
    }

    @Test
    void shouldDoNothingDealingWithValidLegalAccount() {
        accountValidationService.validate(mockValidLegalAccount());
    }

    @Test
    void shouldThrowExceptionDealingWithInvalidLegalAccount() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            accountValidationService.validate(mockInvalidLegalAccount());
        });

        String expectedMessage = "Document is not valid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowExceptionDealingWithInvalidIndividualAccount() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            accountValidationService.validate(mockInvalidIndividualAccount());
        });

        String expectedMessage = "Document is not valid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowExceptionDealingWithInvalidPasswordFormat() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            accountValidationService.validate(mockInvalidIPasswordAccount());
        });

        String expectedMessage = "Password should have at least 6 chars.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    private Account mockInvalidIPasswordAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(new BigDecimal(1000));
        account.setType(AccountType.legal);
        account.setDocument("69198011000124");
        account.setNumber("123212");
        account.setPassword("123");
        return account;
    }

    private Account mockValidIndividualAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(new BigDecimal(1000));
        account.setType(AccountType.individual);
        account.setDocument("47434977019");
        account.setNumber("123212");
        account.setPassword("123123");
        return account;
    }

    private Account mockValidLegalAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(new BigDecimal(1000));
        account.setType(AccountType.legal);
        account.setDocument("69198011000124");
        account.setNumber("123212");
        account.setPassword("123123");
        return account;
    }

    private Account mockInvalidLegalAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(new BigDecimal(1000));
        account.setType(AccountType.legal);
        account.setDocument("69198011012312");
        account.setNumber("123212");
        account.setPassword("123123");
        return account;
    }

    private Account mockInvalidIndividualAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(new BigDecimal(1000));
        account.setType(AccountType.individual);
        account.setDocument("12312312");
        account.setNumber("123212");
        account.setPassword("123123");
        return account;
    }
}