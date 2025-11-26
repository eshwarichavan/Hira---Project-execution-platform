package com.example.projectexecutionplatform.models.dtos;

import com.example.projectexecutionplatform.validators.StrictEmailAddress;
import com.example.projectexecutionplatform.validators.ValidPasswordLength;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInRequestDTO {

    @StrictEmailAddress
    @NotBlank(message = "Email is required")
    private String email;


    @NotBlank(message = "Password cannot be empty")
    @ValidPasswordLength
    private String password;

}
