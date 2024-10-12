package com.poapp.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    
    // Use a secure key for HS256
    private Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(SECRET_KEY)
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    // public Integer extractUserId(String token) {
    //     return Integer.parseInt(extractClaim(token, claims -> claims.get("id").toString())); // Convert to String and parse as Integer
    // }
    
    public String extractUserId(String token) {
        // Logic to extract userId
        Integer userId = Integer.parseInt(extractClaim(token, claims -> claims.get("username").toString()));
        return userId.toString(); // Convert to String and return
    }
    

    // public String generateToken(String username, String role, String roleName, Integer id) {
    //     Map<String, Object> claims = new HashMap<>();
    //     claims.put("role", role); // Add role to claims
    //     claims.put("role_name", roleName); // Add role_name to claims
    //     claims.put("id", id); // Add role_name to claims
    //     return createToken(claims, username);
    // }

    public String generateToken(String username, String role, String roleName, Integer id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role); // Add role to claims
        claims.put("role_name", roleName); // Add role_name to claims
        claims.put("id", id); // Add id to claims
        return createToken(claims, username); // Create and return the token
    }
    


    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiration
                .signWith(SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
