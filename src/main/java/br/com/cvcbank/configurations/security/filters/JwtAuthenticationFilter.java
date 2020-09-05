package br.com.cvcbank.configurations.security.filters;

import br.com.cvcbank.configurations.security.models.Credentials;
import br.com.cvcbank.configurations.security.models.UserDetailsImpl;
import br.com.cvcbank.configurations.security.services.JwtService;
import br.com.cvcbank.entities.Account;
import br.com.cvcbank.exceptions.ValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   JwtService jwtService, ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
//        setFilterProcessesUrl(AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) {
        Credentials credentials;

        try {
            credentials = objectMapper.readValue(request.getInputStream(), Credentials.class);
        } catch (IOException e) {
            throw new ValidationException("Invalid request.");
        }

        var token = new UsernamePasswordAuthenticationToken(credentials.getDocument(),
                credentials.getPassword(),
                Collections.emptyList());

        return this.authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) {

        Account account = ((UserDetailsImpl) authentication.getPrincipal()).getAccount();
        String token = jwtService.generate(account);
        response.addHeader("Authorization", String.format("Bearer %s", token));
        response.addHeader("access-control-expose-headers", "Authorization");
    }
}