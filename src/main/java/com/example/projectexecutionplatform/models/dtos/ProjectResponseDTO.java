package com.example.projectexecutionplatform.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectResponseDTO {

    private String projectId;
    private String name;
    private String description;
    private String createdByUserId;  // USR-xxxx
    private String createdByName;
}
