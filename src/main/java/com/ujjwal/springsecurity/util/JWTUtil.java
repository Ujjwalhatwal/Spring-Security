package com.ujjwal.springsecurity.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {

    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour
    private final String SECRETE = "my-secret-key-that-is-long-engough-to-be-secure"; // Replace with your own secret key
    private final SecretKey key = Keys.hmacShaKeyFor(SECRETE.getBytes());

    public String generateToken(String username) {
        // Implement JWT token generation logic here
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        // Implement logic to extract username from the token
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails,String username) {
        // username and userDetails.getUsername() should be same and token should not be expired
       return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
 
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
