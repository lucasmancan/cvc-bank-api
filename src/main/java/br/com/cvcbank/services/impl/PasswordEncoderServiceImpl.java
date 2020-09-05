package br.com.cvcbank.services.impl;

import br.com.cvcbank.services.PasswordEncoderService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderServiceImpl implements PasswordEncoderService {

    org.springframework.security.crypto.password.PasswordEncoder delegate = new BCryptPasswordEncoder();

    public String encode(String rawPassword) {
        return delegate.encode(rawPassword);
    }

    @Override
    public boolean equals(String rawPassword, String hash) {
        return delegate.matches(rawPassword, hash);
    }
}

