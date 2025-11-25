package com.example.projectexecutionplatform.services;

import com.example.projectexecutionplatform.models.dtos.RegisterRequestDTO;
import com.example.projectexecutionplatform.models.dtos.RegisterResponseDTO;
import com.example.projectexecutionplatform.models.dtos.SignInRequestDTO;
import com.example.projectexecutionplatform.models.dtos.SignInResponseDTO;

public interface AuthService {

    RegisterResponseDTO register(RegisterRequestDTO requestDTO);

    SignInResponseDTO signIn(SignInRequestDTO requestDTO);


}
