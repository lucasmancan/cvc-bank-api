package br.com.cvcbank.services.impl;

import br.com.cvcbank.configurations.security.utils.AppSecurityContext;
import br.com.cvcbank.converters.AccountConverter;
import br.com.cvcbank.dtos.AccountDTO;
import br.com.cvcbank.dtos.CreateAccountDTO;
import br.com.cvcbank.entities.Account;
import br.com.cvcbank.entities.AccountType;
import br.com.cvcbank.exceptions.NotFoundException;
import br.com.cvcbank.repositories.AccountRepository;
import br.com.cvcbank.services.AccountValidationService;
import br.com.cvcbank.services.PasswordEncoderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {AccountServiceImpl.class})
class AccountServiceImplTest {

    @Autowired
    AccountServiceImpl accountService;

    @MockBean
    AccountRepository repository;

    @MockBean
    AccountConverter accountConverter;

    @MockBean
    AppSecurityContext appSecurityContext;

    @MockBean
    PasswordEncoderService passwordEncoderService;

    @MockBean
    AccountValidationService accountValidationService;

    private Account mockAccount() {
        Account account = new Account();
        account.setPassword("12312321");
        account.setNumber("112123");
        account.setDocument("12312312321");
        account.setType(AccountType.individual);
        account.setBalance(new BigDecimal(10000));
        account.setId(1L);
        account.setUpdatedAt(LocalDateTime.now());
        account.setCreatedAt(LocalDateTime.now());
        return account;
    }

    private CreateAccountDTO mockCreateAccountDTO() {
        CreateAccountDTO account = new CreateAccountDTO();
        account.setPassword("12312321");
        account.setDocument("12312312321");
        account.setType(AccountType.individual);
        return account;
    }

    private AccountDTO mockAccountDTO() {
        AccountDTO account = new AccountDTO();
        account.setPassword("12312321");
        account.setNumber("112123");
        account.setDocument("12312312321");
        account.setType(AccountType.individual);
        account.setBalance(new BigDecimal(10000));
        account.setId(1L);
        account.setUpdatedAt(LocalDateTime.now());
        return account;
    }

    @Test
    void shouldFindAccountById() {
        var account = mockAccount();

        when(repository.findById(any())).thenReturn(Optional.of(account));
        when(accountConverter.entityToDTO(any())).thenReturn(mockAccountDTO());

        AccountDTO accountDTO = accountService.findById(any());

        assertEquals(accountDTO.getDocument(), account.getDocument());
        assertEquals(accountDTO.getId(), account.getId());
        assertEquals(accountDTO.getType(), account.getType());
        assertEquals(accountDTO.getBalance(), account.getBalance());
        assertEquals(accountDTO.getNumber(), account.getNumber());
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFound() {
        var account = mockAccount();

        when(repository.findById(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            accountService.findById(any());
        });

        String expectedMessage = "Resource not found.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldCreateAccount() {
        var account = mockAccount();

        doNothing().when(accountValidationService).validate(any());
        when(accountConverter.dtoToEntity(any(CreateAccountDTO.class))).thenReturn(mockAccount());
        when(accountConverter.dtoToEntity(any(AccountDTO.class))).thenReturn(mockAccount());
        when(accountConverter.entityToDTO(any())).thenReturn(mockAccountDTO());
        when(passwordEncoderService.encode(any())).thenReturn("fake hashed password");

        AccountDTO accountDTO = accountService.create(mockCreateAccountDTO());

        assertEquals(accountDTO.getDocument(), account.getDocument());
        assertEquals(accountDTO.getId(), account.getId());
        assertEquals(accountDTO.getType(), account.getType());
        assertEquals(accountDTO.getBalance(), account.getBalance());
        assertEquals(accountDTO.getNumber(), account.getNumber());
    }

    @Test
    void shouldGenerateAValidAccountNumber() {

        String accountNumber = accountService.generateAccountNumber();

        assertNotNull(accountNumber);
        assertFalse(accountNumber.isEmpty());
        assertEquals(6, accountNumber.length());
    }
}