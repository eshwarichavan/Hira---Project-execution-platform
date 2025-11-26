package com.example.projectexecutionplatform.models.dtos;

import com.example.projectexecutionplatform.models.enums.SprintStatus;
import com.example.projectexecutionplatform.validators.ValidDateFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SprintUpdateRequestDTO {

    @NotBlank(message = "Sprint name cannot be blank")
    private String name;

    @NotNull(message = "Start date cannot be null")
    @ValidDateFormat
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    @ValidDateFormat
    private LocalDate endDate;

    @NotNull(message = "Status is required")
    private SprintStatus status;
}
