package com.example.genesis_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    // 회원가입 요청 시 클라이언트(React)가 보낼 데이터를 담는 클래스

    private String username;
    // 사용자가 입력한 아이디

    private String password;
    // 사용자가 입력한 비밀번호 (암호화되기 전의 평문 상태로 잠깐 담겨서 넘어옴)
}