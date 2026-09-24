package com.example.genesis_be.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
// 이 클래스도 Spring이 관리하는 부품(빈)으로 등록 (다른 곳에서 자동 주입받아 쓸 수 있게)

public class JwtUtil {

    private final SecretKey key = Keys.hmacShaKeyFor(
            "this-is-a-very-long-secret-key-for-jwt-signing-1234567890".getBytes()
    );
    // JWT를 암호화(서명)할 때 쓰는 비밀 열쇠
    // 이 문자열은 32byte(256bit) 이상이어야 함 (짧으면 에러 남)
    // 실제 서비스에서는 이런 값을 코드에 직접 쓰지 않고 환경변수로 분리해야 함 (Day 5에서 다룰 보안 주제)

    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간
    // 토큰의 유효 시간을 밀리초 단위로 지정
    // 1000(1초) * 60(1분) * 60(1시간) = 1시간 동안 유효한 토큰

    public String generateToken(String username) {
        // username을 받아서 JWT 토큰 문자열을 만들어내는 메서드
        return Jwts.builder()
                .subject(username)
                // 토큰 안에 "이 토큰은 누구의 것인지" 정보를 담음
                .issuedAt(new Date())
                // 토큰이 발급된 시각 기록
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                // 토큰의 만료 시각 (현재 시각 + 1시간)
                .signWith(key)
                // 아까 만든 비밀 열쇠로 이 토큰에 서명(위조 방지)
                .compact();
        // 최종적으로 문자열 형태의 토큰을 생성
    }

    public String extractUsername(String token) {
        // 토큰을 받아서, 그 안에 담긴 username을 꺼내는 메서드
        return Jwts.parser()
                .verifyWith(key)
                // 서명이 우리가 발급한 게 맞는지 검증
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        // 토큰 안에 담아뒀던 subject(username) 값을 꺼냄
    }

    public boolean isTokenValid(String token) {
        // 토큰이 아직 유효한지(위조 안 됐고, 만료 안 됐는지) 확인하는 메서드
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
            // 여기까지 에러 없이 통과하면 유효한 토큰
        } catch (Exception e) {
            return false;
            // 서명이 이상하거나 만료됐으면 예외가 발생하고, false 반환
        }
    }
}