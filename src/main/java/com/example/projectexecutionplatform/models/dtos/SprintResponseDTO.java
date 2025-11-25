package com.example.projectexecutionplatform.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SprintResponseDTO {

    private String sprintId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
