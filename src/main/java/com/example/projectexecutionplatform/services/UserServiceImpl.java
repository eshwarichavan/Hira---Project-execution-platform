package com.example.projectexecutionplatform.services;

import com.example.projectexecutionplatform.exceptions.CustomException;
import com.example.projectexecutionplatform.models.dtos.UserCreateDTO;
import com.example.projectexecutionplatform.models.dtos.UserResponseDTO;
import com.example.projectexecutionplatform.models.entities.Users;
import com.example.projectexecutionplatform.repositories.UserRepository;
import com.example.projectexecutionplatform.utils.UserIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UsersService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //create user :
    public UserResponseDTO createUser(UserCreateDTO dto) {

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new CustomException("Email already exists", HttpStatus.CONFLICT);
        }

        Users user = Users.builder()
                .userId(UserIdGenerator.generate())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .build();

        userRepository.save(user);

        return mapToResponse(user);
    }


    // get all users :
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // get user by id :
    public UserResponseDTO getUser(Long id) {

        Users user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        return mapToResponse(user);
    }


    // update user details :
    public UserResponseDTO updateUser(Long id, UserCreateDTO dto) {

        Users user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        userRepository.save(user);

        return mapToResponse(user);
    }



    // delete user :
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new CustomException("User not found", HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
    }



    private UserResponseDTO mapToResponse(Users user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
