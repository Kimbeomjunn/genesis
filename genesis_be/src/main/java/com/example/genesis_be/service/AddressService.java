package com.example.genesis_be.service;

import com.example.genesis_be.entity.Address;
import com.example.genesis_be.entity.User;
import com.example.genesis_be.repository.AddressRepository;
import com.example.genesis_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public Address save(String username, String fullAddress, Double lat, Double lng) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        // JwtAuthFilter가 알려준 username으로, 실제 User 엔티티를 DB에서 찾아옴

        Address address = new Address();
        address.setFullAddress(fullAddress);
        address.setLatitude(lat);
        address.setLongitude(lng);
        address.setUser(user);
        // 이 주소가 "누구의 것인지" 연결 (Address 엔티티의 @ManyToOne 관계)

        return addressRepository.save(address);
    }

    public List<Address> getAddressesByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        return addressRepository.findByUser(user);
        // 이 유저가 등록한 주소들만 가져옴
    }
}