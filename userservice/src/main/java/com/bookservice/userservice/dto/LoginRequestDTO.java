package com.bookservice.userservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequestDTO {
    String username;
    String password;
}
