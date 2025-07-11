package com.store_api.store_api.service;

import com.store_api.store_api.config.JwtConfig;
import com.store_api.store_api.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {
    private final JwtConfig jwtConfig;

    public Jwt generateAccessToken(User user) {
        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }

    public Jwt generateRefreshToken(User user) {
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }

    private Jwt generateToken(User user, long tokenExpiration) {
        Claims claims = Jwts.claims()
                .subject(user.getId().toString())
                .add("email", user.getEmail())
                .add("name", user.getName())
                .add("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .build();

        return new Jwt(claims, jwtConfig.getSecretKey());

//        return Jwts.builder()
//                .subject(user.getId().toString())
//                .claim("email", user.getEmail())
//                .claim("name", user.getName())
//                .claim("role", user.getRole())
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
//                .signWith(jwtConfig.getSecretKey())
//                .compact();
    }

//    public boolean validateToken(String token) {
//        try {
//            Claims claims = getClaims(token);
//
//            return claims.getExpiration().after(new Date());
//
//        } catch (JwtException e) {
//            return true;
//        }
//    }


    public Jwt parseToken(String token) {
        try {
            Claims claims = getClaims(token);
            return new Jwt(claims, jwtConfig.getSecretKey());
        } catch (JwtException e) {
            return null;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

//    public Long getUserIdFromToken(String token) {
//        return Long.valueOf(getClaims(token).getSubject());
//    }

//    public Role getRoleFromToken(String token) {
//        return Role.valueOf(getClaims(token).get("role", String.class));
//    }
}
