package com.uow.FYP_23_S1_11.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.uow.FYP_23_S1_11.enums.ETokenType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {
    @Value("${refresh.jwtsecret}")
    private String refreshTokenSecret;
    @Value("${refresh.jwtexpirationms}")
    private int refreshTokenExpiry;
    @Value("${access.jwtsecret}")
    private String accessTokenSecret;
    @Value("${access.jwtexpirationms}")
    private int accessTokenExpiry;

    public String extractUserFromToken(ETokenType type, String token) {
        return extractClaims(type, token, Claims::getSubject);
    }

    public String generateToken(ETokenType type, UserDetails userDetails) {
        return generateToken(type, userDetails, new HashMap<>());
    }

    public String generateToken(ETokenType type, UserDetails userDetails, Map<String, Object> extraClaims) {
        int expiry = 0;
        if (type == ETokenType.ACCESS_TOKEN) {
            expiry = accessTokenExpiry;
        } else if (type == ETokenType.REFRESH_TOKEN) {
            expiry = refreshTokenExpiry;
        }

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiry))
                .signWith(getSignInKey(type), SignatureAlgorithm.HS256)
                .compact();
    }

    public <T> T extractClaims(ETokenType type, String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(type, token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(ETokenType type, String token, UserDetails userDetails) {
        final String validUsername = extractUserFromToken(type, token);
        return (validUsername.equals(userDetails.getUsername()) && !isTokenExpired(type, token));
    }

    private boolean isTokenExpired(ETokenType type, String token) {
        return extractExpiration(type, token).before(new Date());
    }

    private Date extractExpiration(ETokenType type, String token) {
        return extractClaims(type, token, Claims::getExpiration);
    }

    private Claims extractAllClaims(ETokenType type, String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey(type))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            System.out.println(ex);
            throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
        } catch (ExpiredJwtException ex) {
            throw ex;
        }

    }

    private Key getSignInKey(ETokenType type) {
        String secret = "";
        if (type == ETokenType.ACCESS_TOKEN) {
            secret = accessTokenSecret;
        } else if (type == ETokenType.REFRESH_TOKEN) {
            secret = refreshTokenSecret;
        }

        if (secret != null && !secret.trim().isEmpty()) {
            byte[] keyBytes = Decoders.BASE64.decode(secret);
            return Keys.hmacShaKeyFor(keyBytes);
        }
        return null;
    }

}
