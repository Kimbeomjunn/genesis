package com.example.genesis_be.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${JWT_SECRET}")
    // application.properties(또는 환경변수)에서 JWT_SECRET 값을 읽어와서 이 필드에 주입
    private String secret;

    private SecretKey key;

    @jakarta.annotation.PostConstruct
    // 이 클래스가 생성된 직후, 딱 한 번 자동으로 실행되는 메서드
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        // 주입받은 secret 값으로 실제 암호화 키를 만듦
        // (생성자 시점에는 @Value 값이 아직 준비 안 될 수 있어서, @PostConstruct로 분리)
    }

    private final long EXPIRATION_TIME = 1000 * 60 * 60;

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}