package com.example.projectexecutionplatform.services;

import com.example.projectexecutionplatform.models.dtos.UserCreateDTO;
import com.example.projectexecutionplatform.models.dtos.UserResponseDTO;

import java.util.List;

public interface UsersService {
    UserResponseDTO createUser(UserCreateDTO dto);
    UserResponseDTO getUser(Long id);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO updateUser(Long id, UserCreateDTO dto);
    void deleteUser(Long id);
}
