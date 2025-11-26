package com.example.projectexecutionplatform.controllers;

import com.example.projectexecutionplatform.models.dtos.ProjectCreateRequestDTO;
import com.example.projectexecutionplatform.models.dtos.ProjectResponseDTO;
import com.example.projectexecutionplatform.models.dtos.ProjectUpdateDTO;
import com.example.projectexecutionplatform.services.ProjectServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/projects")
@Tag(name = "Project API", description ="This is project controller API to manage all the operations of project.")
public class ProjectController {

    @Autowired
    private ProjectServiceImpl projectService;


    // create project :
    @PostMapping("/create")
    @Operation(summary = "Create Project ")
    public ProjectResponseDTO createProject(
            @Valid
            @RequestBody ProjectCreateRequestDTO dto){
        return projectService.createProject(dto);
    }


    // get project by id :
    @GetMapping("/getProject/{id}")
    @Operation(summary = "Get Project details by its id ")
    public ProjectResponseDTO getProject(@PathVariable Long id){
        return projectService.getProject(id);
    }


    // get all projects
    @GetMapping("/getAllProject")
    @Operation(summary = "Get All Project details ")
    public List<ProjectResponseDTO> getAllProject(){
        return projectService.getAllProjects();
    }


    // update project details by id
    @PutMapping("/update/{id}")
    @Operation(summary = "Update Project details by its id ")
    public ProjectResponseDTO updateProject(
            @Valid
            @PathVariable Long id,
            @RequestBody ProjectUpdateDTO dto){

        return projectService.updateProject(id,dto);
    }


    // delete project details by id :
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete Project details by its id ")
    public String deleteProject(@PathVariable Long id){

        projectService.deleteProject(id);
        return "Project Deleted Successfully !";
    }




}
