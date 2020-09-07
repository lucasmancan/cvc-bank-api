package br.com.cvcbank.services.impl;

import br.com.cvcbank.configurations.security.utils.AppSecurityContext;
import br.com.cvcbank.dtos.BalanceUpdate;
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
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {BalanceServiceImpl.class})
class BalanceServiceImplTest {

    @Autowired
    BalanceServiceImpl transferValidatonService;

    @MockBean
    AppSecurityContext appSecurityContext;

    @MockBean
    AccountRepository accountRepository;


    private Account mockAccount() {
        Account account = new Account();
        account.setPassword("12312321");
        account.setNumber("112123");
        account.setDocument("12312312321");
        account.setType(AccountType.individual);
        account.setBalance(new BigDecimal(10000));
        account.setId(1L);
        account.setUpdatedAt(LocalDateTime.now());
        return account;
    }

    public BalanceUpdate mockValidBalanceTransfer() {
        return new BalanceUpdate(2L, 1L, new BigDecimal("1000"), new BigDecimal("1100"));
    }

    public BalanceUpdate mockInvalidBalanceTransferToTheSameAccount() {
        return new BalanceUpdate(1L, 1L, new BigDecimal("1000"), new BigDecimal("1100"));
    }

    @Test
    void shouldDoNothingWithAValidAccountBalance() {

        when(accountRepository.findById(any())).thenReturn(Optional.of(mockAccount()));
        when(appSecurityContext.getCurrentAccountId()).thenReturn(1L);

        transferValidatonService.updateBalance(mockValidBalanceTransfer());
    }

    @Test
    void shouldThrowExceptionWhenTryingToMakeTransferToTheSameAccount() {
        var transfer = mockInvalidBalanceTransferToTheSameAccount();

        when(accountRepository.findById(any())).thenReturn(Optional.of(mockAccount()));
        when(appSecurityContext.getCurrentAccountId()).thenReturn(1L);

        Exception exception = assertThrows(ValidationException.class, () -> {
            transferValidatonService.updateBalance(transfer);
        });

        String expectedMessage = "Transfer to the same account is not allowed.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowExceptionWhenTryingToMakeTransferToAccountWithInvalidBallance() {
        var account = mockAccount();
        account.setBalance(new BigDecimal(1));

        when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        when(appSecurityContext.getCurrentAccountId()).thenReturn(1L);

        Exception exception = assertThrows(ValidationException.class, () -> {
            transferValidatonService.updateBalance(mockValidBalanceTransfer());
        });

        String expectedMessage = "Account does not have enough balance to complete the transaction";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}