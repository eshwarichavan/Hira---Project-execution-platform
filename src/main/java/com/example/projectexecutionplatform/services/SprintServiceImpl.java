package com.example.projectexecutionplatform.services;

import com.example.projectexecutionplatform.exceptions.CustomException;
import com.example.projectexecutionplatform.models.dtos.SprintCreateRequestDTO;
import com.example.projectexecutionplatform.models.dtos.SprintResponseDTO;
import com.example.projectexecutionplatform.models.dtos.SprintUpdateRequestDTO;
import com.example.projectexecutionplatform.models.entities.Project;
import com.example.projectexecutionplatform.models.entities.Sprint;
import com.example.projectexecutionplatform.models.entities.Users;
import com.example.projectexecutionplatform.models.enums.SprintStatus;
import com.example.projectexecutionplatform.repositories.ProjectRepository;
import com.example.projectexecutionplatform.repositories.SprintRepository;
import com.example.projectexecutionplatform.repositories.UserRepository;
import com.example.projectexecutionplatform.utils.SprintIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SprintServiceImpl {

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;


    // create the sprint :
    public SprintResponseDTO createSprint(SprintCreateRequestDTO dto){

        // Handling the exceptions :
        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(()-> new CustomException("Project not found", HttpStatus.NOT_FOUND));

        Users creator = userRepository.findById(dto.getCreatedBy())
                .orElseThrow(()-> new CustomException("Creator User not found ",HttpStatus.NOT_FOUND));

        if(dto.getEndDate().isBefore(dto.getStartDate())){
            throw new CustomException("End Date cannot be before start date",HttpStatus.BAD_REQUEST);
        }

        Sprint sprint=Sprint.builder()
                .sprintId(SprintIdGenerator.generate())
                .name(dto.getName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .sprintStatus(SprintStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .project(project)
                .createdBy(creator)
                .build();

        sprintRepository.save(sprint);

        return mapToResponse(sprint);

    }


    // get sprint by id :
    public SprintResponseDTO getSprintById(Long id){
        Sprint sprint=sprintRepository.findById(id)
                .orElseThrow(()-> new CustomException("Sprint Not found " ,HttpStatus.NOT_FOUND));

        return mapToResponse(sprint);
    }


    // get all sprint :
    public List<SprintResponseDTO> getAllSprint(){
        return sprintRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }



    // update sprint details :
    public SprintResponseDTO updateSprint(Long id,SprintUpdateRequestDTO dto){

        Sprint sprint=sprintRepository.findById(id)
                .orElseThrow(()-> new CustomException("Sprint Not found",HttpStatus.NOT_FOUND));

        sprint.setName(dto.getName());
        sprint.setStartDate(dto.getStartDate());
        sprint.setEndDate(dto.getEndDate());
        sprint.setSprintStatus(dto.getStatus());
        sprint.setUpdatedAt(LocalDateTime.now());

        sprintRepository.save(sprint);

        return mapToResponse(sprint);
    }




    // delete sprint details :
    public void deleteSprint(Long id){
        Sprint sprint=sprintRepository.findById(id)
                .orElseThrow(() -> new CustomException("Sprint not found ",HttpStatus.NOT_FOUND));

        sprintRepository.save(sprint);
    }



    // object mapper :
    private SprintResponseDTO mapToResponse(Sprint sprint){
        return SprintResponseDTO.builder()
                .sprintId(sprint.getSprintId())
                .name(sprint.getName())
                .status(sprint.getSprintStatus())
                .startDate(sprint.getStartDate())
                .endDate(sprint.getEndDate())
                .build();
    }
}
