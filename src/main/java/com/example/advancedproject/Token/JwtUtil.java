package com.example.advancedproject.Token;

import com.example.advancedproject.Entities.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtil {
    private String SECRET_KEY = "SE2016";
    public String generateToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        return  createToken(claims, user.getEmail());
    }
    public String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt
                        (new Date(System.currentTimeMillis())).
                setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)).
                signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
}