package com.example.projectexecutionplatform.services;

import com.example.projectexecutionplatform.exceptions.CustomException;
import com.example.projectexecutionplatform.models.dtos.ProjectCreateRequestDTO;
import com.example.projectexecutionplatform.models.dtos.ProjectResponseDTO;
import com.example.projectexecutionplatform.models.entities.Project;
import com.example.projectexecutionplatform.repositories.ProjectRepository;
import com.example.projectexecutionplatform.utils.ProjectIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl {

    @Autowired
    private ProjectRepository projectRepository;


    public ProjectResponseDTO createProject(ProjectCreateRequestDTO dto){

        Project project= Project.builder()
                .projectId(ProjectIdGenerator.generate())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();

        projectRepository.save(project);

        return ProjectResponseDTO.builder()
                .projectId(Long.valueOf(project.getProjectId()))
                .name(project.getName())
                .description(project.getDescription())
                .build();

    }

    public Project getProject(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));
    }

}
