package com.example.projectexecutionplatform.models.dtos;

import com.example.projectexecutionplatform.models.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInResponseDTO {

    private String email;
    private Roles role;
    private String message;
    private String accessToken;
    private String refreshToken;
}
