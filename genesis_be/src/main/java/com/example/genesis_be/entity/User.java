package com.example.genesis_be.entity;

import jakarta.persistence.*;
// JPA(자바 진영의 DB 매핑 도구) 관련 어노테이션들을 가져옴

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
// Lombok — 반복되는 getter/setter, 생성자 코드를 자동으로 만들어주는 라이브러리

@Entity
// 이 클래스가 데이터베이스의 "테이블"과 매핑된다는 것을 표시
// (spring.jpa.hibernate.ddl-auto=update 설정 덕분에, 이 클래스 기준으로 테이블이 자동 생성/반영됨)

@Table(name = "users")
// 실제 테이블 이름을 "users"로 지정
// (테이블명을 "user"로 그대로 쓰면 일부 DB에서 예약어와 충돌할 수 있어 관례적으로 users를 많이 씀)

@Getter
@Setter
// 모든 필드에 대해 getXxx(), setXxx() 메서드를 자동 생성 (직접 안 써도 됨)

@NoArgsConstructor
// 매개변수 없는 기본 생성자를 자동 생성 (JPA가 객체를 만들 때 필요로 함)

public class User {

    @Id
    // 이 필드가 테이블의 기본키(Primary Key)임을 표시
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 값을 자동으로 1, 2, 3... 순서대로 증가시켜서 생성 (DB가 알아서 번호 매김)
    private Long id;

    @Column(unique = true, nullable = false)
    // unique = true: 같은 값 중복 저장 불가 (아이디 중복 방지)
    // nullable = false: 반드시 값이 있어야 함 (비워둘 수 없음)
    private String username;

    @Column(nullable = false)
    private String password;
    // 비밀번호는 나중에 BCrypt로 암호화된 값이 저장될 예정 (평문 저장 절대 금지)
}