package br.com.cvcbank.services.impl;

import br.com.cvcbank.dtos.CreateAccountDTO;
import br.com.cvcbank.entities.Account;
import br.com.cvcbank.entities.AccountType;
import br.com.cvcbank.exceptions.ValidationException;
import br.com.cvcbank.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = AccountValidationServiceImpl.class)
class AccountValidationServiceImplTest {

    @Autowired
    AccountValidationServiceImpl accountValidationService;

    @MockBean
    AccountRepository accountRepository;

    @Test
    void shouldDoNothingDealingWithValidIndividualAccount() {
        accountValidationService.validate(mockValidIndividualAccount());
    }

    @Test
    void shouldDoNothingDealingWithValidLegalAccount() {
        when(accountRepository.findByDocument(any())).thenReturn(Optional.empty());
        accountValidationService.validate(mockValidLegalAccount());
    }

    @Test
    void shouldThrowExceptionDealingWithInvalidLegalAccount() {
        when(accountRepository.findByDocument(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ValidationException.class, () -> {
            accountValidationService.validate(mockInvalidLegalAccount());
        });

        String expectedMessage = "Document is not valid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowExceptionDealingWithInvalidIndividualAccount() {
        when(accountRepository.findByDocument(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ValidationException.class, () -> {
            accountValidationService.validate(mockInvalidIndividualAccount());
        });

        String expectedMessage = "Document is not valid.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowExceptionWhenAccountAlreadyExists() {
        when(accountRepository.findByDocument(any())).thenReturn(Optional.of(new Account()));

        Exception exception = assertThrows(ValidationException.class, () -> {
            accountValidationService.validate(mockInvalidIPasswordAccount());
        });

        String expectedMessage = "Account already exists.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowExceptionDealingWithInvalidPasswordFormat() {
        when(accountRepository.findByDocument(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ValidationException.class, () -> {
            accountValidationService.validate(mockInvalidIPasswordAccount());
        });

        String expectedMessage = "Password should have at least 6 chars.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    private CreateAccountDTO mockInvalidIPasswordAccount() {
        CreateAccountDTO account = new CreateAccountDTO();
        account.setInitialBalance(new BigDecimal(1000));
        account.setType(AccountType.legal);
        account.setDocument("69198011000124");
        account.setPassword("123");
        return account;
    }

    private CreateAccountDTO mockValidIndividualAccount() {
        CreateAccountDTO account = new CreateAccountDTO();
        account.setInitialBalance(new BigDecimal(1000));
        account.setType(AccountType.individual);
        account.setDocument("47434977019");
        account.setPassword("123123");
        return account;
    }

    private CreateAccountDTO mockValidLegalAccount() {
        CreateAccountDTO account = new CreateAccountDTO();
        account.setInitialBalance(new BigDecimal(1000));
        account.setType(AccountType.legal);
        account.setDocument("69198011000124");
        account.setPassword("123123");
        return account;
    }

    private CreateAccountDTO mockInvalidLegalAccount() {
        CreateAccountDTO account = new CreateAccountDTO();
        account.setInitialBalance(new BigDecimal(1000));
        account.setType(AccountType.legal);
        account.setDocument("69198011012312");
        account.setPassword("123123");
        return account;
    }

    private CreateAccountDTO mockInvalidIndividualAccount() {
        CreateAccountDTO account = new CreateAccountDTO();
        account.setInitialBalance(new BigDecimal(1000));
        account.setType(AccountType.individual);
        account.setDocument("12312312");
        account.setPassword("123123");
        return account;
    }
}