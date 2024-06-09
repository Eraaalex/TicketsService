package com.hse.software.construction.ticketsapp.authorization.service;


import com.hse.software.construction.ticketsapp.authorization.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    Long jwtLifetimeMs;

    private static final String ID = "id";
    private static final String EMAIL = "email";

    public String generateToken(User userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ID, userDetails.getId());
        claims.put(EMAIL, userDetails.getEmail());
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + jwtLifetimeMs);

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            return !isExpired(token) && claims.getSubject().equals(userDetails.getUsername());
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean isExpired(String token) {
        return getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
