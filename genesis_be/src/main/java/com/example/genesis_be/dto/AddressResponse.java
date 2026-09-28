package com.example.genesis_be.dto;

import com.example.genesis_be.entity.Address;
import lombok.Getter;

@Getter
public class AddressResponse {
    // 클라이언트에게 돌려줄 "필요한 필드만" 담은 응답 전용 클래스

    private final Long id;
    private final String fullAddress;
    private final Double latitude;
    private final Double longitude;
    // user 필드는 일부러 뺌 → password가 응답에 섞여 나갈 일이 없어짐

    public AddressResponse(Address address) {
        this.id = address.getId();
        this.fullAddress = address.getFullAddress();
        this.latitude = address.getLatitude();
        this.longitude = address.getLongitude();
        // Entity를 받아서, 응답에 보여줄 값만 복사해 담는 생성자
    }
}