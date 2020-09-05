package br.com.cvcbank.configurations.security.filters;

import br.com.cvcbank.configurations.security.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private final JwtService jwtService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  JwtService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, java.io.IOException {

        final String tokenHeader = request.getHeader(TOKEN_HEADER);
        if (tokenHeader != null &&
                tokenHeader.startsWith(TOKEN_PREFIX)) {
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
        }
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(final String token) {
        Jws<Claims> parsedToken = jwtService.decode(token);
        long accountId = Long.parseLong(parsedToken.getBody().get("accountId").toString());
        return new UsernamePasswordAuthenticationToken(accountId, null, Collections.emptyList());
    }
}
