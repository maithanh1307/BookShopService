package com.bookservice.userservice.service;

import com.bookservice.userservice.dto.CreateUserRequestDTO;
import com.bookservice.userservice.dto.UserResponseDTO;

import java.util.List;


public interface IUserService {
    UserResponseDTO createUser(CreateUserRequestDTO dto);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
    UserResponseDTO updateUser(Long id, CreateUserRequestDTO dto);
    void deleteUser(Long id);
}
