package com.example.genesis_be.controller;

import com.example.genesis_be.dto.AddressRequest;
import com.example.genesis_be.dto.AddressResponse;
import com.example.genesis_be.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:8081",
        "https://baknu.com",
        "https://www.baknu.com"
})
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public AddressResponse addAddress(@RequestBody AddressRequest request, Authentication authentication) {
        String username = authentication.getName();
        return new AddressResponse(
                addressService.save(username, request.getFullAddress(), request.getLatitude(), request.getLongitude())
        );
        // 저장된 Address 엔티티를 그대로 반환하지 않고, AddressResponse로 감싸서 반환
    }

    @GetMapping
    public List<AddressResponse> getMyAddresses(Authentication authentication) {
        String username = authentication.getName();
        return addressService.getAddressesByUsername(username).stream()
                .map(AddressResponse::new)
                .toList();
        // 엔티티 리스트를 하나씩 AddressResponse로 변환해서 리스트로 반환
    }
}