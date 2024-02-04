package com.example.groceries.security.service.impl;

import java.util.*;
import java.util.function.Function;
import java.security.Key;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.groceries.security.entity.User;
import com.example.groceries.security.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Service
public class JwtServiceImpl implements JwtService{
    
    public String generateUserToken(UserDetails userDetails){
        return Jwts
        .builder()
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) /// Expiration after one hour
        .signWith(setSigninKey(), SignatureAlgorithm.HS256)
        .compact();
    }

    public String generateResetPasswordToken(UserDetails userDetails){
        return Jwts
        .builder()
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5)) /// Expiration after 5 minutes
        .signWith(setSigninKey(), SignatureAlgorithm.HS256)
        .compact();
    }

    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts
        .builder().setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 604800000))
        .signWith(setSigninKey(), SignatureAlgorithm.HS256)
        .compact();
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Key setSigninKey(){
        byte[] data = Decoders.BASE64.decode("413F4428472B6250655368566D5970337336763979244226452948404D6351");
        return Keys.hmacShaKeyFor(data);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUserName(token);
        return(username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }


    private Claims extractAllClaims(String token){
        try {
            return Jwts
            .parserBuilder()
            .setSigningKey(setSigninKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token has expired");
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            throw new RuntimeException("Invalid token");
        }
    }

}
