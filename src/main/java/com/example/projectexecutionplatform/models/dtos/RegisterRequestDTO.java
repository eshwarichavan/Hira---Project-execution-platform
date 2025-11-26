package com.example.projectexecutionplatform.models.dtos;

import com.example.projectexecutionplatform.models.enums.Roles;
import com.example.projectexecutionplatform.validators.StrictEmailAddress;
import com.example.projectexecutionplatform.validators.ValidPasswordLength;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO {

    @NotBlank(message = "Username is required")
    @Size(min=3,max=30,message = "Username should be in between 3 to 30 characters")
    private String name;


    @NotBlank(message = "Email is required")
    @StrictEmailAddress
    private String email;


    @NotBlank(message = "Password is required")
    @ValidPasswordLength
    private String password;


    @NotNull(message = "Role is required")
    private Roles role;
}
