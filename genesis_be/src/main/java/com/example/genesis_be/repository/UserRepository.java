package com.example.genesis_be.repository;

import com.example.genesis_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository<User, Long>을 상속하면
    // save(), findAll(), findById() 같은 기본 DB 조작 메서드들을 자동으로 다 갖게 됨
    // <User, Long>에서 User는 "어떤 엔티티를 다룰지", Long은 "그 엔티티의 id 타입"을 의미

    Optional<User> findByUsername(String username);
    // "findBy + 필드명" 규칙으로 메서드 이름만 지으면, Spring이 알아서 SQL을 만들어줌
    // 로그인 시 "이 아이디를 가진 유저가 있는지" 찾을 때 사용
    // Optional은 "결과가 없을 수도 있다"는 걸 명시적으로 표현하는 타입 (null 대신 안전하게 처리)
}