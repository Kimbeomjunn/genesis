package com.example.genesis_be.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:8081",
        "https://genesis-ie4awf5gl-bj-e691.vercel.app",
        "https://baknu.com",
        "https://www.baknu.com"
})
public class HealthController {

    @GetMapping("/api/health")
    public String health() {
        return "{\"status\": \"ok\"}";
    }
}