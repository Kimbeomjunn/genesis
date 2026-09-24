package com.example.genesis_be.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// 이 클래스가 "설정 정보를 담고 있는 클래스"임을 Spring에게 알림

@EnableWebSecurity
// Spring Security 기능을 활성화

public class SecurityConfig {

    @Bean
    // 이 메서드가 반환하는 객체를 Spring이 관리하는 "부품(빈)"으로 등록
    // 다른 곳에서 이 부품이 필요할 때 Spring이 자동으로 가져다 꽂아줌 (의존성 주입)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // 비밀번호를 BCrypt 방식으로 암호화하는 도구를 하나의 부품으로 등록
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // CSRF(Cross-Site Request Forgery) 보호 기능 끄기
                // 원래는 보안을 위해 켜져 있는데, 우리는 JWT 방식(토큰 기반)을 쓸 거라 필요 없음

                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/health", "/api/auth/**").permitAll()
                                // "/api/health"와 "/api/auth/로 시작하는 모든 경로"는 로그인 없이도 접근 허용
                                // (회원가입/로그인 API 자체는 당연히 로그인 안 해도 접근할 수 있어야 하니까)
                                .anyRequest().authenticated()
                        // 그 외 나머지 모든 요청은 반드시 로그인(인증)이 되어 있어야 접근 가능
                );

        return http.build();
        // 지금까지 설정한 내용을 최종적으로 만들어서 반환
    }
}