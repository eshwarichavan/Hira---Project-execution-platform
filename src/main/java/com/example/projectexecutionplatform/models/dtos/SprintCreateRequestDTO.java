package com.example.projectexecutionplatform.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SprintCreateRequestDTO {

    @NotBlank(message = "Sprint name is required")
    @Size(min = 3, max = 50, message = "Sprint name must be 3–50 characters")
    private String name;

    @NotNull(message = "Date must be in Format yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "Date must be in Format yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    @NotNull(message = "Creator ID is required")
    private Long createdBy;
}
