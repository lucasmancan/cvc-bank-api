package br.com.cvcbank.converters.impl;

import br.com.cvcbank.dtos.AccountDTO;
import br.com.cvcbank.dtos.CreateAccountDTO;
import br.com.cvcbank.entities.Account;
import br.com.cvcbank.entities.AccountType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ContextConfiguration(classes = {AccountConverterImpl.class})
class AccountConverterImplTest {

    @Autowired
    AccountConverterImpl accountConverter;

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

    private CreateAccountDTO mockCreateAccountDTO() {
        CreateAccountDTO account = new CreateAccountDTO();
        account.setPassword("12312321");
        account.setDocument("12312312321");
        account.setType(AccountType.individual);
        return account;
    }

    @Test
    void shouldConvertDtoToEntity() {
        var dto = mockAccountDTO();
        Account account = accountConverter.dtoToEntity(dto);

        assertEquals(account.getPassword(), dto.getPassword());
        assertEquals(account.getDocument(), dto.getDocument());
        assertEquals(account.getId(), dto.getId());
        assertEquals(account.getType(), dto.getType());
        assertEquals(account.getUpdatedAt(), dto.getUpdatedAt());
        assertEquals(account.getNumber(), dto.getNumber());
    }

    @Test
    void shouldConvertCreateAccountDtoToEntity() {
        var dto = mockCreateAccountDTO();
        Account account = accountConverter.dtoToEntity(dto);

        assertEquals(account.getPassword(), dto.getPassword());
        assertEquals(account.getDocument(), dto.getDocument());
        assertEquals(account.getType(), dto.getType());
    }

    @Test
    void shouldConvertEntityToDTO() {
        var account = mockAccount();
        AccountDTO dto = accountConverter.entityToDTO(account);

        assertNull(dto.getPassword());
        assertEquals(dto.getDocument(), account.getDocument());
        assertEquals(dto.getId(), account.getId());
        assertEquals(dto.getType(), account.getType());
        assertEquals(dto.getUpdatedAt(), account.getUpdatedAt());
        assertEquals(dto.getBalance(), account.getBalance());
        assertEquals(dto.getNumber(), account.getNumber());
    }
}