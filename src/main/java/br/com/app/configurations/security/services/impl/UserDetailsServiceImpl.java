package br.com.app.configurations.security.services.impl;

import br.com.app.configurations.security.models.UserDetailsImpl;
import br.com.app.entities.Account;
import br.com.app.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String s) {
        Account user = repository.findByDocument(s).orElseThrow(() -> new UsernameNotFoundException("Credenciais inválidas"));
        return new UserDetailsImpl(user);
    }
}
