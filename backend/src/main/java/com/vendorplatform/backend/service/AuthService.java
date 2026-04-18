package com.vendorplatform.backend.service;

import com.vendorplatform.backend.model.User;
import com.vendorplatform.backend.repository.UserRepository;
import com.vendorplatform.backend.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepo, BCryptPasswordEncoder encoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public Map<String, String> register(User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return Map.of("token", token, "role", user.getRole().name());
    }

    public Map<String, String> login(String email, String password) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!encoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        String token = jwtUtil.generateToken(email, user.getRole().name());
        return Map.of("token", token, "role", user.getRole().name(), "name", user.getName());
    }
}
