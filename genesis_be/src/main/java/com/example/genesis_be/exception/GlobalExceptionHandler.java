package com.example.genesis_be.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import io.sentry.Sentry;

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
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception e) {
        Sentry.captureException(e);
        // 예상 못 한 에러를 Sentry로 전송 (스택트레이스 포함)
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("status", 500, "message", "서버 내부 오류가 발생했습니다."));
    }
}