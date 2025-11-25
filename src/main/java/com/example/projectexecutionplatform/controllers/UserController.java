package com.example.projectexecutionplatform.controllers;

import com.example.projectexecutionplatform.models.dtos.UserCreateDTO;
import com.example.projectexecutionplatform.models.dtos.UserResponseDTO;
import com.example.projectexecutionplatform.services.UserServiceImpl;
import com.example.projectexecutionplatform.services.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/users")
@Tag(name = "User API", description ="This is user controller API to manage all the operations of user.")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/create")
    @Operation(summary = "create User ")
    public UserResponseDTO createUser(
            @Valid
            @RequestBody UserCreateDTO dto) {
        return userService.createUser(dto);
    }

    @GetMapping("/getUserById/{id}")
    @Operation(summary = "get User ")
    public UserResponseDTO getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }


    @GetMapping
    @Operation(summary = "get all User ")
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }


    @PutMapping("/update/{id}")
    @Operation(summary = "update User details by its id ")
    public UserResponseDTO updateUser(@PathVariable Long id,
                                      @Valid
                                      @RequestBody UserCreateDTO dto) {
        return userService.updateUser(id, dto);
    }


    @DeleteMapping("/delete/{id}")
    @Operation(summary = "delete User details by id")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }

}
