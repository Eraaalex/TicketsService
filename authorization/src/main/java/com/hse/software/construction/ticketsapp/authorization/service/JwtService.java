package com.hse.software.construction.ticketsapp.authorization.service;


import com.hse.software.construction.ticketsapp.authorization.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
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
        Claims claims = getAllClaimsFromToken(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    public Boolean validateToken(String token, UserDetails user) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            return !isExpired(token) && claims.getSubject().equals(user.getUsername());
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getAllClaimsFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        try {
            return Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            return null;
        }
    }


    public Boolean isExpired(String token) {
        Claims claims = getAllClaimsFromToken(token);
        if (claims == null) {
            return false;
        }
        return getAllClaimsFromToken(token).getExpiration().before(new Date());
    }


}
