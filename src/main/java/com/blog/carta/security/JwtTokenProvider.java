package com.blog.carta.security;

import com.blog.carta.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-security}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = convertStringToKey(jwtSecret);
    }

    private Key convertStringToKey(String secret) {
        try {
            // Use SHA-256 to ensure the key is at least 256 bits long
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] hashedSecret = sha256.digest(secret.getBytes());
            return new SecretKeySpec(hashedSecret, 0, hashedSecret.length, "HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to initialize the JWT key", e);
        }
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(org.springframework.security.core.userdetails.User userDetails) {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        // Convert authorities to a list of role names
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Create claims with roles
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", roles);
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
        Long expirationTimeLong = Long.parseLong(String.valueOf(jwtExpirationInMs)); //in second
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
