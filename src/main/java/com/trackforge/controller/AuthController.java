package com.trackforge.controller;

import com.trackforge.dto.LoginRequestDTO;
import com.trackforge.dto.LogoutRequest;
import com.trackforge.dto.RefreshTokenRequest;
import com.trackforge.dto.RegisterRequestDTO;
import com.trackforge.dto.TokenResponse;
import com.trackforge.entity.RefreshToken;
import com.trackforge.entity.User;
import com.trackforge.repository.UserRepository;
import com.trackforge.service.AuthService;
import com.trackforge.service.JwtService;
import com.trackforge.service.RefreshTokenService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    // ===================== REGISTER =====================
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO request) {
        String result = authService.register(request);
        return ResponseEntity.ok(result);
    }

    // ===================== LOGIN =====================
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequestDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

            String accessToken = jwtService.generateToken(userDetails.getUsername());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

            return ResponseEntity.ok(new TokenResponse(accessToken, refreshToken.getToken()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        }
    }

    // ===================== REFRESH TOKEN =====================
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        Optional<RefreshToken> refreshTokenOptional = refreshTokenService.findByToken(requestRefreshToken);

        if (refreshTokenOptional.isEmpty()) {
            throw new RuntimeException("Refresh token not found.");
        }

        RefreshToken refreshToken = refreshTokenService.verifyExpiration(refreshTokenOptional.get());
        String username = refreshToken.getUser().getEmail();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String newAccessToken = jwtService.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new TokenResponse(newAccessToken, requestRefreshToken));
    }

    // ===================== LOGOUT BY EMAIL =====================
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestBody LogoutRequest logoutRequest) {
        String username = logoutRequest.getEmail();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        refreshTokenService.deleteByUserId(user.getId());

        return ResponseEntity.ok("User logged out successfully. Refresh token deleted.");
    }

    // ===================== LOGOUT BY REFRESH TOKEN =====================
    @PostMapping("/logout-by-token")
    public ResponseEntity<?> logoutByRefreshToken(@RequestBody RefreshTokenRequest request) {
        refreshTokenService.deleteByToken(request.getRefreshToken());
        return ResponseEntity.ok("Logout successful. Refresh token deleted.");
    }
}
