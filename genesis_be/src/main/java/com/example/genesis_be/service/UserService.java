package com.example.genesis_be.service;

import com.example.genesis_be.entity.User;
import com.example.genesis_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
// 이 클래스가 "비즈니스 로직을 처리하는 서비스"임을 Spring에게 알림
// (Spring이 이 클래스도 자동으로 부품(빈)으로 등록해서 관리하게 됨)

@RequiredArgsConstructor
// final로 선언된 필드들을 매개변수로 받는 생성자를 Lombok이 자동으로 만들어줌
// (아래 UserRepository, PasswordEncoder를 Spring이 자동으로 주입해주기 위한 준비)

public class UserService {

    private final UserRepository userRepository;
    // 아까 만든 UserRepository를 가져와서 사용 (DB 조회/저장용)

    private final PasswordEncoder passwordEncoder;
    // SecurityConfig에서 @Bean으로 등록해둔 그 PasswordEncoder를 자동으로 가져옴

    public User signup(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            // findByUsername으로 같은 아이디가 이미 있는지 확인
            // .isPresent()는 "Optional 안에 값이 실제로 들어있는지" 확인하는 메서드
            throw new RuntimeException("이미 존재하는 아이디입니다.");
            // 이미 있으면 에러를 발생시켜서 회원가입을 막음
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        // 비밀번호를 평문 그대로 저장하지 않고, encode()로 BCrypt 암호화한 값을 저장
        // (암호화된 값은 되돌릴 수 없는 단방향이라, 나중에 로그인 시 "같은 방식으로 암호화해서 비교"하는 식으로 검증함)

        return userRepository.save(user);
        // 최종적으로 User 객체를 DB에 저장하고, 저장된 결과를 반환
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 아이디입니다."));
        // 아이디로 유저를 찾고, 없으면 에러 발생

        if (!passwordEncoder.matches(password, user.getPassword())) {
            // m atches(): 입력받은 평문 비밀번호를, DB에 저장된 암호화된 비밀번호와 비교
            // (암호화는 단방향이라 복호화해서 비교하는 게 아니라, 같은 방식으로 암호화했을 때 결과가 같은지 비교하는 방식)
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return user;
        // 검증 통과하면 user 정보 반환
    }
}