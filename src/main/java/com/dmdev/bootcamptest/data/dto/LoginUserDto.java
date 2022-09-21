package com.dmdev.bootcamptest.data.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginUserDto {
    @NotBlank(message = "E-mail can't be empty")
    @Email
    private String email;
    @NotBlank(message = "Password can't be empty")
    private String password;
}
