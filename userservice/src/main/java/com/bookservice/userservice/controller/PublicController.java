package com.bookservice.userservice.controller;

import com.bookservice.userservice.dto.LoginRequestDTO;
import com.bookservice.userservice.dto.identity.TokenExchangeRepsonse;
import com.bookservice.userservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/public")
public class PublicController {
    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    ResponseEntity<TokenExchangeRepsonse> login(@RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(userService.login(dto));
    }

}
