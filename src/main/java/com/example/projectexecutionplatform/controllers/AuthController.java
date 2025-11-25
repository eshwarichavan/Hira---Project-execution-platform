package com.example.projectexecutionplatform.controllers;

import com.example.projectexecutionplatform.models.dtos.RegisterRequestDTO;
import com.example.projectexecutionplatform.models.dtos.RegisterResponseDTO;
import com.example.projectexecutionplatform.models.dtos.SignInRequestDTO;
import com.example.projectexecutionplatform.models.dtos.SignInResponseDTO;
import com.example.projectexecutionplatform.repositories.RefreshTokenRepository;
import com.example.projectexecutionplatform.repositories.UserRepository;
import com.example.projectexecutionplatform.services.AuthServiceImpl;
import com.example.projectexecutionplatform.services.RefreshTokenServiceImpl;
import com.example.projectexecutionplatform.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/api/auth")
@Tag(name = "Authentication API", description ="This is Auth controller API to manage all the operations of Authentication.")  //for swagger
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private RefreshTokenServiceImpl refreshTokenService;

    @Autowired
    private JwtUtil jwtUtil;


    // Register user :
    @PostMapping("/registerUser")
    @Operation(summary = "Register User ")
    public ResponseEntity<RegisterResponseDTO> register(
            @Valid
            @RequestBody RegisterRequestDTO requestDTO){
        return ResponseEntity.ok(authService.register(requestDTO));
    }


    // Sign In :
    @PostMapping("/signin")
    @Operation(summary = "Sign In api for all the Users")
    public ResponseEntity<SignInResponseDTO> signIn(
            @RequestBody SignInRequestDTO requestDTO) {

        return ResponseEntity.ok(authService.signIn(requestDTO));
    }


    //Refresh Token :
    @PostMapping("/refreshToken")
    @Operation(summary = "Generates refresh token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> payload){

        String requestToken = payload.get("refreshToken");

        return refreshTokenRepository.findByToken(requestToken)
                .map(token -> {
                    if (refreshTokenService.isTokenExpired(token)) {
                        refreshTokenRepository.delete(token);
                        return ResponseEntity.badRequest().body("Refresh token expired. Please login again.");
                    }
                    List<String> rolesList = new ArrayList<>();
                    rolesList.add(String.valueOf(token.getUser().getRole()));
                    String newJwt = jwtUtil.generateToken(token.getUser().getEmail(), rolesList);
                    return ResponseEntity.ok(Map.of("token", newJwt));
                })
                .orElse(ResponseEntity.badRequest().body("Invalid refresh token."));
    }


    // Logout :
    @PostMapping("/logout")
    @Operation(summary = "User will logout using this API")
    public ResponseEntity<?> logoutUser(@RequestBody Map<String, String> payload) {
        String requestToken = payload.get("refreshToken");

        if (requestToken == null || requestToken.isBlank()) {
            return ResponseEntity.badRequest().body("Refresh token is required.");
        }

        return refreshTokenRepository.findByToken(requestToken)
                .map(token -> {
                    refreshTokenRepository.delete(token);
                    return ResponseEntity.ok("Logged out successfully.");
                })
                .orElse(ResponseEntity.badRequest().body("Invalid refresh token."));
    }

}
