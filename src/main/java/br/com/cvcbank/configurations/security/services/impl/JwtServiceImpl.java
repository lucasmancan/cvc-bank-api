package br.com.cvcbank.configurations.security.services.impl;


import br.com.cvcbank.configurations.security.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Component("jwtService")
public class JwtServiceImpl implements JwtService {

    @Value("jwt.secret")
    private String secret;

    public String generate(br.com.cvcbank.entities.Account account) {
        Date expirationDate = Date.from(LocalDateTime.now().plusSeconds(300)
                .atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, secret)
                .setSubject(account.getNumber())
                .claim("accountId", account.getId())
                .setExpiration(expirationDate)
                .compact();
    }

    public Jws<Claims> decode(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token.replace("Bearer ", ""));
    }
}
