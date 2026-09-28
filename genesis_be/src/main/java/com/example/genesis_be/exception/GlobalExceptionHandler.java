package com.example.genesis_be.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
// 이 클래스가 "모든 컨트롤러에서 발생하는 예외를 가로채서 처리하는 곳"임을 표시
// (@RestController + @ControllerAdvice가 합쳐진 어노테이션이라, JSON 응답을 바로 반환 가능)

public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateUserException.class)
    // "DuplicateUserException이 발생하면, 이 메서드가 대신 처리해라"는 뜻
    public ResponseEntity<Map<String, Object>> handleDuplicateUser(DuplicateUserException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                // 409 Conflict: "요청은 이해했지만, 현재 상태와 충돌한다"는 의미의 상태 코드
                // (이미 존재하는 아이디로 가입하려는 상황에 딱 맞는 코드)
                .body(Map.of(
                        "status", 409,
                        "message", e.getMessage()
                        // e.getMessage()는 우리가 예외를 던질 때 넣었던 그 문자열
                        // (예: "이미 존재하는 아이디입니다.")
                ));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCredentials(InvalidCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                // 401 Unauthorized: "인증에 실패했다"는 의미
                .body(Map.of(
                        "status", 401,
                        "message", e.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    // 위에서 처리 안 한, 그 외 모든 예외를 잡는 "안전망"
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                // 500: 우리가 예상 못 한 에러는 일단 500으로 처리
                .body(Map.of(
                        "status", 500,
                        "message", "서버 내부 오류가 발생했습니다."
                        // e.getMessage()를 그대로 노출하지 않는 이유: 예상 못 한 에러 메시지에는
                        // DB 구조나 내부 로직 같은 민감한 정보가 담겨 있을 수 있어서, 안전하게 일반 메시지로 감춤
                ));
    }
}