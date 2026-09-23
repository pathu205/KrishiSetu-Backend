package com.example.demo.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey secretKey = Keys.hmacShaKeyFor(
            "my-super-secret-key-for-krishisetu-jwt-2026".getBytes()
    );

    public String generateToken(String email) {

        Date now = new Date();

        Date expiration = new Date(
                now.getTime() + 1000 * 60 * 60
        );

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
        }

        public String extractEmail(String token) {

            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        }

        public boolean isTokenValid(String token) {

            try {
                Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(token);

                return true;

            } catch (Exception e) {
                return false;
            }
        }
}
