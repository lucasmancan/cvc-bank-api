package br.com.cvcbank.configurations.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface JwtService {
    String generate(br.com.cvcbank.entities.Account user);

    Jws<Claims> decode(String token);
}
