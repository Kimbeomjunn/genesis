package com.example.genesis_be.controller;

import com.example.genesis_be.dto.AddressRequest;
import com.example.genesis_be.entity.Address;
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
        "https://genesis-ie4awf5gl-bj-e691.vercel.app",
        "https://baknu.com",
        "https://www.baknu.com"
})
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public Address addAddress(@RequestBody AddressRequest request, Authentication authentication) {
        // Authentication authentication ← 이게 핵심!
        // JwtAuthFilter가 SecurityContextHolder에 심어둔 로그인 정보를
        // Spring이 자동으로 이 매개변수에 꺼내서 넣어줌

        String username = authentication.getName();
        // authentication 안에는 아까 필터에서 넣어준 username이 들어있음

        return addressService.save(username, request.getFullAddress(), request.getLatitude(), request.getLongitude());
    }

    @GetMapping
    public List<Address> getMyAddresses(Authentication authentication) {
        String username = authentication.getName();
        return addressService.getAddressesByUsername(username);
    }
}