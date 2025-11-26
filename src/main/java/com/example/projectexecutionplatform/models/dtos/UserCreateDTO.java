package com.example.projectexecutionplatform.models.dtos;

import com.example.projectexecutionplatform.models.enums.Roles;
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
public class UserCreateDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @StrictEmailAddress
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @ValidPasswordLength
    private String password;

    private Roles role;
}
