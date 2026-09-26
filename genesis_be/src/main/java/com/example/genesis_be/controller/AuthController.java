package com.example.genesis_be.controller;

import com.example.genesis_be.dto.SignupRequest;
import com.example.genesis_be.entity.User;
import com.example.genesis_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.genesis_be.config.JwtUtil;
import com.example.genesis_be.dto.LoginRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
// 이 컨트롤러의 모든 API 주소 앞에 공통으로 "/api/auth"가 붙음
// 예: 아래 signup은 실제로 "/api/auth/signup"이 됨

@RequiredArgsConstructor
// UserService를 자동으로 주입받기 위한 생성자를 Lombok이 자동 생성

@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:8081",
        "https://genesis-ie4awf5gl-bj-e691.vercel.app",
        "https://baknu.com",
        "https://www.baknu.com"
})
public class AuthController {

    private final UserService userService;
    // 아까 만든 UserService를 가져와서 사용
    private final JwtUtil jwtUtil;


    @PostMapping("/signup")
    // POST 방식으로 "/api/auth/signup" 요청이 오면 이 메서드 실행
    public User signup(@RequestBody SignupRequest request) {
        // @RequestBody: React가 JSON으로 보낸 데이터를 SignupRequest 객체로 자동 변환
        return userService.signup(request.getUsername(), request.getPassword());
        // UserService에 값을 넘겨서 실제 회원가입 처리, 결과(저장된 User)를 반환
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        userService.login(request.getUsername(), request.getPassword());
        // 아이디/비밀번호가 맞는지 검증 (틀리면 여기서 에러 발생하고 아래 코드는 실행 안 됨)

        String token = jwtUtil.generateToken(request.getUsername());
        // 검증 통과했으니, 그 아이디로 JWT 토큰 발급

        return Map.of("token", token);
        // {"token": "발급된토큰값"} 형태로 응답
    }

}
