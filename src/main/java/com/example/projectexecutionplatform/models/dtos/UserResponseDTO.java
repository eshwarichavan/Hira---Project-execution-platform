package com.example.projectexecutionplatform.models.dtos;

import com.example.projectexecutionplatform.models.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;
    private String userId;
    private String name;
    private String email;
    private Roles role;
}
