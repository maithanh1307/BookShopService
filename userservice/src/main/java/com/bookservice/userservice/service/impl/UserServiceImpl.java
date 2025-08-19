package com.bookservice.userservice.service.impl;

import com.bookservice.userservice.dto.CreateUserRequestDTO;
import com.bookservice.userservice.dto.LoginRequestDTO;
import com.bookservice.userservice.dto.UserResponseDTO;
import com.bookservice.userservice.dto.identity.*;
import com.bookservice.userservice.entity.User;
import com.bookservice.userservice.repository.IdentityClient;
import com.bookservice.userservice.repository.UserRepository;
import com.bookservice.userservice.service.IUserService;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IdentityClient identityClient;

    @Value("${idp.client-id}")
    @NonFinal
    String clientId;

    @Value("${idp.client-secret}")
    @NonFinal
    String clientSecret;

    @Override
    public UserResponseDTO createUser(CreateUserRequestDTO dto) {
        var token = identityClient.exchangeClientToken(TokenExchangeParam.builder()
                .grant_type("client_credentials")
                .client_secret(clientSecret)
                .client_id(clientId)
                .scope("openid")
                .build());

        String accessToken = token.getAccessToken();

        // 1. Tạo user không có credentials
        var creationResponse = identityClient.createUser(UserCreationParam.builder()
                        .username(dto.getUsername())
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .email(dto.getEmail())
                        .enabled(true)
                        .emailVerified(false)
                        .build(),
                "Bearer " + accessToken);

        String userId = extractUserId(creationResponse);
        log.info("UserId {}", userId);

        // 3. Lưu vào DB riêng
        User user = new User();
        user.setUserId(userId);
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setDob(dto.getDob());
        user.setName(dto.getName());

        user = userRepository.save(user);
        return toDTO(user);
    }


    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return toDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(Long id, CreateUserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setDob(dto.getDob());
        user.setName(dto.getName());

        return toDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public TokenExchangeRepsonse login(LoginRequestDTO dto) {
        var token = identityClient.exchangeUserToken(UserTokenExchangeParam.builder()
                .grant_type("password")
                .client_secret(clientSecret)
                .client_id(clientId)
                .scope("openid")
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build());
        return token;
    }

    private UserResponseDTO toDTO(User user) {
        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dob(user.getDob())
                .name(user.getName())
                .id(user.getId())
                .build();
    }

    private String extractUserId(ResponseEntity<?> response) {
        List<String> locations = response.getHeaders().get("Location");
        if (locations == null || locations.isEmpty()) {
            throw new IllegalStateException("Location header is missing in the response");
        }

        String location = locations.get(0);
        String[] splitedStr = location.split("/");
        return splitedStr[splitedStr.length - 1];
    }
}
