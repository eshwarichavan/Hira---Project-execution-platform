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
public class SprintRequestDTO {

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
}
