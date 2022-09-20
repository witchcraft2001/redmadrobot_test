package com.dmdev.bootcamptest.components;

import com.dmdev.bootcamptest.data.dto.ApiResponse;
import com.dmdev.bootcamptest.exceptions.UnknownUserException;
import com.dmdev.bootcamptest.exceptions.UserEmailUnavailableException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler
    public ResponseEntity emailUnavailable(UserEmailUnavailableException exception) {
        var res = ApiResponse.getErrorResponse(401, exception.getMessage());
        return ResponseEntity.unprocessableEntity().body(res);
    }

    @ExceptionHandler
    public ResponseEntity unknownUser(UnknownUserException exception) {
        var res = ApiResponse.getErrorResponse(400, exception.getMessage());
        return ResponseEntity.badRequest().body(res);
    }
}
