package com.example.genesis_be.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
// Spring이 관리하는 부품으로 등록

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    // OncePerRequestFilter: "요청 하나당 딱 한 번만 실행되는 필터"를 만들 때 상속하는 클래스

    private final JwtUtil jwtUtil;
    // Day 2에서 만든 JwtUtil을 가져와서 사용

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 모든 요청마다 자동으로 실행되는 메서드

        String authHeader = request.getHeader("Authorization");
        // 요청의 헤더에서 "Authorization"이라는 이름의 값을 꺼냄
        // React에서 보낼 때 "Bearer eyJhbGci..." 형태로 담아서 보낼 예정

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // 헤더가 있고, "Bearer "로 시작하는 경우만 처리
            String token = authHeader.substring(7);
            // "Bearer " 뒤의 실제 토큰 부분만 잘라냄 ("Bearer "는 7글자)

            if (jwtUtil.isTokenValid(token)) {
                // JwtUtil로 토큰이 유효한지 검증
                String username = jwtUtil.extractUsername(token);
                // 유효하면, 토큰 안에서 username을 꺼냄

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(username, null, null);
                // "이 사람은 인증된 사용자다"라는 정보를 담은 객체 생성

                SecurityContextHolder.getContext().setAuthentication(authToken);
                // Spring Security에게 "지금 이 요청은, 이 username으로 로그인된 상태다"라고 등록
                // 이렇게 해야 컨트롤러에서 "지금 로그인한 사용자가 누구인지" 알 수 있게 됨
            }
        }

        filterChain.doFilter(request, response);
        // 다음 필터(또는 최종 컨트롤러)로 요청을 넘김 (이 줄이 없으면 요청이 멈춰버림)
    }
}