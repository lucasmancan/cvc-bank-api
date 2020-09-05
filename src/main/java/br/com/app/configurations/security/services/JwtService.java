package br.com.app.configurations.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface JwtService {
    String generate(br.com.app.entities.Account user);

    Jws<Claims> decode(String token);
}
