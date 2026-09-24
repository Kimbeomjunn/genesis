package com.example.genesis_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
// 테이블 이름을 "addresses"로 지정

@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullAddress;
    // 사용자가 검색해서 선택한 전체 주소 텍스트 (예: "서울특별시 강남구 테헤란로 123")

    @Column(nullable = false)
    private Double latitude;
    // 위도 (Geocoding API로 변환된 좌표)

    @Column(nullable = false)
    private Double longitude;
    // 경도

    @ManyToOne
    // "여러 개의 Address가, 하나의 User에 속한다"는 관계 설정
    // (한 사용자가 여러 주소를 등록할 수 있음 - 1:N 관계)
    @JoinColumn(name = "user_id")
    // 실제 테이블에는 user_id라는 컬럼이 생기고, 여기에 User의 id 값이 저장됨
    private User user;
    // 이 주소가 "누구의" 주소인지 연결하는 필드
}