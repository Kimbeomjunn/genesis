package com.example.genesis_be.repository;

import com.example.genesis_be.entity.Address;
import com.example.genesis_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    // 기본 CRUD 메서드(save, findById 등)를 자동으로 갖게 됨

    List<Address> findByUser(User user);
    // "findBy + 필드명" 규칙으로, 특정 User가 등록한 주소들만 찾아옴
    // Day 3 목표인 "로그인한 사용자의 주소만 보여주기"에 핵심적으로 쓰일 메서드
}