package com.example.projectexecutionplatform.controllers;

import com.example.projectexecutionplatform.models.dtos.SprintCreateRequestDTO;
import com.example.projectexecutionplatform.models.dtos.SprintResponseDTO;
import com.example.projectexecutionplatform.models.dtos.SprintUpdateRequestDTO;
import com.example.projectexecutionplatform.services.SprintServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sprints")
@Validated
@Tag(name = "Sprints API" , description ="This is sprint controller API to manage all the operations of sprint.")
public class SprintController {

    @Autowired
    private SprintServiceImpl sprintService;


    // create sprint :
    @PostMapping("/create")
    @Operation(summary = "Create Sprint ")
    public SprintResponseDTO createSprint(
            @Valid
            @RequestBody SprintCreateRequestDTO dto){

        return sprintService.createSprint(dto);
    }


    //get all sprint :
    @GetMapping("/getAll")
    @Operation(summary = "Get all Sprint details ")
    public List<SprintResponseDTO> getSprint(){
        return sprintService.getAllSprint();
    }



    //get sprint by id :
    @GetMapping("/getById/{id}")
    @Operation(summary = "Get Sprint details by its id ")
    public SprintResponseDTO getSprintById(@PathVariable Long id){
        return sprintService.getSprintById(id);
    }


    // update sprint by id :
    @PutMapping("/update/{id}")
    @Operation(summary = "Update Sprint details by its id ")
    public SprintResponseDTO updateSprint(
            @Valid
            @PathVariable Long id,
            @RequestBody SprintUpdateRequestDTO dto){

        return sprintService.updateSprint(id,dto);
    }


    // delete sprint by id :
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete Sprint details by its id ")
    public ResponseEntity<String> deleteSprint(@PathVariable Long id){
        sprintService.deleteSprint(id);

        return ResponseEntity.ok("Sprint deleted successfully");
    }

}
