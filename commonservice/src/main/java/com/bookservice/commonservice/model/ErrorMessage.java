package com.bookservice.commonservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private String code;
    private String message;
    private HttpStatus status;

    public ErrorMessage(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
