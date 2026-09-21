package com.example.genesis_be;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081"})
public class HealthController {

    @GetMapping("/api/health")
    public String health() {
        return "{\"status\": \"ok\"}";
    }
}