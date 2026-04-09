package com.example.gateway.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

import java.util.Date;

public class JwtUtil {

	private String SECRET = "mysecretkeymysecretkeymysecretkeymysecretkeymysecretkeymysecretkeymysecretkeymysecretkeymysecretkeymysecretkeymysecretkeymysecretkey";    private long expiration = 1000 * 60 * 60; // 1 hour

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token); // handles signature + expiry
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
    
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }
}
