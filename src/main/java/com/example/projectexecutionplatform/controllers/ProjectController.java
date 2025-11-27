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
    @GetMapping("/getProject/{project_id}")
    @Operation(summary = "Get Project details by its id ")
    public ProjectResponseDTO getProject(@PathVariable String project_id){
        return projectService.getProject(project_id);
    }


    // get all projects
    @GetMapping("/getAllProject")
    @Operation(summary = "Get All Project details ")
    public List<ProjectResponseDTO> getAllProject(){
        return projectService.getAllProjects();
    }


    // update project details by id
    @PutMapping("/update/{project_id}")
    @Operation(summary = "Update Project details by its id ")
    public ProjectResponseDTO updateProject(
            @Valid
            @PathVariable String project_id,
            @RequestBody ProjectUpdateDTO dto){

        return projectService.updateProject(project_id,dto);
    }


    // delete project details by id :
    @DeleteMapping("/delete/{project_id}")
    @Operation(summary = "Delete Project details by its id ")
    public String deleteProject(@PathVariable String project_id){

        projectService.deleteProject(project_id);
        return "Project Deleted Successfully !";
    }




}
