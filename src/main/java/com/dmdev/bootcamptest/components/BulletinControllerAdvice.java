package com.dmdev.bootcamptest.components;

import com.dmdev.bootcamptest.data.dto.ApiResponse;
import com.dmdev.bootcamptest.exceptions.BulletinNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BulletinControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<?> bulletinNotFound(BulletinNotFoundException exception) {
        var res = ApiResponse.getErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler
    public ResponseEntity<?> accessDenied(AccessDeniedException exception) {
        var res = ApiResponse.getErrorResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage());
        return ResponseEntity.badRequest().body(res);
    }
}
