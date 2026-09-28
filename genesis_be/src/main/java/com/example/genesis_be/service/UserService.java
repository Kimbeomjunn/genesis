package com.example.genesis_be.service;

import com.example.genesis_be.entity.User;
import com.example.genesis_be.exception.DuplicateUserException;
import com.example.genesis_be.exception.InvalidCredentialsException;
// 새로 만든 예외 클래스들을 가져옴
import com.example.genesis_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signup(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new DuplicateUserException("이미 존재하는 아이디입니다.");
            // 기존: throw new RuntimeException(...)
            // 변경: 전용 예외 타입으로 명확하게 표현
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다."));
        // 기존: new RuntimeException("존재하지 않는 아이디입니다.")
        // 변경: InvalidCredentialsException으로 통일
        // (보안 팁: "아이디가 없다"와 "비밀번호가 틀렸다"를 구분해서 알려주면,
        //  공격자가 "이 아이디는 존재하는구나"를 알아낼 수 있음. 그래서 메시지를 통일함)

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다.");
            // 이것도 동일한 메시지로 통일
        }

        return user;
    }
}