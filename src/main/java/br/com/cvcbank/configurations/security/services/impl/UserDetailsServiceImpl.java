package br.com.cvcbank.configurations.security.services.impl;

import br.com.cvcbank.configurations.security.models.UserDetailsImpl;
import br.com.cvcbank.entities.Account;
import br.com.cvcbank.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@AllArgsConstructor

public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String s) {
        Account user = repository.findByDocument(s).orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));
        return new UserDetailsImpl(user);
    }
}
