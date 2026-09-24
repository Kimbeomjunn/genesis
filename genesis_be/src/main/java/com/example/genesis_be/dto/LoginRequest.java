package com.example.genesis_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    // 로그인 요청 시 클라이언트가 보낼 데이터를 담는 클래스

    private String username;
    private String password;
    // 회원가입과 필드는 똑같지만, "로그인용"이라는 의미로 별도 클래스로 분리
    // (나중에 각각 다른 용도로 발전할 수 있어서 미리 구분해두는 게 관례)
}