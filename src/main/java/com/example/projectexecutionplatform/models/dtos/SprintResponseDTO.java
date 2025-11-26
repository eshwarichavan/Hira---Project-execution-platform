package com.example.projectexecutionplatform.models.dtos;

import com.example.projectexecutionplatform.models.enums.SprintStatus;
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
    private SprintStatus status;
    private LocalDate startDate;
    private LocalDate endDate;

}
