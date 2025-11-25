package com.example.projectexecutionplatform.models.dtos;

import com.example.projectexecutionplatform.models.enums.Roles;
import jakarta.validation.constraints.Email;
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
    @Email(message = "Please provide a valid email address")
    // make changes here
    @Size(max = 10, message = "Email cannot be more than 10 characters")
    private String email;


    @NotBlank(message = "Password is required")
    @Size(min=6,max=20,message = "Password should be in between 6 to 20 characters")
    private String password;


    @NotNull(message = "Role is required")
    private Roles role;
}
