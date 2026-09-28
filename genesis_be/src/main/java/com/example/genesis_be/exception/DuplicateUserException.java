package com.example.genesis_be.exception;

public class DuplicateUserException extends RuntimeException {
    // RuntimeException을 상속받아서, "이미 존재하는 아이디" 상황만을 위한 전용 예외 타입을 만듦

    public DuplicateUserException(String message) {
        super(message);
        // 부모 클래스(RuntimeException)의 생성자에 메시지를 그대로 전달
    }
}