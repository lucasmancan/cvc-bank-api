package br.com.cvcbank.configurations.security.services.impl;

import br.com.cvcbank.entities.Account;
import br.com.cvcbank.entities.AccountType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = {JwtServiceImpl.class})
class JwtServiceImplTest {

    @Autowired
    JwtServiceImpl jwtService;

    @Test
    void shouldGenerateAJWT() {
        String jwt = jwtService.generate(mockValidAccount());
        assertFalse(jwt.isEmpty());
    }

    @Test
    void shouldDecodeJWT() {
        String jwt = jwtService.generate(mockValidAccount());
        var claims = jwtService.decode(jwt);
        assertFalse(claims.getBody().isEmpty());
        assertTrue(claims.getBody().containsKey("accountId"));
        assertEquals(claims.getBody().get("accountId"), mockValidAccount().getId().intValue());

    }

    public Account mockValidAccount() {
        Account account = new Account();
        account.setDocument("88741439074");
        account.setType(AccountType.individual);
        account.setPassword("123456");
        account.setId(1L);
        return account;
    }
}