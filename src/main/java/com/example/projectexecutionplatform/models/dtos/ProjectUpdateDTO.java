package com.example.projectexecutionplatform.models.dtos;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectUpdateDTO {

    @Size(min = 1, max = 50, message = "Project name must be between 1 and 50 characters")
    private String name;

    private String description;
}
