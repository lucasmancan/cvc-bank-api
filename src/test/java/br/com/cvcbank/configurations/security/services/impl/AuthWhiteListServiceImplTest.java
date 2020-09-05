package br.com.cvcbank.configurations.security.services.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AuthWhiteListServiceImplTest {

    @Autowired
    AuthWhiteListServiceImpl authWhiteListService;

    @Test
    void shouldGetAllWhiteListUrls() {
        String[] urls = authWhiteListService.getAll();
        assertTrue(urls.length > 0);
    }
}