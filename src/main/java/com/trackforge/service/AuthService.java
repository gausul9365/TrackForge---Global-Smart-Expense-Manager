package com.trackforge.service;

import com.trackforge.dto.LoginRequestDTO;
import com.trackforge.dto.LoginResponseDTO;
import com.trackforge.dto.RegisterRequestDTO;

import com.trackforge.entity.User;
import com.trackforge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtUtil;


    public String register(RegisterRequestDTO request) {
        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            return "Email already in use.";
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));

        userRepo.save(user);
        return "User registered successfully.";
    }
    
    
    public LoginResponseDTO login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String token = jwtUtil.generateToken(request.getEmail());
        return new LoginResponseDTO(token);
    }
    
    
}

