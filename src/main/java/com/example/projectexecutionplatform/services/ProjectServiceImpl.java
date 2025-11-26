package com.example.projectexecutionplatform.services;

import com.example.projectexecutionplatform.exceptions.CustomException;
import com.example.projectexecutionplatform.models.dtos.ProjectCreateRequestDTO;
import com.example.projectexecutionplatform.models.dtos.ProjectResponseDTO;
import com.example.projectexecutionplatform.models.dtos.ProjectUpdateDTO;
import com.example.projectexecutionplatform.models.entities.Project;
import com.example.projectexecutionplatform.models.entities.Users;
import com.example.projectexecutionplatform.models.enums.Roles;
import com.example.projectexecutionplatform.repositories.ProjectRepository;
import com.example.projectexecutionplatform.repositories.UserRepository;
import com.example.projectexecutionplatform.utils.ProjectIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectServiceImpl {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;


    // create project :
    public ProjectResponseDTO createProject(ProjectCreateRequestDTO dto) {

        Users creator = userRepository.findById(dto.getCreatedBy())
                .orElseThrow(() -> new CustomException("Creator user not found", HttpStatus.NOT_FOUND));

        if (!creator.getRole().equals(Roles.PROJECT_MANAGER)) {
            throw new CustomException("Only Project Manager can create a project ",HttpStatus.UNAUTHORIZED);
        }

        Project project = Project.builder()
                .projectId(ProjectIdGenerator.generate())
                .name(dto.getName())
                .description(dto.getDescription())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy(creator)
                .build();

        projectRepository.save(project);

        return mapToDto(project);
    }


    // get project by id
    public ProjectResponseDTO getProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));
        return mapToDto(project);
    }


    // get all projects
    public List<ProjectResponseDTO> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }


    // update Project details using id :
    public ProjectResponseDTO updateProject(Long id, ProjectUpdateDTO dto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));

        if (dto.getName() != null) project.setName(dto.getName());
        if (dto.getDescription() != null) project.setDescription(dto.getDescription());

        project.setUpdatedAt(LocalDateTime.now());
        projectRepository.save(project);

        return mapToDto(project);
    }


    // delete project by id :
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new CustomException("Project not found", HttpStatus.NOT_FOUND));

        projectRepository.delete(project);
    }


    // mapper :
    private ProjectResponseDTO mapToDto(Project project) {
        return ProjectResponseDTO.builder()
                .projectId(ProjectIdGenerator.generate())
                .name(project.getName())
                .description(project.getDescription())
                .createdByUserId(project.getCreatedBy().getUserId())
                .createdByName(project.getCreatedBy().getName())
                .build();
    }
}


