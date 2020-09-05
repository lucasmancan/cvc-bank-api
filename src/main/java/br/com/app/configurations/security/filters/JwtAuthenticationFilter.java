package br.com.app.configurations.security.filters;

import br.com.app.configurations.security.models.Credentials;
import br.com.app.configurations.security.models.UserDetailsImpl;
import br.com.app.configurations.security.services.JwtService;
import br.com.app.entities.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public static final String AUTH_LOGIN_URL = "/auth";

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtServiceImpl;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtServiceImpl) {
        this.jwtServiceImpl = jwtServiceImpl;
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Credentials credentials = new ObjectMapper().readValue(request.getInputStream(), Credentials.class);

            try {
                final var token = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword(), Collections.emptyList());

                return this.authenticationManager.authenticate(token);
            } catch (BadCredentialsException usernameNotFoundException) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Credenciais inválidas");
                return null;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) {

        final Account user = ((UserDetailsImpl) authentication.getPrincipal()).getAccount();
        final String token = jwtServiceImpl.generate(user);

        response.addHeader("Authorization", String.format("Bearer %s", token));
        response.addHeader("access-control-expose-headers", "Authorization");
    }
}