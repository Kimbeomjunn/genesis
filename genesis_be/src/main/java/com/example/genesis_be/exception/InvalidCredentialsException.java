package com.example.genesis_be.exception;

public class InvalidCredentialsException extends RuntimeException {
    // 로그인 실패(아이디 없음, 비밀번호 틀림) 상황을 위한 전용 예외 타입

    public InvalidCredentialsException(String message) {
        super(message);
    }
}