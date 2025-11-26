package com.example.projectexecutionplatform.services;

import com.example.projectexecutionplatform.models.dtos.ProjectCreateRequestDTO;
import com.example.projectexecutionplatform.models.dtos.ProjectResponseDTO;
import com.example.projectexecutionplatform.models.dtos.ProjectUpdateDTO;

import java.util.List;

public interface ProjectService {

    ProjectResponseDTO createProject(ProjectCreateRequestDTO dto);

    ProjectResponseDTO getProject(Long id);

    List<ProjectResponseDTO> getAllProjects();

    ProjectResponseDTO updateProject(Long id, ProjectUpdateDTO dto);

    void deleteProject(Long id);
}
