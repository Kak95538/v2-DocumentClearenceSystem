package com.example.payment.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

	private String SECRET = "mysecretkeymysecretkeymysecretkeymysecretkeymysecretkeymysecretkeymysecretkeymysecretkeymysecretkeymysecretkeymysecretkeymysecretkey";    private long expiration = 1000 * 60 * 60; // 1 hour
//    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // Generate Token
	public String generateToken(String email, String role) {
	    return Jwts.builder()
	            .setSubject(email)
	            .claim("role", role)
	            .setIssuedAt(new Date())
	            .setExpiration(new Date(System.currentTimeMillis() + expiration))
	            .signWith(getSignKey(), SignatureAlgorithm.HS256)
	            .compact();
	}

    // Extract Username
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Validate Token
    public boolean validateToken(String token) {
        try {
            getClaims(token); // verifies signature + expiry
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
    
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }
}