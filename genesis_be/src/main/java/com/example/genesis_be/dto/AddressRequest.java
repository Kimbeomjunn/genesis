package com.example.genesis_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {
    // 주소 등록 요청 시 클라이언트가 보낼 데이터

    private String fullAddress;
    private Double latitude;
    private Double longitude;
    // React의 Google Places Autocomplete가 주소를 선택하면,
    // 이 세 값(전체 주소, 위도, 경도)을 채워서 보내줄 예정
}