package br.com.cvcbank.services.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = {PasswordEncoderServiceImpl.class})
class PasswordEncoderServiceImplTest {

    @Autowired
    PasswordEncoderServiceImpl passwordEncoderService;

    public String mockRawPassword() {
        return "123";
    }

    @Test
    void shouldEncodePassword() {
        String rawPassword = mockRawPassword();
        String hash = passwordEncoderService.encode(rawPassword);
        assertNotNull(hash);
        assertFalse(hash.isEmpty());
        assertNotEquals(rawPassword, hash);
    }

    @Test
    void testEquals() {
        String rawPassword = mockRawPassword();
        String hash = passwordEncoderService.encode(rawPassword);
        assertTrue(passwordEncoderService.equals(rawPassword, hash));
    }
}