package de.becker.household.adapters.out.encoder;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtService {

    private final SecretKey secretKey = Jwts.SIG.HS512.key()
                                                      .build();

    public String generateToken(final Map<String, Object> claims,
                                final String subject) {
        return Jwts.builder()
                   .claims(claims)
                   .subject(subject)
                   .issuedAt(Date.from(Instant.now()))
                   .expiration(Date.from(Instant.now()
                                                .plus(Duration.ofDays(1))))
                   .signWith(secretKey)
                   .compact();
    }

    public Claims parseToken(final String token) {
        return Jwts.parser()
                   .verifyWith(secretKey)
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();
    }

    public boolean validateToken(final String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }
}
