package com.dmdev.bootcamptest.components;

import com.dmdev.bootcamptest.data.dto.ApiResponse;
import com.dmdev.bootcamptest.exceptions.MessageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MessageControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<?> messageNotFound(MessageNotFoundException exception) {
        var res = ApiResponse.getErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return ResponseEntity.badRequest().body(res);
    }
}
