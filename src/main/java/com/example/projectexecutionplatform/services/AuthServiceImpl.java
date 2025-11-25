package com.example.projectexecutionplatform.services;

import com.example.projectexecutionplatform.exceptions.CustomException;
import com.example.projectexecutionplatform.models.dtos.RegisterRequestDTO;
import com.example.projectexecutionplatform.models.dtos.RegisterResponseDTO;
import com.example.projectexecutionplatform.models.dtos.SignInRequestDTO;
import com.example.projectexecutionplatform.models.dtos.SignInResponseDTO;
import com.example.projectexecutionplatform.models.entities.RefreshToken;
import com.example.projectexecutionplatform.models.entities.Users;
import com.example.projectexecutionplatform.repositories.UserRepository;
import com.example.projectexecutionplatform.utils.JwtUtil;
import com.example.projectexecutionplatform.utils.UserIdGenerator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AuthServiceImpl implements  AuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RefreshTokenServiceImpl refreshTokenService;

    @Autowired
    private JwtUtil jwtUtil;


    // Register :
    public RegisterResponseDTO register(RegisterRequestDTO requestDTO){

        // checking if user exists :
        if(userRepository.findByEmail(requestDTO.getEmail()).isPresent()){
            throw new CustomException("User Already Exists.", HttpStatus.CONFLICT);
        }

        // Create user and save its details :
        Users user= Users.builder()
                .userId(UserIdGenerator.generate())
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .role(requestDTO.getRole())
                .createdBy(null)
                .updatedBy(null)
                .build();

        userRepository.save(user);

        //Response :
        return RegisterResponseDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .message("User Registered Successfully")
                .build();
    }



    // Sign In :
    public SignInResponseDTO signIn(@Valid SignInRequestDTO requestDTO) {

        Users user = userRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new CustomException("User not found!", HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new CustomException("Invalid password!", HttpStatus.UNAUTHORIZED);
        }


        //Generate token using user's email and role
        List<String> roles = Collections.singletonList(user.getRole().name());
        String token = jwtUtil.generateToken(user.getEmail(), roles);
        System.out.println("Generated JWT Token : " + token);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return new SignInResponseDTO(
                user.getEmail(),
                user.getRole(),
                "Login Successfully",
                token,
                refreshToken.getToken()
        );
    }




}
