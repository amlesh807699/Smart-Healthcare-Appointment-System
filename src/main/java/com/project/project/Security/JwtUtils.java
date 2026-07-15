package com.project.project.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secret}")
    private String secretKey;

    private Key getKey() {
        return Keys.hmacShaKeyFor(
                secretKey.getBytes(StandardCharsets.UTF_8)
        );
    }

    // Access Token
    public String accessToken(String role, String email) {

        return generateToken(
                role,
                email,
                "access",
                15 * 60 * 1000
        );
    }

    // Refresh Token
    public String refreshToken(String role, String email) {

        return generateToken(
                role,
                email,
                "refresh",
                7L * 24 * 60 * 60 * 1000
        );
    }

    private String generateToken(
            String role,
            String email,
            String type,
            long expiration
    ) {

        Date now = new Date();

        return Jwts.builder()
                .setSubject(email)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(
                        new Date(now.getTime() + expiration)
                )
                .claim("role", role)
                .claim("typ", type)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims getClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getEmail(String token) {
        return getClaims(token).getSubject();
    }

    public String getRole(String token) {
        return getClaims(token)
                .get("role", String.class);
    }

    public String extractType(String token) {
        return getClaims(token)
                .get("typ", String.class);
    }

    public boolean validate(String token) {

        try {

            getClaims(token);

            return true;

        } catch (JwtException | IllegalArgumentException e) {

            return false;
        }
    }
}