package com.dmdev.bootcamptest.components;

import com.dmdev.bootcamptest.data.dto.ApiResponse;
import com.dmdev.bootcamptest.exceptions.TagNameUnavailableException;
import com.dmdev.bootcamptest.exceptions.TagNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TagControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<?> tagNotFound(TagNotFoundException exception) {
        var res = ApiResponse.getErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler
    public ResponseEntity<?> tagNameUnavailable(TagNameUnavailableException exception) {
        var res = ApiResponse.getErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        return ResponseEntity.badRequest().body(res);
    }
}
